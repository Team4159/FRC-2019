package frc.team4159.robot;

public class CollisionAvoidance {
    // Returns whether or not it's safe for the feeder to be up
    public static boolean feederSafeToBeUp(int elevator_position, int elevator_goal) {
         return elevatorMovingUpThroughFeeder(elevator_position, elevator_goal)
                 || elevatorMovingDownThroughFeeder(elevator_position, elevator_goal);
    }

    // Returns whether or not it's safe for the raiser to be up
    public static boolean raiserSafeToBeUp(int elevator_position, int elevator_goal) {
        return elevatorMovingUpThroughRaiser(elevator_position, elevator_goal)
                || elevatorMovingDownThroughRaiser(elevator_position, elevator_goal);
    }

    // Returns whether or not it is safe to move the elevator
    public static boolean safeToMoveElevator(int elevator_position, int elevator_goal, int feeder_position, boolean raiser_extended) {
        boolean feeder_up = feeder_position >= (Constants.FEEDER_STOWED + 100);
        if (!feederSafeToBeUp(elevator_position, elevator_goal) && feeder_up) {
            return false;
        }
        if (!raiserSafeToBeUp(elevator_position, elevator_goal) && !raiser_extended) {
            return false;
        }
        return true;
    }

    // Returns whether or not the elevator is going to move upwards through the feeder’s range
    private static boolean elevatorMovingUpThroughFeeder(int elevator_position, int elevator_goal) {
        return elevator_position < Constants.FEEDER_DANGEROUS_LOWER_BAND
                && elevator_goal < Constants.FEEDER_DANGEROUS_LOWER_BAND;
    }

    // Returns whether or not the elevator is going to move downwards through the feeder’s range
    private static boolean elevatorMovingDownThroughFeeder(int elevator_position, int elevator_goal) {
        return elevator_goal > Constants.FEEDER_DANGEROUS_HIGHER_BAND
                && elevator_position > Constants.FEEDER_DANGEROUS_HIGHER_BAND;
    }

    // Returns whether or not the elevator is going to move upwards through the raiser’s range
    private static boolean elevatorMovingUpThroughRaiser(int elevator_position, int elevator_goal) {
        return elevator_position < Constants.RAISER_DANGEROUS_LOWER_BAND
                && elevator_goal < Constants.RAISER_DANGEROUS_LOWER_BAND;
    }

    // Returns whether or not the elevator is going to move downwards through the raiser’s range
    private static boolean elevatorMovingDownThroughRaiser(int elevator_position, int elevator_goal) {
        return elevator_goal > Constants.RAISER_DANGEROUS_HIGHER_BAND
                && elevator_position > Constants.RAISER_DANGEROUS_HIGHER_BAND;
    }
}
