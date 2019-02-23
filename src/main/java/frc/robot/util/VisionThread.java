package frc.robot.util;

import org.zeromq.ZMQ;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VisionThread extends Thread {

    private static VisionThread instance;
    public static VisionThread getInstance() {
        if (instance == null)
            instance = new VisionThread();
        return instance;
    }

    private double frontCameraError = 0;
    private double backCameraError = 0;

    private VisionThread() {

        super();
    }


    public double getFrontCameraError() {

        return frontCameraError;

    }

    public double getBackCameraError() {

        return backCameraError;

    }

    @Override
    public void run() {

        ZMQ.Context context = ZMQ.context(1);
        System.out.println("Connecting to ZMQ server…");

        // Socket to talk to server
        ZMQ.Socket alignmentSocket = context.socket(ZMQ.SUB);
        alignmentSocket.connect("tcp://127.0.0.1:5802");
        alignmentSocket.subscribe(new byte[0]);
        alignmentSocket.setConflate(true);

        byte[] data = alignmentSocket.recv();
        ByteBuffer buffer = ByteBuffer
                .wrap(data)
                .order(ByteOrder.LITTLE_ENDIAN);

        frontCameraError = buffer.getDouble();
        backCameraError = buffer.getDouble();

    }

}
