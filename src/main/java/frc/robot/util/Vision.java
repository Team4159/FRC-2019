package frc.robot.util;

import edu.wpi.first.wpilibj.Notifier;
import org.zeromq.ZMQ;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Vision implements Runnable {

    private Notifier notifier;

    private static Vision instance;
    public static Vision getInstance() {
        if (instance == null) {
            instance = new Vision();
        }
        return instance;
    }

    private ZMQ.Context context;
    private ZMQ.Socket requester;

    private double tickTime = 0.1;

    private double frontCameraError = 0;
    private double backCameraError = 0;

    private Vision() {

        notifier = new Notifier(this);
        notifier.startPeriodic(tickTime);

        context = ZMQ.context(1);

        //  Socket to talk to server
        System.out.println("Connecting to ZMQ server…");

        requester = context.socket(ZMQ.SUB);
        requester.connect("tcp://127.0.0.1:5555"); // TODO: Change

    }

    private void sendData(byte[] data) {

        requester.send(data, 0);

    }

    private byte[] getData() {

        return requester.recv(0);

    }

    public void closeConnection() {

        requester.close();
        context.term();

    }

    @Override
    public void run() {

        byte[] data = getData();
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
