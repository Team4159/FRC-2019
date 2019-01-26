package frc.robot.commands.grabber;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.subsystems.Grabber;

public class OuttakeGrabber extends TimedCommand {

    private Grabber grabber;

    public OuttakeGrabber(double timeout) {

        super(timeout);

        grabber = Grabber.getInstance();
        requires(grabber);
    }

    @Override
    protected void execute() {

        grabber.outtake();

    }

    @Override
    protected boolean isFinished() {

        return isTimedOut();

    }

    @Override
    protected void end() {

        grabber.stop();

    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }
}
