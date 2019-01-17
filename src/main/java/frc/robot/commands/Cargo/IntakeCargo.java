package frc.robot.commands.Cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Cargo;

public class IntakeCargo extends Command {
    private Cargo cargo;


    public IntakeCargo(double timeout) {
        super(timeout);
        cargo = Cargo.getInstance();
        requires(cargo);
    }

    @Override
    protected void execute() {
        cargo.intakeCargo();
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
