package frc.robot.util;

import edu.wpi.first.wpilibj.Notifier;
import frc.robot.OI;
import org.zeromq.ZMQ;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Vision implements Runnable {

    private static Vision instance;
    public static Vision getInstance() {
        if (instance == null) {
            instance = new Vision();
        }
        return instance;
    }

    private Notifier notifier;

    private ZMQ.Context context;
    private ZMQ.Socket alignmentSocket;

    private double tickTime = 0.2;

    private double frontCameraError = 0;
    private double backCameraError = 0;

    private Vision() {

        context = ZMQ.context(1);

        // Socket to talk to server
        System.out.println("Connecting to ZMQ server…");

        alignmentSocket = context.socket(ZMQ.SUB);
        alignmentSocket.connect("tcp://127.0.0.1:5802");
        alignmentSocket.subscribe(new byte[0]);
        alignmentSocket.setConflate(true);

        notifier = new Notifier(this);
        notifier.startPeriodic(tickTime);

    }

    @Override
    public void run() {

        byte[] data = alignmentSocket.recv();
        ByteBuffer buffer = ByteBuffer
                .wrap(data)
                .order(ByteOrder.LITTLE_ENDIAN);

        frontCameraError = buffer.getDouble();
        backCameraError = buffer.getDouble();

    }

    public double getFrontCameraError() {

        return frontCameraError;

    }

    public double getBackCameraError() {

        return backCameraError;

    }

}
