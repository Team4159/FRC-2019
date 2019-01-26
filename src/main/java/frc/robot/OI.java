/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


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
    private Joystick leftJoy, rightJoy;
    private static XboxController xbox;


    public static OI getInstance() {
        if(instance == null)
            instance = new OI();
        return instance;
    }

    public static boolean getSolenoids() {
        return xbox.getRawButton(0);
    }


    private OI() {
        leftJoy = new Joystick(Constants.getInt("LEFT_JOY"));
        rightJoy = new Joystick(Constants.getInt("RIGHT_JOY"));
        xbox = new XboxController(Constants.getInt("XBOX"));
    }

    public boolean hatchOpenButtonPressed() {
        return rightJoy.getRawButtonPressed(4);
    }

    public boolean hatchCloseButtonPressed() {
        return rightJoy.getRawButtonPressed(5);
    }

    public double getLeftY() {
        return leftJoy.getY(); // in future square it
    }

    public double getRightY() {
        return rightJoy.getY();
    }

    public double getXboxRightStick() {
        return xbox.getY(GenericHID.Hand.kRight);
    }

    public double getCargoIntake() {
        return xbox.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    public double getCargoOuttake() {
        return xbox.getTriggerAxis(GenericHID.Hand.kRight);
    }

    //Random numbers for button IDs (temporary)
    public boolean getFeederIntake() {
        return xbox.getRawButtonPressed(2);
    }

    public boolean getFeederOuttake() {
        return xbox.getRawButtonPressed(3);
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
