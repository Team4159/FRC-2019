package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Vision;

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

        // Trigger held = auto align
        if(oi.getRightTrigger()) {

            double speed = (oi.getLeftY() + oi.getRightY()) / 2;
            // double turn = Vision.getInstance().getOffset() * Constants.getDouble("kP_ALIGN");
            // drivetrain.arcadeDrive(speed, turn);

        // Regular control
        } else {

            drivetrain.rawDrive(oi.getLeftY(), oi.getRightY());

        }


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
