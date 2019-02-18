package frc.robot.util;

import edu.wpi.first.wpilibj.Notifier;
import frc.robot.OI;
import org.zeromq.ZMQ;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Vision implements Runnable {

    private Notifier notifier;
    private OI oi;

    private static Vision instance;
    public static Vision getInstance() {
        if (instance == null) {
            instance = new Vision();
        }
        return instance;
    }

    private ZMQ.Context context;
    private ZMQ.Socket alignmentSocket;
    private ZMQ.Socket cameraSocket;

    private double tickTime = 0.1;

    private double frontCameraError = 0;
    private double backCameraError = 0;

    private Vision() {

        oi = OI.getInstance();

        notifier = new Notifier(this);
        notifier.startPeriodic(tickTime);

        context = ZMQ.context(1);

        // Socket to talk to server
        System.out.println("Connecting to ZMQ server…");

        alignmentSocket = context.socket(ZMQ.SUB);
        alignmentSocket.connect("tcp://127.0.0.1:5802");
        alignmentSocket.subscribe(new byte[0]);
        alignmentSocket.setConflate(true);

        cameraSocket = context.socket(ZMQ.PUB);
        cameraSocket.connect("tcp://127.0.0.1:5803");
        cameraSocket.setConflate(true);

    }

    @Override
    public void run() {

        byte[] data = alignmentSocket.recv();
        ByteBuffer buffer = ByteBuffer
                .wrap(data)
                .order(ByteOrder.LITTLE_ENDIAN);

        frontCameraError = buffer.getDouble();
        backCameraError = buffer.getDouble();

        if (oi.getCameraChange()) {
            cameraSocket.send(new byte[0], 0);
        }

    }

    public double getFrontCameraError() {

        return frontCameraError;

    }

    public double getBackCameraError() {

        return backCameraError;

    }

}
