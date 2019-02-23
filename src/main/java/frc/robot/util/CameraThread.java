package frc.robot.util;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Notifier;
import frc.robot.subsystems.Infrastructure;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CameraThread implements Runnable {

    private static CameraThread instance;
    public static CameraThread getInstance() {
        if (instance == null)
            instance = new CameraThread();
        return instance;
    }

    private Infrastructure infrastructure;
    private CvSink cvSink;
    private CvSource cvSource;
    private UsbCamera video1, video2;
    private Mat image;
    private Scalar blue, red;

    private CameraThread() {

        infrastructure = Infrastructure.getInstance();
        video1 = CameraServer.getInstance().startAutomaticCapture(0);
        video2 = CameraServer.getInstance().startAutomaticCapture(1);

        video1.setFPS(30);
        video2.setFPS(30);
        video1.setResolution(640, 480);
        video2.setResolution(640, 480);

        cvSink = CameraServer.getInstance().getVideo();
        cvSource = CameraServer.getInstance().putVideo("Driver Camera", 640, 480);

        video1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        video2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);


        image = new Mat();

        blue = new Scalar(255, 0, 0);
        red = new Scalar(0, 0, 255);

        Notifier notifier = new Notifier(this);
        notifier.startPeriodic(0.01);

    }


    @Override
    public void run() {

        if (infrastructure.getOrientation() == Infrastructure.Orientation.Front) {
            cvSink.setSource(video1);

        } else {
            cvSink.setSource(video2);
        }

        cvSink.grabFrame(image);

        if (!image.empty() && image.rows() > 50 && image.cols() > 50) {
            Imgproc.rectangle(image, new Point(25, 25), new Point(50, 50), infrastructure.getOrientation() == Infrastructure.Orientation.Front ? blue : red, 25);
            cvSource.putFrame(image);
        }
    }

}
