package frc.robot.state;

public class OrientationState {

    public enum Orientation {
        Front,
        Back
    }

    private static Orientation state = Orientation.Front;

    public static void toggleState() {
        if (state == Orientation.Front) {
            state = Orientation.Back;
        } else {
            state = Orientation.Front;
        }
    }

    public static Orientation getState() {
        return state;
    }

}
