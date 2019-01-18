package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.util.Constants;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    private static OI instance;

    public static OI getInstance() {
        if(instance == null)
            instance = new OI();
        return instance;
    }

    private Joystick leftJoy, rightJoy;
    private XboxController xbox;
    private Constants constants;

    private OI() {
        leftJoy = new Joystick(constants.getInt("LEFT_JOY"));
        rightJoy = new Joystick(constants.getInt("RIGHT_JOY"));
        xbox = new XboxController(constants.getInt("XBOX"));
    }

    public double getLeftY() {
        return leftJoy.getY();
    }

    public double getRightY() {
        return rightJoy.getY();
    }

}
