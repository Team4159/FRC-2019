package frc.team4159.robot;

public class Constants {
    // Ports

    // TODO: Check
    public static int LEFT_MASTER_TALON = 2;
    public static int LEFT_SLAVE_TALON = 3;
    public static int RIGHT_MASTER_TALON = 4;
    public static int RIGHT_SLAVE_TALON = 5;

    public static int ELEVATOR_MASTER_TALON = 11;
    public static int ELEVATOR_SLAVE_TALON = 15;

    public static int LIFTER_TALON = 9;
    public static int INTAKE_TALON = 10;

    public static int GRABBER_MASTER_VICTOR = 8;
    public static int GRABBER_SLAVE_VICTOR = 5;

    public static int RAISER_FORWARD = 0;
    public static int RAISER_REVERSE = 4;

    public static int HOOKS_FORWARD = 5;
    public static int HOOKS_REVERSE = 1;

    public static int ELEVATOR_LIMIT_SWITCH = 9;
    // TODO: Check
    public static int LIFTER_LIMIT_SWITCH = 8;

    // Math-y Stuff
    public static double ELEVATOR_SPROCKET_RADIUS = RobotMath.FeettoMeters(1.751 / 12.0);
    public static double ELEVATOR_SPROCKET_CIRCUMFERENCE = ELEVATOR_SPROCKET_RADIUS * Math.PI * 2;
    public static double TICKS_PER_REV = 4096.0;
}
