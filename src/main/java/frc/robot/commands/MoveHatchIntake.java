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
    }

    @Override
    protected void execute() {
        if (OI.getSolenoid1()) {
            hatchIntake.outSolenoid1();
        } else {
            hatchIntake.inSolenoid1();
        }

        if (OI.getSolenoid2()) {
            hatchIntake.outSolenoid2();
        } else {
            hatchIntake.inSolenoid2();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        hatchIntake.inSolenoid1();
        hatchIntake.inSolenoid2();
    }
}
