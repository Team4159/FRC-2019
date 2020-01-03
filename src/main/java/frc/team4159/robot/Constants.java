package frc.team4159.robot;

import frc.team4159.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.util.Units;

public class Constants {
    // Controls for robot
    public static class CONTROLS {
        public static final int LEFT_JOY = 0;
        public static final int RIGHT_JOY = 1;
        public static final int SECONDARY_JOY = 2;
        
        public static final int FLIP_ORIENTATION = 2;

        public static final int INTAKE_CARGO = 7;
        public static final int OUTTAKE_CARGO = 8;

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
    public static class PORTS {
        // TODO: Check
        public static int LEFT_MASTER_TALON = 6;
        public static int LEFT_SLAVE_TALON = 14;
        public static int RIGHT_MASTER_TALON = 13;
        public static int RIGHT_SLAVE_TALON = 16;

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
    }

    // Constants for the elevator
    public static class ELEVATOR_CONSTANTS {
        public static final double SPROCKET_RADIUS = Units.feetToMeters(1.751 / 12.0);
        public static final double SPROCKET_CIRCUMFERENCE = SPROCKET_RADIUS * Math.PI * 2;
        public static final int TICKS_PER_REV = 4096;

        public static final double HATCH_TO_BOTTOM = Units.feetToMeters(19.0 / 12.0);
        public static final int ROCKET_HATCH_LEVEL_THREE = Elevator.metersToTicks(1.90 - HATCH_TO_BOTTOM);
        public static final int ROCKET_HATCH_LEVEL_TWO = Elevator.metersToTicks(1.19 - HATCH_TO_BOTTOM);
        public static final int ROCKET_HATCH_LEVEL_ONE = Elevator.metersToTicks(0.48 - HATCH_TO_BOTTOM);
        public static final int CARGO_SHIP_HATCH = Elevator.metersToTicks(Units.feetToMeters(19.0 / 12.0) - HATCH_TO_BOTTOM);
        public static final double CARGO_TO_BOTTOM = Units.feetToMeters(13.0 / 12.0);
        public static final int ROCKET_PORT_LEVEL_THREE = Elevator.metersToTicks(2.12 - CARGO_TO_BOTTOM);
        public static final int ROCKET_PORT_LEVEL_TWO = Elevator.metersToTicks(1.41 - CARGO_TO_BOTTOM);
        public static final int ROCKET_PORT_LEVEL_ONE = Elevator.metersToTicks(0.70 - CARGO_TO_BOTTOM);
        public static final int CARGO_SHIP_PORT = Elevator.metersToTicks(Units.feetToMeters(49.0 / 12.0) - CARGO_TO_BOTTOM);

        public static final double kP = 0.12;
        public static final double kI = 0.0;
        public static final double kD = 4.0;

        public static final double ZEROING_SPEED = -0.3;
        public static final int TOLERANCE = Elevator.metersToTicks(0.01);
    }

    // Constants for the feeder
    public static class FEEDER_CONSTANTS {
        public static final double kP = 1.0;
        public static final double kI = 0.0;
        public static final double kD = 20.0;

        public static final int FEEDER_UP = 0;
        public static final int FEEDER_DOWN = -2960;
        public static final int FEEDER_STOWED = FEEDER_DOWN / 2;

        public static final double ZEROING_SPEED = 0.4;
        public static final int TOLERANCE = 0;
    }

    // Constants for the grabber
    public static class GRABBER_CONSTANTS {
        public static final double IDLE_SPEED = -0.15;
    }
}
