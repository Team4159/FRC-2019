package frc.team4159.robot;

import frc.team4159.robot.subsystems.Elevator;

public class Constants {
    // Ports
    // TODO: Check
    public static int LEFT_MASTER_SPARK = 1;
    public static int LEFT_SLAVE_SPARK = 2;
    public static int RIGHT_MASTER_SPARK = 3;
    public static int RIGHT_SLAVE_SPARK = 4;

    public static int ELEVATOR_MASTER_TALON = 11;
    public static int ELEVATOR_SLAVE_TALON = 16;

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
    public static double ELEVATOR_SPROCKET_RADIUS = RobotMath.FeetToMeters(1.751 / 12.0);
    public static double ELEVATOR_SPROCKET_CIRCUMFERENCE = ELEVATOR_SPROCKET_RADIUS * Math.PI * 2;
    public static int TICKS_PER_REV = 4096;

    // TODO: Real numbers
    public static int FEEDER_UP = 0;
    public static int FEEDER_DOWN = -2960;
    public static int FEEDER_STOWED = FEEDER_DOWN / 2;

    public static double RAISER_DANGEROUS_LOWER_BAND = Elevator.MetersToTicks(0.3);
    public static double RAISER_DANGEROUS_HIGHER_BAND = Elevator.MetersToTicks(0.4);
    public static double FEEDER_DANGEROUS_LOWER_BAND = Elevator.MetersToTicks(0.4);
    public static double FEEDER_DANGEROUS_HIGHER_BAND = Elevator.MetersToTicks(0.6);

    public static double HATCH_TO_BOTTOM = RobotMath.FeetToMeters(19.0 / 12.0);
    public static double CARGO_TO_BOTTOM = RobotMath.FeetToMeters(13.0 / 12.0);

    // Cargo ship levels in meters
    public static int CARGO_SHIP_HATCH = Elevator.MetersToTicks(RobotMath.FeetToMeters(19.0 / 12.0) - HATCH_TO_BOTTOM);
    public static int CARGO_SHIP_PORT = Elevator.MetersToTicks(RobotMath.FeetToMeters(49.0 / 12.0) - CARGO_TO_BOTTOM);

    // Rocket levels in meters
    public static int ROCKET_PORT_LEVEL_ONE = Elevator.MetersToTicks(0.70 - CARGO_TO_BOTTOM);
    public static int ROCKET_PORT_LEVEL_TWO = Elevator.MetersToTicks(1.41 - CARGO_TO_BOTTOM);
    public static int ROCKET_PORT_LEVEL_THREE = Elevator.MetersToTicks(2.12 - CARGO_TO_BOTTOM);
    public static int ROCKET_HATCH_LEVEL_ONE = Elevator.MetersToTicks(0.48 - HATCH_TO_BOTTOM);
    public static int ROCKET_HATCH_LEVEL_TWO = Elevator.MetersToTicks(1.19 - HATCH_TO_BOTTOM);
    public static int ROCKET_HATCH_LEVEL_THREE = Elevator.MetersToTicks(1.90 - HATCH_TO_BOTTOM);
}
