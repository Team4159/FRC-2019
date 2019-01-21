package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Drivetrain;

public class DriveControl extends Command {

    private Drivetrain drivetrain;
    private OI oi;

    public DriveControl() {

        drivetrain = Drivetrain.getInstance();
        oi = OI.getInstance();

        requires(drivetrain);
    }

    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        //drivetrain.setState();
    }

    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {

        drivetrain.rawDrive(oi.getLeftY(), oi.getRightY());

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

        drivetrain.stop();

    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
