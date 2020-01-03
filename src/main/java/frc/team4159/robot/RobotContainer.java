package frc.team4159.robot;

import frc.team4159.robot.subsystems.Drivetrain;
import frc.team4159.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class RobotContainer {
    private final Drivetrain drivetrain = new Drivetrain();
    private final Elevator elevator = new Elevator();

    private Joystick left_joy = new Joystick(Constants.CONTROLS.LEFT_JOY),
                     right_joy = new Joystick(Constants.CONTROLS.RIGHT_JOY),
                     secondary_joy = new Joystick(Constants.CONTROLS.SECONDARY_JOY);

    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        // Configure default commands
        // Set the default drive command to split-stick arcade drive
        m_robotDrive.setDefaultCommand(
                // A split-stick arcade command, with forward/backward controlled by the left
                // hand, and turning controlled by the right.
                new RunCommand(() -> m_robotDrive
                        .arcadeDrive(m_driverController.getY(GenericHID.Hand.kLeft),
                                m_driverController.getX(GenericHID.Hand.kRight)), m_robotDrive));

    }

    private void configureButtonBindings() {

    }
}
