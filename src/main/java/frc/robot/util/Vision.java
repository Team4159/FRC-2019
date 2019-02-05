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
    private boolean switchCamera = false;

    private Vision() {

        notifier = new Notifier(this);
        notifier.startPeriodic(tickTime);

        context = ZMQ.context(1);

        //  Socket to talk to server
        System.out.println("Connecting to ZMQ server…");

        requester = context.socket(ZMQ.PAIR);
        requester.connect("tcp://localhost:5555"); // TODO: Change

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

    public void switchCamera() {

        // switch camera on next tick
        switchCamera = true;

    }

    @Override
    public void run() {

        byte[] data = getData();
        double xValueToAlignTo = ByteBuffer
                .wrap(data)
                .getDouble();

        System.out.println(xValueToAlignTo); // TODO: send to drivetrain

        if (switchCamera) {
            sendData(ByteBuffer
                        .allocate(1)
                        .putInt(1)
                        .array()
            );

            switchCamera = false;
        } else {
            sendData(ByteBuffer
                    .allocate(1)
                    .putInt(1)
                    .array()
            );
        }

    }
}
