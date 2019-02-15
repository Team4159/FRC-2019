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
    private XboxController xbox;

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

    public boolean getRightTrigger() {

        return rightJoy.getTrigger();

    }

    public boolean hatchButtonPressed() {

        return xbox.getRawButtonPressed(Constants.getInt("HATCH_BUTTON"));

    }

    public double getXboxRightStick() {

        return xbox.getY(GenericHID.Hand.kRight);

    }

    public boolean getFeederIntake() {

        return xbox.getRawButton(Constants.getInt("FEEDER_IN_BUTTON"));
    }

    public boolean getFeederOuttake() {

        return xbox.getRawButton(Constants.getInt("FEEDER_OUT_BUTTON"));

    }

    public boolean getGrabberIntake() {

      return xbox.getRawButton(Constants.getInt("GRABBER_IN_BUTTON"));

    }
  
    public boolean getGrabberOuttake() {

      return xbox.getRawButton(Constants.getInt("GRABBER_OUT_BUTTON"));

    
    }

    public boolean extenderButtonPressed() {

        return xbox.getRawButtonPressed(Constants.getInt("EXTENDER_BUTTON"));
    }

    public boolean getAligner() {

        return xbox.getRawButtonPressed(Constants.getInt("ALIGNER_BUTTON"));

    }

    public boolean peckerButtonPressed() {

        return xbox.getRawButton(Constants.getInt("PECKER_BUTTON"));

    }

    public boolean getCameraState() {

        return xbox.getRawButtonPressed(Constants.getInt("CAMERA_TOGGLE_BUTTON"));

    }
}
