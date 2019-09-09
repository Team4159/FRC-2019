package frc.team4159.robot;

import edu.wpi.first.wpilibj.Notifier;

public class Main {
    // control loop speed in seconds
    public static double dt = 0.01;

    public static void main(String... args) {
        new Notifier(Main::iterate).startPeriodic(dt);
    }

    private static void iterate() {
    }
}
