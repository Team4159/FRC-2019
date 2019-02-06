package frc.robot.util;

import edu.wpi.first.wpilibj.Notifier;
import org.zeromq.ZMQ;

import java.nio.ByteBuffer;

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
    private ZMQ.Socket requester;

    private float tickTime = 1;

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
        double xValueToAlignTo = ByteBuffer
                .wrap(data)
                .getDouble();

        System.out.println(xValueToAlignTo); // TODO: send to drivetrain

    }
}
