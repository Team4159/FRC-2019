package frc.robot.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.CargoExtender;

public class ExtenderControl extends Command {
    private CargoExtender extender;
    private OI oi;

    public ExtenderControl() {

        extender = CargoExtender.getInstance();
        oi = OI.getInstance();

        requires(extender);

    }

    @Override
    protected void execute() {
        if (oi.getExtender()) {
            extender.out();
        } else {
            extender.in();
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
        end();
    }
}
