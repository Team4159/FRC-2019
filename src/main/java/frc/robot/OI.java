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
     * We use Thrustmaster T.16000M joysticks.
     * See: http://ts.thrustmaster.com/download/accessories/manuals/T16000M/T16000M-User_manual.pdf
     *      for joystick mappings
     */
    private Joystick leftJoy, rightJoy, secondaryJoy;

    private OI() {

        leftJoy = new Joystick(Constants.getInt("LEFT_JOY"));
        rightJoy = new Joystick(Constants.getInt("RIGHT_JOY"));
        secondaryJoy = new Joystick(Constants.getInt("SECONDARY_JOY"));

    }

    /**
     * @return Left joystick y-axis value squared with original sign
     */
    public double getLeftY() {

        return Math.copySign(Math.pow(leftJoy.getY(), 2), leftJoy.getY());

    }

    /**
     * @return Right joystick y-axis value squared with original sign
     */
    public double getRightY() {

        return Math.copySign(Math.pow(rightJoy.getY(), 2), rightJoy.getY());

    }

    public boolean alignButtonHeld() {

        return rightJoy.getRawButton(Constants.getInt("ALIGN_BUTTON"));

    }

    public double getSecondaryY() {

        return secondaryJoy.getY();

    }

    public boolean cargoIntakeButtonHeld() {

      return secondaryJoy.getRawButton(Constants.getInt("CARGO_INTAKE_BUTTON"));

    }
  
    public boolean cargoOuttakeButtonHeld() {

      return secondaryJoy.getRawButton(Constants.getInt("CARGO_OUTTAKE_BUTTON"));
      
    }

    public boolean extenderButtonPressed() {

        return secondaryJoy.getRawButtonPressed(Constants.getInt("EXTENDER_BUTTON"));
    }


    public boolean peckerButtonPressed() {

        return secondaryJoy.getRawButtonPressed(Constants.getInt("PECKER_BUTTON"));

    }

    public boolean beakButtonPressed() {

        return secondaryJoy.getRawButtonPressed(Constants.getInt("BEAK_BUTTON"));

    }

    public boolean elevatorBotHatchButtonPressed() {

        return secondaryJoy.getRawButtonPressed(Constants.getInt("ELEVATOR_BOT_HATCH_BUTTON"));

    }

    public boolean elevatorMidHatchButtonPressed() {

        return secondaryJoy.getRawButtonPressed(Constants.getInt("ELEVATOR_MID_HATCH_BUTTON"));

    }

    public boolean elevatorTopHatchButtonPressed() {

        return secondaryJoy.getRawButtonPressed(Constants.getInt("ELEVATOR_TOP_HATCH_BUTTON"));

    }

    public boolean elevatorBotCargoButtonPressed() {

        return secondaryJoy.getRawButtonPressed(Constants.getInt("ELEVATOR_BOT_CARGO_BUTTON"));

    }

    public boolean elevatorMidCargoButtonPressed() {

        return secondaryJoy.getRawButtonPressed(Constants.getInt("ELEVATOR_MID_CARGO_BUTTON"));

    }

    public boolean elevatorTopCargoButtonPressed() {

        return secondaryJoy.getRawButtonPressed(Constants.getInt("ELEVATOR_TOP_CARGO_BUTTON"));

    }

    public boolean getCameraState() {
      
        return secondaryJoy.getRawButtonPressed(Constants.getInt("CAMERA_TOGGLE_BUTTON"));
      
    }
}
