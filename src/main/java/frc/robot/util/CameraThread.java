package frc.robot.util;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Notifier;
import frc.robot.subsystems.Superstructure;
import frc.robot.util.enums.Orientation;
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

    private Notifier notifier;
    private CvSink cvSink;
    private CvSource cvSource;
    private UsbCamera video1, video2;
    private Mat image;
    private Scalar white, orange, color;

    private CameraThread() {

        video1 = CameraServer.getInstance().startAutomaticCapture(0);
        video2 = CameraServer.getInstance().startAutomaticCapture(1);

        video1.setFPS(15);
        video2.setFPS(15);
        video1.setResolution(320, 240);
        video2.setResolution(320, 240);

        cvSink = CameraServer.getInstance().getVideo();
        cvSink.setSource(video1);
        cvSource = CameraServer.getInstance().putVideo("Driver Camera", 320, 240);

        video1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        video2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        image = new Mat();
        white = new Scalar(255, 255, 255); // hatch panel color
        orange = new Scalar(0, 140, 255);  // cargo color
        color = white;

    }


    @Override
    public void run() {

        cvSink.grabFrame(image);

        if (!image.empty() && image.rows() > 50 && image.cols() > 50) {
            Imgproc.rectangle(image, new Point(25, 25), new Point(50, 50), color, 25);
            cvSource.putFrame(image);
        }

    }

    public void setOrientation(Orientation orientation) {

        if(orientation == Orientation.FRONT_HATCH) {
            cvSink.setSource(video1);
            color = white;

        } else {
            cvSink.setSource(video2);
            color = orange;
        }

    }

    public void start() {

        if (notifier == null) {
            notifier = new Notifier(this);
            notifier.startPeriodic(0.2);
        }

    }

}
