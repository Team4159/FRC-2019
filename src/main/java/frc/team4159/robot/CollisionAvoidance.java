package frc.team4159.robot;


public class CollisionAvoidance {
    // TODO: Verify numbers
    // Heights in Elevator Ticks
    public static final double kRaiserMinDangerousHeight = RobotMath.MeterstoTicks(0.3, Constants.ELEVATOR_SPROCKET_RADIUS, Constants.TICKS_PER_REV);
    public static final double kRaiserMaxDangerousHeight = RobotMath.MeterstoTicks(0.4, Constants.ELEVATOR_SPROCKET_RADIUS, Constants.TICKS_PER_REV);
    public static final double kFeederMinDangerousHeight = RobotMath.MeterstoTicks(0.4, Constants.ELEVATOR_SPROCKET_RADIUS, Constants.TICKS_PER_REV);
    public static final double kFeederMaxDangerousHeight = RobotMath.MeterstoTicks(0.6, Constants.ELEVATOR_SPROCKET_RADIUS, Constants.TICKS_PER_REV);

    // Feeder positions in Feeder Ticks
    public static final int kFeederPositionStaying = Constants.FEEDER_DOWN_TICKS / 2;
    public static final int kFeederPositionStayingBuffer = Constants.FEEDER_DOWN_TICKS / 2 - 100;

    // Returns true if it is safe for Feeder to be up
    public static boolean safeFeederUp(int elevator_position, int elevator_goal) {
         return elevatorAboveFeederUp(elevator_position, elevator_goal) || elevatorUnderFeederUp(elevator_position, elevator_goal);
    }

    // Returns true if it is safe to move Elevator
    public static boolean safeMoveElevator(int elevator_position, int elevator_goal, int feeder_position) {
        return safeFeederUp(elevator_position, elevator_goal) || feeder_position >= kFeederPositionStaying;
    }

    // Returns true if it is safe for raiser (Nose) to be up
    public static boolean getRaiserSafeState(int elevator_position, int elevator_goal) {
        return elevator_position < kRaiserMinDangerousHeight
                && elevator_goal < kRaiserMinDangerousHeight;
    }

    // Returns true if Elevator is under Feeder's minimum height
    private static boolean elevatorUnderFeederUp(int elevator_position, int elevator_goal) {
        return elevator_position < kFeederMinDangerousHeight
                && elevator_goal < kFeederMinDangerousHeight;
    }

    // Returns true if Elevator is above Feeder's maximum height
    private static boolean elevatorAboveFeederUp(int elevator_position, int elevator_goal) {
        return elevator_goal > kFeederMaxDangerousHeight
                && elevator_position > kFeederMaxDangerousHeight;
    }
}
