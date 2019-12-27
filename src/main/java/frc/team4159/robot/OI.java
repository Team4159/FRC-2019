package frc.team4159.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
    private static OI instance;

    public static OI getInstance() {
        if (instance == null)
            instance = new OI();
        return instance;
    }

    private Joystick left_joy, right_joy, secondary_joy;

    private OI() {
        left_joy = new Joystick(0);
        right_joy = new Joystick(1);
        secondary_joy = new Joystick(2);
    }

    public Joystick getLeftJoy() {
        return left_joy;
    }

    public Joystick getRightJoy() {
        return right_joy;
    }

    public Joystick getSecondaryJoy() {
        return secondary_joy;
    }
}
