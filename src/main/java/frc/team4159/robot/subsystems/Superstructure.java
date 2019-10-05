package frc.team4159.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;

public class Superstructure implements Subsystem {
    private static Superstructure instance;
    public static  Superstructure getInstance() {
        if (instance == null) {
            instance = new Superstructure();
        }
        return instance;
    }

    private Superstructure() {
        CameraServer.getInstance().startAutomaticCapture();
    }

    @Override
    public void iterate() {

    }
}
