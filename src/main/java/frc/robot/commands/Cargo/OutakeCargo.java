package frc.robot.commands.Cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Cargo;

public class OutakeCargo extends Command {
    private Cargo cargo;

    public OutakeCargo(double timeout) {
        super(timeout);
        cargo = Cargo.getInstance();
        requires(cargo);
    }

    @Override
    protected void execute() {
        //TODO:Determine what speed to outtake and outtake with.
        cargo.outtakeCargo();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        cargo.stopCargo();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
