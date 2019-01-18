/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


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

    private static Joystick secondaryJoy;

    public static OI getInstance() {
        if(instance == null)
            instance = new OI();
        return instance;
    }


    public static boolean getSolenoid1() {
        return secondaryJoy.getRawButton(0);
    }

    public static boolean getSolenoid2() {
        return secondaryJoy.getRawButton(1);
    }


    private Joystick leftJoy, rightJoy;
    private XboxController xbox;
    private Constants constants;

    private OI() {
        constants = Constants.getInstance();
      
        leftJoy = new Joystick(constants.getInt("LEFT_JOY"));
        rightJoy = new Joystick(constants.getInt("RIGHT_JOY"));
        secondaryJoy = new Joystick(constants.getInt("SECONDARY_JOY"));
        xbox = new XboxController(constants.getInt("XBOX"));
    }

    public double getLeftY() {
        return leftJoy.getY();
    }

    public double getRightY() {
        return rightJoy.getY();
    }
}
