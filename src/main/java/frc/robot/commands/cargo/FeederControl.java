package frc.robot.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Feeder;

public class FeederControl extends Command {

    private Feeder feeder;
    private OI oi;

    public FeederControl() {

        feeder = Feeder.getInstance();
        oi = OI.getInstance();

        requires(feeder);
    }

    @Override
    protected void execute() {
        if (oi.getFeederIntake()) {
            feeder.intake();
        } else
        if (oi.getFeederOuttake()) {
            feeder.outtake();
        } else {
            feeder.stop();
        }
    }

    @Override
    protected boolean isFinished() {

        return false;

    }

    @Override
    protected void end() {

        feeder.stop();

    }

    @Override
    protected void interrupted() {
        end();
    }
}
