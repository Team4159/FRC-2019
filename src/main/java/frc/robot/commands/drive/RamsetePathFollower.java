package frc.robot.commands.drive;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.enums.MotionProfileDirection;
import frc.robot.util.motion.DriveSignal;
import frc.robot.util.motion.RamseteFollower;
import jaci.pathfinder.Trajectory;

@SuppressWarnings("FieldCanBeLocal")
public class RamsetePathFollower extends Command {

    private RamseteFollower ramseteFollower;
    private DriveSignal driveSignal;
    private Trajectory.Segment current;
    private Drivetrain drivetrain = Drivetrain.getInstance();

    public RamsetePathFollower(Trajectory trajectory) {

        this(trajectory, MotionProfileDirection.FORWARD);

    }

    public RamsetePathFollower(Trajectory trajectory, MotionProfileDirection direction) {

        requires(drivetrain);
        ramseteFollower = new RamseteFollower(trajectory, direction);

    }

    public RamsetePathFollower(Trajectory trajectory, double b, double zeta, MotionProfileDirection direction) {

        requires(drivetrain);
        ramseteFollower = new RamseteFollower(trajectory, b, zeta, direction);

    }

    @Override
    protected void initialize() {
        // drivetrain.setBrakeMode();
        // Should be in brake mode all the time anyways
    }

    @Override
    protected void execute() {

        driveSignal = ramseteFollower.getNextDriveSignal();
        current = ramseteFollower.currentSegment();

        drivetrain.setVelocity(driveSignal.getLeft(), driveSignal.getRight(), current.acceleration);

//        OI.liveDashboardTable.getEntry("Path X").setNumber(current.x);
//        OI.liveDashboardTable.getEntry("Path Y").setNumber(current.y);

        SmartDashboard.putNumber("Path Left Wheel Velocity", driveSignal.getLeft());
        SmartDashboard.putNumber("Path Right Wheel Velocity", driveSignal.getRight());

        SmartDashboard.putNumber("Robot Left Velocity", drivetrain.getLeftVelocity());
        SmartDashboard.putNumber("Robot Right Velocity", drivetrain.getRightVelocity());

    }

    @Override
    protected void end(){

        drivetrain.stop();

    }

    @Override
    protected boolean isFinished(){

        return ramseteFollower.isFinished();

    }

}