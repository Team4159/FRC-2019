package frc.robot.util;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.OI;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Camera extends Thread {

    @Override
    public void run() {
        UsbCamera video1 = CameraServer.getInstance().startAutomaticCapture(0);
        UsbCamera video2 = CameraServer.getInstance().startAutomaticCapture(1);

        video1.setFPS(30);
        video2.setFPS(30);
        video1.setResolution(640, 480);
        video2.setResolution(640, 480);

        CvSink cvSink = CameraServer.getInstance().getVideo();
        CvSource cvSource = CameraServer.getInstance().putVideo("Driver Camera", 640, 480);

        video1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        video2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        OI oi = OI.getInstance();
        boolean frontCamera = true;

        Mat image = new Mat();

        Scalar blue = new Scalar(255, 0, 0);
        Scalar red = new Scalar(0, 0, 255);

        while (!interrupted()) {
            if (oi.getCameraButtonPressed()) {
                frontCamera = !frontCamera;
                cvSink.setSource(frontCamera ? video1 : video2);
            }

            cvSink.grabFrame(image);
            if (!image.empty() && image.rows() > 50 && image.cols() > 50) {
                Imgproc.rectangle(image, new Point(25, 25), new Point(50, 50), frontCamera ? blue : red, 25);
                cvSource.putFrame(image);
            }
        }

    }

}
