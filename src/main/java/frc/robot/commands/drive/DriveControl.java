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

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        drivetrain.rawDrive(oi.getLeftY(), oi.getRightY());

        drivetrain.logDashboard();

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
