package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
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
    private static XboxController xbox;

    private OI() {

        leftJoy = new Joystick(Constants.getInt("LEFT_JOY"));
        rightJoy = new Joystick(Constants.getInt("RIGHT_JOY"));
        xbox = new XboxController(Constants.getInt("XBOX"));

    }



    public double getLeftY() {

        return leftJoy.getY(); // in future square it

    }

    public double getRightY() {

        return rightJoy.getY();

    }

    public boolean hatchOpenButtonPressed() {

        return xbox.getRawButtonPressed(4);

    }

    public boolean hatchCloseButtonPressed() {

        return xbox.getRawButtonPressed(5);

    }

    public double getXboxRightStick() {

        return xbox.getY(GenericHID.Hand.kRight);

    }

    public boolean getFeederIntake() {

        return xbox.getRawButton(3);
    }

    public boolean getFeederOuttake() {

        return xbox.getRawButton(4);

    }

    public boolean getGrabberIntake() {

      return xbox.getXButtonPressed();

    }
  
    public boolean getGrabberOuttake() {

      return xbox.getYButtonPressed();

    
    }
    public boolean getExtender() {

        return xbox.getRawButtonPressed(4);

    }
}
