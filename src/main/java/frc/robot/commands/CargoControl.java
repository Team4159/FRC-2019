package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Cargo;

public class CargoControl extends Command {
    private Cargo cargo;
    private OI oi;

    public CargoControl() {
        cargo = Cargo.getInstance();
        oi = OI.getInstance();

        requires(cargo);
    }

    @Override
    protected void execute() {
        if (oi.getCargoIntake() > 0 && oi.getCargoOuttake() > 0) {
            cargo.stopCargo();
        } else if (oi.getCargoIntake() > 0) {
            cargo.intakeCargo();
        } else if (oi.getCargoOuttake() > 0) {
            cargo.outtakeCargo();
        } else {
            cargo.stopCargo();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() { cargo.stopCargo(); }

    @Override
    protected void interrupted() { end(); }
}
