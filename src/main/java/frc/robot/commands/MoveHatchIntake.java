package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.HatchIntake;

public class MoveHatchIntake extends Command {
    private OI oi;
    private HatchIntake hatchIntake;

    public MoveHatchIntake() {
        hatchIntake = HatchIntake.getInstance();
        oi = OI.getInstance();

        requires(hatchIntake);
    }

    @Override
    protected void execute() {
        if (OI.getSolenoids()) {
            hatchIntake.outSolenoids();
        } else {
            hatchIntake.inSolenoids();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        hatchIntake.inSolenoids();
    }
}
