package frc.robot.commands.grabber;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Grabber;


public class GrabberControl extends Command {

    private Grabber grabber;
    private OI oi;

    public GrabberControl() {

        grabber = Grabber.getInstance();
        oi = OI.getInstance();
        requires(grabber);

    }

    @Override
    protected void execute() {

        if (oi.cargoIntakeButtonHeld() == oi.cargoOuttakeButtonHeld()) {
            grabber.stop();

        } else if (oi.cargoOuttakeButtonHeld()) {
            grabber.outtake();

        } else if (oi.cargoIntakeButtonHeld()) {
            grabber.intake();

        } else {
            grabber.stop();

        }

    }


    @Override
    protected boolean isFinished() {

        return false;

    }


    @Override
    protected void end() {

        grabber.stop();

    }

    @Override
    protected void interrupted() {

        end();

    }
}
