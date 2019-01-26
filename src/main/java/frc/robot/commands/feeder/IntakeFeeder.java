package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.subsystems.Feeder;

public class IntakeFeeder extends TimedCommand {

    private Feeder feeder;

    public IntakeFeeder(double timeout) {

        super(timeout);

        feeder = Feeder.getInstance();
        requires(feeder);
    }

    @Override
    protected void execute() {

        feeder.intake();

    }

    @Override
    protected boolean isFinished() {

        return isTimedOut();

    }

    @Override
    protected void end() {

        feeder.stop();

    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }
}
