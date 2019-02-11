package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
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

    /*
     * We use the Thrustmaster T.16000M joysticks.
     * See: http://ts.thrustmaster.com/download/accessories/manuals/T16000M/T16000M-User_manual.pdf
     *      for joystick mappings
     */
    private Joystick leftJoy, rightJoy, secondaryJoy;

    private OI() {

        leftJoy = new Joystick(Constants.getInt("LEFT_JOY"));
        rightJoy = new Joystick(Constants.getInt("RIGHT_JOY"));
        secondaryJoy = new Joystick(Constants.getInt("SECONDARY_JOY"));

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

        return secondaryJoy.getRawButtonPressed(Constants.getInt("HATCH_BUTTON"));

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

      return xbox.getXButtonPressed();

    }
  
    public boolean getGrabberOuttake() {

      return xbox.getYButtonPressed();

    
    }

    public boolean extenderButtonPressed() {

        return leftJoy.getRawButtonPressed(Constants.getInt("EXTENDER_BUTTON"));

    }

    public boolean peckerButtonPressed() {

        return xbox.getRawButton(Constants.getInt("PECKER_BUTTON"));

    }
}
