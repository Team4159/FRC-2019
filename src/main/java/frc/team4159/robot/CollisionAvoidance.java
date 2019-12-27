package frc.team4159.robot;

public class CollisionAvoidance {
    // Returns whether or not it's safe for the feeder to be up
    public static boolean feederSafeToBeUp(int elevator_position, int elevator_goal) {
         return elevatorNotMovingUpThroughFeeder(elevator_position, elevator_goal)
                 || elevatorNotMovingDownThroughFeeder(elevator_position, elevator_goal);
    }

    // Returns whether or not it's safe for the raiser to be up
    public static boolean raiserSafeToBeUp(int elevator_position, int elevator_goal) {
        /*
        return elevatorNotMovingUpThroughRaiser(elevator_position, elevator_goal)
                   || elevatorNotMovingDownThroughRaiser(elevator_position, elevator_goal);
         */
        return elevator_position < Constants.NUMS.FEEDER_DANGEROUS_LOWER_BAND && elevator_goal < Constants.NUMS.FEEDER_DANGEROUS_LOWER_BAND;
    }

    // Returns whether or not it is safe to move the elevator
    public static boolean safeToMoveElevator(int elevator_position, int elevator_goal, int feeder_position, boolean raiser_extended) {
        boolean feeder_up = feeder_position >= (Constants.NUMS.FEEDER_STOWED + 100);
        if (!feederSafeToBeUp(elevator_position, elevator_goal) && feeder_up) {
            return false;
        }
        if (!raiserSafeToBeUp(elevator_position, elevator_goal) && !raiser_extended) {
            return false;
        }
        return true;
    }

    // Returns whether or not the elevator is moving up through the feeder
    private static boolean elevatorNotMovingUpThroughFeeder(int elevator_position, int elevator_goal) {
        return elevator_position < Constants.NUMS.FEEDER_DANGEROUS_LOWER_BAND
                && elevator_goal < Constants.NUMS.FEEDER_DANGEROUS_LOWER_BAND;
    }

    // Returns whether or not the elevator is moving down through the feeder
    private static boolean elevatorNotMovingDownThroughFeeder(int elevator_position, int elevator_goal) {
        return elevator_goal > Constants.NUMS.FEEDER_DANGEROUS_HIGHER_BAND
                && elevator_position > Constants.NUMS.FEEDER_DANGEROUS_HIGHER_BAND;
    }

    // Returns whether or not the elevator is moving up through the raiser
    private static boolean elevatorNotMovingUpThroughRaiser(int elevator_position, int elevator_goal) {
        return elevator_position < Constants.NUMS.RAISER_DANGEROUS_LOWER_BAND
                && elevator_goal < Constants.NUMS.RAISER_DANGEROUS_LOWER_BAND;
    }

    // Returns whether or not the elevator is moving down through the feeder
    private static boolean elevatorNotMovingDownThroughRaiser(int elevator_position, int elevator_goal) {
        return elevator_goal > Constants.NUMS.RAISER_DANGEROUS_HIGHER_BAND
                && elevator_position > Constants.NUMS.RAISER_DANGEROUS_HIGHER_BAND;
    }
}
