package frc.robot.util;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Notifier;
import frc.robot.OI;

public class Camera implements Runnable {

    private static Camera instance;
    public static Camera getInstance() {
        if (instance == null)
            instance = new Camera();
        return instance;
    }

    private Notifier notifier;

    private UsbCamera video1;
    private UsbCamera video2;
    private VideoSink server;

    private OI oi;

    private int cameraPort = 0;

    private double tickTime = (double) 1 / 60;

    private Camera() {

        video1 = CameraServer.getInstance().startAutomaticCapture(0);
        video2 = CameraServer.getInstance().startAutomaticCapture(1);

        video1.setFPS(30);
        video2.setFPS(30);
        video1.setResolution(640, 480);
        video2.setResolution(640, 480);

        server = CameraServer.getInstance().getServer();

        video1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        video2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        oi = OI.getInstance();

    }

    @Override
    public void run() {

        if (oi.getCameraButtonPressed()) {
            cameraPort ^= 1;
            if (cameraPort == 0) {
                server.setSource(video1);
            } else if (cameraPort == 1) {
                server.setSource(video2);
            }
        }

    }

    public void start() {

        if (notifier == null) {
            notifier = new Notifier(this);
            notifier.startPeriodic(tickTime);
        }

    }

}
