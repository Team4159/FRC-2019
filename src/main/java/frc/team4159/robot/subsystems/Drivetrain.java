package frc.team4159.robot.subsystems;

public class Drivetrain implements Subsystem {

    private static Drivetrain instance;

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }

    @Override
    public void iterate() {

    }
}
