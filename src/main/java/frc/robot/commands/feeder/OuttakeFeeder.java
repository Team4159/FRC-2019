package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.subsystems.Feeder;

public class OuttakeFeeder extends TimedCommand {

    private Feeder feeder;

    public OuttakeFeeder(double timeout) {

        super(timeout);

        feeder = Feeder.getInstance();
        requires(feeder);

    }

    @Override
    protected void execute() {

        feeder.outtake();

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
