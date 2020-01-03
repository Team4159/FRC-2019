package frc.team4159.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
    @Override
    public void startCompetition() {
        HAL.observeUserProgramStarting();
        UsbCamera hatch_camera = CameraServer.getInstance().startAutomaticCapture();
        hatch_camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        hatch_camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 640, 480, 30);
        VideoSink server = CameraServer.getInstance().addServer("Driver Camera");
        server.setSource(hatch_camera);
        while (true) {

        }
    }
}
