package frc.robot.commands.extender;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Extender;

public class ExtenderControl extends Command {

    private Extender extender;
    private OI oi;

    public ExtenderControl() {

        extender = Extender.getInstance();
        oi = OI.getInstance();

        requires(extender);

    }

    @Override
    protected void execute() {

        if (oi.getExtender()) {

            if(extender.getValue() == DoubleSolenoid.Value.kForward) {
                extender.in();

            } else if (extender.getValue() == DoubleSolenoid.Value.kReverse) {
                extender.out();

            } else {
                extender.out();
            }
        }

    }

    @Override
    protected boolean isFinished() {

        return false;

    }

    @Override
    protected void end() {

        extender.in();

    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }
}
