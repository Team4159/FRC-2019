package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;


public class MoveElevator extends Command {

    private Elevator elevator;
    private int height;

    MoveElevator(int height) {

        this.height = height;
        elevator = Elevator.getInstance();
        requires(elevator);

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        elevator.checkLimitSwitch();
        elevator.setTargetPosition(height);

    }

    @Override
    protected boolean isFinished() {

        return elevator.isMotionMagicFinished();

    }

    @Override
    protected void end() {

        // This also effectively stops/cancels motion magic following
        elevator.setPercentOutput(0);

    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }

}
