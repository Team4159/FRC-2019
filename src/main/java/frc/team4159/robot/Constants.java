package frc.team4159.robot;

import frc.team4159.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.util.Units;

public class Constants {
    // Button IDs
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

    // Math-y Stuff
    public static class MATH {
        public static final double ELEVATOR_SPROCKET_RADIUS = Units.feetToMeters(1.751 / 12.0);
        public static final double ELEVATOR_SPROCKET_CIRCUMFERENCE = ELEVATOR_SPROCKET_RADIUS * Math.PI * 2;
        public static final int TICKS_PER_REV = 4096;
    }

    // Positions in ticks
    public static class POSITIONS {
        public static final int FEEDER_UP = 0;
        public static final int FEEDER_DOWN = -2960;
        public static final int FEEDER_STOWED = FEEDER_DOWN / 2;

        public static final double RAISER_DANGEROUS_LOWER_BAND = Elevator.metersToTicks(0.3);
        public static final double RAISER_DANGEROUS_HIGHER_BAND = Elevator.metersToTicks(0.4);

        public static final double FEEDER_DANGEROUS_LOWER_BAND = Elevator.metersToTicks(0.4);
        public static final double FEEDER_DANGEROUS_HIGHER_BAND = Elevator.metersToTicks(0.6);

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
    }
}
