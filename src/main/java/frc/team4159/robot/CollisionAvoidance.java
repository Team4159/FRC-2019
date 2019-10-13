package frc.team4159.robot;


public class CollisionAvoidance {
    // TODO: Verify numbers
    // Heights in Elevator Ticks
    public static final double kRaiserMinDangerousHeight = RobotMath.MeterstoTicks(0.3, Constants.ELEVATOR_SPROCKET_RADIUS, Constants.TICKS_PER_REV);
    public static final double kRaiserMaxDangerousHeight = RobotMath.MeterstoTicks(0.4, Constants.ELEVATOR_SPROCKET_RADIUS, Constants.TICKS_PER_REV);
    public static final double kFeederMinDangerousHeight = RobotMath.MeterstoTicks(0.4, Constants.ELEVATOR_SPROCKET_RADIUS, Constants.TICKS_PER_REV);
    public static final double kFeederMaxDangerousHeight = RobotMath.MeterstoTicks(0.6, Constants.ELEVATOR_SPROCKET_RADIUS, Constants.TICKS_PER_REV);

    public static final double kFeederStayingPosition = -1536;

    public static boolean safeFeederUp(int elevator_position, int elevator_goal) {
         return elevatorAboveFeederUp(elevator_position, elevator_goal) || elevatorUnderFeederUp(elevator_position, elevator_goal);
    }

    public static boolean safeMoveElevator(int elevator_position, int elevator_goal, int feeder_position) {
        return safeFeederUp(elevator_position, elevator_goal) || feeder_position >= 1536;
    }

    public static boolean getRaiserSafeState(int elevator_position, int elevator_goal) {
        return elevator_position < kRaiserMinDangerousHeight
                && elevator_goal < kRaiserMinDangerousHeight;
    }

    private static boolean elevatorUnderFeederUp(int elevator_position, int elevator_goal) {
        return elevator_position < kFeederMinDangerousHeight
                && elevator_goal < kFeederMinDangerousHeight;
    }

    private static boolean elevatorAboveFeederUp(int elevator_position, int elevator_goal) {
        return elevator_goal > kFeederMaxDangerousHeight
                && elevator_position > kFeederMaxDangerousHeight;
    }
}
