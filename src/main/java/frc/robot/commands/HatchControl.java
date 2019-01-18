package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Hatch;

public class HatchControl extends Command {

    private OI oi;
    private Hatch hatch;

    public HatchControl() {

        hatch = Hatch.getInstance();
        oi = OI.getInstance();

        requires(hatch);
    }

    @Override
    protected void execute() {

        if (oi.getSolenoid1()) {
            hatch.outSolenoid1();

        } else {
            hatch.inSolenoid1();
        }

        if (oi.getSolenoid2()) {
            hatch.outSolenoid2();

        } else {
            hatch.inSolenoid2();
        }

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        hatch.inSolenoid1();
        hatch.inSolenoid2();
    }
}
