package frc.robot.commands.grabber;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.subsystems.Grabber;

public class IntakeGrabber extends TimedCommand {

    private Grabber grabber;

    public IntakeGrabber(double timeout) {

        super(timeout);

        grabber = Grabber.getInstance();
        requires(grabber);
    }

    @Override
    protected void execute() {

        grabber.intake();

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
