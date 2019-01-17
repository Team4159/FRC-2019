package frc.robot.commands.Cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Cargo;

public class ControlCargo extends Command {
    private Cargo cargo;
    private OI oi;

    public ControlCargo() {
        cargo = Cargo.getInstance();
        oi = OI.getInstance();

        requires(cargo);
    }

    @Override
    protected void execute() {
        if (oi.getCargoIntakeButton() && oi.getCargoOuttakeButton()) {
            cargo.stopCargo();
        } else if (oi.getCargoIntakeButton()) {
            cargo.intakeCargo();
        } else if (oi.getCargoOuttakeButton()) {
            cargo.outtakeCargo();
        } else {
            cargo.stopCargo();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
