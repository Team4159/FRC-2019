package frc.team4159.robot;

import edu.wpi.first.wpilibj.Notifier;
import frc.team4159.robot.subsystems.Elevator;

public class Main {
    // control loop speed in seconds
    public static double dt = 0.01;

    public static void main(String... args) {
        Elevator elevator = Elevator.getInstance();
        new Notifier(elevator::iterate).startPeriodic(dt);
    }
}
