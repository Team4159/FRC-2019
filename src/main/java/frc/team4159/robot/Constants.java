package frc.team4159.robot;

import frc.team4159.robot.subsystems.Elevator;

public class Constants {
    // Button IDs
    public static class BUTTON_IDS {
        public static final int ROBOT_ORIENTATION = 2;

        public static final int INTAKE = 7;
        public static final int OUTTAKE = 8;

        public static final int LOWER_FEEDER = 9;
        public static final int RAISE_FEEDER = 6;

        public static final int TOGGLE_HOOKS = 5;
        public static final int TOGGLE_RAISER = 10;

        public static final int ELEVATOR_MANUAL_CONTROL = 1;

        public static final int ELEVATOR_TO_CARGO_SHIP_HATCH = 2;
        public static final int ELEVATOR_TO_CARGO_SHIP_PORT = 14;
        public static final int ELEVATOR_TO_ROCKET_HATCH_LEVEL_TWO = 15;
        public static final int ELEVATOR_TO_ROCKET_HATCH_LEVEL_THREE = 16;
        public static final int ELEVATOR_TO_ROCKET_PORT_LEVEL_ONE = 13;
        public static final int ELEVATOR_TO_ROCKET_PORT_LEVEL_TWO = 12;
        public static final int ELEVATOR_TO_ROCKET_PORT_LEVEL_THREE = 11;
    }

    // Ports
    // TODO: Check
    public static class PORTS {
        public static final int LEFT_MASTER_SPARK = 1;
        public static final int LEFT_SLAVE_SPARK = 2;
        public static final int RIGHT_MASTER_SPARK = 3;
        public static final int RIGHT_SLAVE_SPARK = 4;

        public static final int ELEVATOR_MASTER_TALON = 11;
        public static final int ELEVATOR_SLAVE_TALON = 16;

        public static final int LIFTER_TALON = 9;
        public static final int INTAKE_TALON = 10;

        public static final int GRABBER_MASTER_VICTOR = 8;
        public static final int GRABBER_SLAVE_VICTOR = 5;

        public static final int RAISER_FORWARD = 0;
        public static final int RAISER_REVERSE = 4;

        public static final int HOOKS_FORWARD = 5;
        public static final int HOOKS_REVERSE = 1;

        public static final int ELEVATOR_LIMIT_SWITCH = 9;
        public static final int LIFTER_LIMIT_SWITCH = 8;
    }

    // Math-y Stuff
    public static class NUMS {
        public static final double ELEVATOR_SPROCKET_RADIUS = RobotMath.FeetToMeters(1.751 / 12.0);
        public static final double ELEVATOR_SPROCKET_CIRCUMFERENCE = ELEVATOR_SPROCKET_RADIUS * Math.PI * 2;
        public static final int TICKS_PER_REV = 4096;

        public static final int FEEDER_UP = 0;
        public static final int FEEDER_DOWN = -2960;
        public static final int FEEDER_STOWED = FEEDER_DOWN / 2;

        public static final double RAISER_DANGEROUS_LOWER_BAND = Elevator.MetersToTicks(0.3);
        public static final double RAISER_DANGEROUS_HIGHER_BAND = Elevator.MetersToTicks(0.4);

        public static final double FEEDER_DANGEROUS_LOWER_BAND = Elevator.MetersToTicks(0.4);
        public static final double FEEDER_DANGEROUS_HIGHER_BAND = Elevator.MetersToTicks(0.6);

        public static final double HATCH_TO_BOTTOM = RobotMath.FeetToMeters(19.0 / 12.0);
        public static final int ROCKET_HATCH_LEVEL_THREE = Elevator.MetersToTicks(1.90 - HATCH_TO_BOTTOM);
        public static final int ROCKET_HATCH_LEVEL_TWO = Elevator.MetersToTicks(1.19 - HATCH_TO_BOTTOM);
        public static final int ROCKET_HATCH_LEVEL_ONE = Elevator.MetersToTicks(0.48 - HATCH_TO_BOTTOM);
        public static final int CARGO_SHIP_HATCH = Elevator.MetersToTicks(RobotMath.FeetToMeters(19.0 / 12.0) - HATCH_TO_BOTTOM);
        public static final double CARGO_TO_BOTTOM = RobotMath.FeetToMeters(13.0 / 12.0);
        public static final int ROCKET_PORT_LEVEL_THREE = Elevator.MetersToTicks(2.12 - CARGO_TO_BOTTOM);
        public static final int ROCKET_PORT_LEVEL_TWO = Elevator.MetersToTicks(1.41 - CARGO_TO_BOTTOM);
        public static final int ROCKET_PORT_LEVEL_ONE = Elevator.MetersToTicks(0.70 - CARGO_TO_BOTTOM);
        public static final int CARGO_SHIP_PORT = Elevator.MetersToTicks(RobotMath.FeetToMeters(49.0 / 12.0) - CARGO_TO_BOTTOM);
    }
}
