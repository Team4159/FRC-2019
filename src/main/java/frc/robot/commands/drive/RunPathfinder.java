package frc.robot.commands.drive;

import java.io.File;
import java.io.IOException;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Constants;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;


public class RunPathfinder extends Command implements Runnable {

    private Notifier notifier;
    private Drivetrain drivetrain;
    private EncoderFollower left, right;
    private String leftCSV, rightCSV;

    public RunPathfinder(String leftCSV, String rightCSV) {

        this.leftCSV = leftCSV;
        this.rightCSV = rightCSV;

        drivetrain = Drivetrain.getInstance();
        requires(drivetrain);

    }

    @Override
    protected void initialize() {

        System.out.println("Running: " + leftCSV + ", " + rightCSV);

        double MAX_VELOCITY = Constants.getDouble("maxVelocity");
        double kV = 1 / MAX_VELOCITY;

        System.out.println(MAX_VELOCITY);

        // drivetrain.zeroNavX();

        File left_csv_trajectory  = new File(leftCSV);
        File right_csv_trajectory = new File(rightCSV);

        Trajectory left_trajectory = null;
        Trajectory right_trajectory = null;

        try {
            left_trajectory = Pathfinder.readFromCSV(left_csv_trajectory);
            right_trajectory = Pathfinder.readFromCSV(right_csv_trajectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        left =  new EncoderFollower(left_trajectory);
        right = new EncoderFollower(right_trajectory);

        left.configureEncoder(drivetrain.getleftEncoderCount(), Constants.getInt("TICKS_PER_REV"), Constants.getInt("WHEEL_DIAMETER_M"));
        left.configurePIDVA(0.0, 0.0, 0.0, kV, Constants.getDouble("kA_DT"));

        right.configureEncoder(drivetrain.getRightEncoderCount(), Constants.getInt("TICKS_PER_REV"), Constants.getInt("WHEEL_DIAMETER_M"));
        right.configurePIDVA(0.0, 0.0, 0.0, kV, Constants.getDouble("kA_DT"));

        /*
         * Period of the loop. Consistent with the time step on motion profile csv
         */
        final double period = 0.01;

        /*
         * Start a separate thread with given period
         */
        notifier = new Notifier(this);
        notifier.startPeriodic(period);

    }

    /**
     * Loops in separate thread running at constant rate
     */
    @Override
    public void run() {

        double l = left.calculate(drivetrain.getleftEncoderCount());
        double r = right.calculate(drivetrain.getRightEncoderCount());

        double gyro_heading = drivetrain.getYaw();
        double desired_heading = Pathfinder.r2d(left.getHeading());

        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        double kG = Constants.getDouble("kP_TURN") * (-1.0/80.0); // TODO: Maybe change
        double turn = kG * angleDifference;

        drivetrain.rawDrive(l + turn, r - turn);
        drivetrain.logDashboard();

    }

    /**
     * @return True if finished tracking trajectory
     */
    @Override
    protected boolean isFinished() {
        return left.isFinished() && right.isFinished();
    }

    /**
     * Stop drivetrain from moving when command ends
     */
    @Override
    protected void end() {
        notifier.stop();
        drivetrain.stop();
    }

    /**
     * Calls end() if interrupted by another command
     */
    @Override
    protected void interrupted() {
        super.interrupted();
    }

}
