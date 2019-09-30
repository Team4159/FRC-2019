package frc.team4159.robot;

import edu.wpi.first.wpilibj.Notifier;

import frc.team4159.robot.subsystems.*;

public class Main {
    // control loop speed in seconds
    public static double dt = 0.01;

    public static void main(String... args) {
        startSubsystem(Elevator.getInstance());
        startSubsystem(Drivetrain.getInstance());
        startSubsystem(Feeder.getInstance());
        startSubsystem(Nose.getInstance());
        startSubsystem(Grabber.getInstance());
    }

    private static void startSubsystem(Subsystem subsystem) {
        new Notifier(subsystem::iterate).startPeriodic(dt);
    }
}
