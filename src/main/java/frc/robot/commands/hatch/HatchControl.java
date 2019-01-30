package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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

        if(oi.hatchButtonPressed()) {

            if(hatch.getValue() == DoubleSolenoid.Value.kReverse) {
                hatch.intake();

            } else if (hatch.getValue() == DoubleSolenoid.Value.kForward) {
                hatch.outtake();

            } else {
                hatch.outtake();

            }
        }

    }

    @Override
    protected boolean isFinished() {

        return false;

    }

    @Override
    protected void end() {

        hatch.close();

    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }
}
