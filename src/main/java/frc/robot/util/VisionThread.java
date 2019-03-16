package frc.robot.util;

import edu.wpi.first.wpilibj.Notifier;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VisionThread implements Runnable {

    private static VisionThread instance;
    public static VisionThread getInstance() {
        if (instance == null)
            instance = new VisionThread();
        return instance;
    }

    private Notifier notifier;

    private double frontCameraError = 0;
    private double backCameraError = 0;
    private double frontCameraArea = 0;
    private double backCameraArea = 0;
    private double distance = 0;

    public double getFrontCameraError() {

        return frontCameraError;

    }

    public double getBackCameraError() {

        return backCameraError;

    }

    public double getFrontCameraArea() {

        return frontCameraArea;

    }

    public double getBackCameraArea() {

        return backCameraArea;

    }

    public double getDistance() {

        return frontCameraArea;

    }

    private ZMQ.Socket alignmentSocket;

    private VisionThread() {

        ZMQ.Context context = ZMQ.context(1);
        System.out.println("Connecting to zmq server");

        // Socket to talk to server
        alignmentSocket = context.socket(SocketType.SUB);
        alignmentSocket.connect("tcp://10.41.59.10:5801");
        alignmentSocket.subscribe(new byte[0]);
        alignmentSocket.setConflate(true);

    }

    @Override
    public void run() {

       byte[] data = alignmentSocket.recv();
       ByteBuffer buffer = ByteBuffer
               .wrap(data)
               .order(ByteOrder.LITTLE_ENDIAN);

       frontCameraError = buffer.getDouble();
       frontCameraArea  = buffer.getDouble();
       backCameraError  = buffer.getDouble();
       backCameraArea   = buffer.getDouble();

       //System.out.println(frontCameraError + "," + frontCameraArea  + "," +  + ",");

    }

    public void start() {

        if (notifier == null) {
            notifier = new Notifier(this);
            notifier.startPeriodic(0.01);
        }

    }

}