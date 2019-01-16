package frc.robot.commands.Cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Cargo;

public class IntakeCargo extends Command {
    private Cargo cargo;

    IntakeCargo() {
        super();
        cargo = Cargo.getInstance();
    }
    //TODO:Do we need a timeout in constructor?
    /*IntakeCargo(double timeout) {
        super(timeout);
        cargo = Cargo.getInstance();
    }
    Maybe use a timeout constructor?*/

    @Override
    protected void execute() {
        //TODO:Determine what speed to intake and outtake with.
        cargo.setSpeed(1.0);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
