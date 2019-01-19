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


public class OI {

    private static OI instance;
    private Joystick leftJoy, rightJoy;
    private static XboxController xbox;

    public static OI getInstance() {
        if(instance == null)
            instance = new OI();
        return instance;
    }

    private OI() {
        leftJoy = new Joystick(Constants.getInt("LEFT_JOY"));
        rightJoy = new Joystick(Constants.getInt("RIGHT_JOY"));
        xbox = new XboxController(Constants.getInt("XBOX"));
    }

    public boolean getSolenoid1() {
        return xbox.getBumper(GenericHID.Hand.kLeft);
    }

    public boolean getSolenoid2() {
        return xbox.getBumper(GenericHID.Hand.kRight);
    }

    public double getLeftY() {
        return leftJoy.getY();
    }

    public double getRightY() {
        return rightJoy.getY();
    }
}
