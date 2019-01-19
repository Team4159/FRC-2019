package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Elevator;

public class ElevatorControl extends Command {

    private Elevator elevator;
    private OI oi;

    public ElevatorControl() {

        elevator = Elevator.getInstance();
        oi = OI.getInstance();

        requires(elevator);
    }



    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
