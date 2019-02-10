package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Beak;

public class HatchControl extends Command {

    private OI oi;
    private Beak beak;

    public HatchControl() {

        beak = Beak.getInstance();
        oi = OI.getInstance();
        requires(beak);

    }

    @Override
    protected void execute() {

        if(oi.hatchButtonPressed()) {

            if(beak.getValue() == DoubleSolenoid.Value.kReverse) {
                beak.intake();

            } else if (beak.getValue() == DoubleSolenoid.Value.kForward) {
                beak.outtake();

            } else {
                beak.outtake();

            }
        }

    }

    @Override
    protected boolean isFinished() {

        return false;

    }

    @Override
    protected void end() {

        beak.close();

    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }
}
