package frc.team4159.robot;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj.Joystick;

import frc.team4159.robot.subsystems.*;

import static frc.team4159.robot.Constants.*;

public class RobotContainer {
    private final Drivetrain drivetrain = new Drivetrain();
    private final Elevator elevator = new Elevator();
    private final Feeder feeder = new Feeder();
    private final Grabber grabber = new Grabber();
    private final Nose nose = new Nose();

    private Joystick left_joy = new Joystick(Constants.CONTROLS.LEFT_JOY),
                     right_joy = new Joystick(Constants.CONTROLS.RIGHT_JOY),
                     secondary_joy = new Joystick(Constants.CONTROLS.SECONDARY_JOY);

    public RobotContainer() {
        configureButtonBindings();

        drivetrain.setDefaultCommand(
                new RunCommand(() -> drivetrain.setRawSpeeds(
                        left_joy.getY(), right_joy.getY()
                ), drivetrain));
        new InstantCommand(() -> elevator.setRawSpeed(ELEVATOR_CONSTANTS.ZEROING_SPEED), elevator).andThen(
                new WaitUntilCommand(elevator::isZeroed).andThen(() -> {
                    elevator.resetEncoder();
                    elevator.enable();
                })
        ).schedule();
        new InstantCommand(() -> feeder.setRawLifterSpeed(FEEDER_CONSTANTS.ZEROING_SPEED), feeder).andThen(
                new WaitUntilCommand(feeder::isZeroed).andThen(() -> {
                    feeder.resetEncoder();
                    feeder.enable();
                })
        ).schedule();
    }

    private void configureButtonBindings() {
        new JoystickButton(right_joy, Constants.CONTROLS.FLIP_ORIENTATION)
                .whenPressed(drivetrain::flipOrientation);

        new JoystickButton(secondary_joy, CONTROLS.ELEVATOR_MANUAL_CONTROL)
                .whenHeld(
                        new InstantCommand(elevator::disable)
                        .andThen(new RunCommand(() -> elevator.setRawSpeed(secondary_joy.getY()), elevator)
                ))
                .whenReleased(elevator::enable);

        new JoystickButton(secondary_joy, CONTROLS.ELEVATOR_TO_CARGO_SHIP_HATCH)
                .whenPressed(() -> elevator.setSetpoint(ELEVATOR_CONSTANTS.CARGO_SHIP_HATCH));
        new JoystickButton(secondary_joy, CONTROLS.ELEVATOR_TO_CARGO_SHIP_PORT)
                .whenPressed(() -> elevator.setSetpoint(ELEVATOR_CONSTANTS.CARGO_SHIP_PORT));
        new JoystickButton(secondary_joy, CONTROLS.ELEVATOR_TO_ROCKET_HATCH_LEVEL_TWO)
                .whenPressed(() -> elevator.setSetpoint(ELEVATOR_CONSTANTS.ROCKET_HATCH_LEVEL_TWO));
        new JoystickButton(secondary_joy, CONTROLS.ELEVATOR_TO_ROCKET_HATCH_LEVEL_THREE)
                .whenPressed(() -> elevator.setSetpoint(ELEVATOR_CONSTANTS.ROCKET_HATCH_LEVEL_THREE));
        new JoystickButton(secondary_joy, CONTROLS.ELEVATOR_TO_ROCKET_PORT_LEVEL_ONE)
                .whenPressed(() -> elevator.setSetpoint(ELEVATOR_CONSTANTS.ROCKET_PORT_LEVEL_ONE));
        new JoystickButton(secondary_joy, CONTROLS.ELEVATOR_TO_ROCKET_PORT_LEVEL_TWO)
                .whenPressed(() -> elevator.setSetpoint(ELEVATOR_CONSTANTS.ROCKET_PORT_LEVEL_TWO));
        new JoystickButton(secondary_joy, CONTROLS.ELEVATOR_TO_ROCKET_PORT_LEVEL_THREE)
                .whenPressed(() -> elevator.setSetpoint(ELEVATOR_CONSTANTS.ROCKET_PORT_LEVEL_THREE));

        new JoystickButton(secondary_joy, CONTROLS.LOWER_FEEDER)
                .whenPressed(() -> feeder.setSetpoint(FEEDER_CONSTANTS.FEEDER_DOWN));
        new JoystickButton(secondary_joy, CONTROLS.RAISE_FEEDER)
                .whenPressed(() -> feeder.setSetpoint(FEEDER_CONSTANTS.FEEDER_UP));

        new JoystickButton(secondary_joy, CONTROLS.INTAKE_CARGO)
                .whenPressed(new InstantCommand(() -> {
                    feeder.intakeCargo();
                    grabber.intakeCargo();
                }, feeder, grabber))
                .whenReleased(new InstantCommand(() -> {
                    feeder.stopIntaking();
                    grabber.stopTaking();
                }, feeder, grabber));
        new JoystickButton(secondary_joy, CONTROLS.OUTTAKE_CARGO)
                .whenPressed(new InstantCommand(grabber::outtakeCargo, grabber))
                .whenReleased(new InstantCommand(grabber::stopTaking, grabber));

        new JoystickButton(secondary_joy, CONTROLS.TOGGLE_HOOKS)
                .whenPressed(new ConditionalCommand(
                        new InstantCommand(nose::releaseHatch, nose),
                        new InstantCommand(nose::grabHatch, nose),
                        nose::isGrabbing
                ));
        new JoystickButton(secondary_joy, CONTROLS.TOGGLE_RAISER)
                .whenPressed(new ConditionalCommand(
                        new InstantCommand(nose::lowerIntake, nose),
                        new InstantCommand(nose::raiseIntake, nose),
                        nose::isRaised
                ));
    }
}
