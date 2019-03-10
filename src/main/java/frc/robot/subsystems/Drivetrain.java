package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.commands.drive.DriveControl;
import frc.robot.util.Constants;
import frc.robot.util.RobotMath;
import frc.robot.util.VisionThread;
import frc.robot.util.enums.Orientation;

public class Drivetrain extends Subsystem {

    private static Drivetrain instance;
    public static Drivetrain getInstance() {
        if(instance == null)
            instance = new Drivetrain();
        return instance;
    }

    private TalonSRX leftMasterTalon, leftSlaveTalon, rightMasterTalon, rightSlaveTalon;
    private PigeonIMU pigeon;
    private Superstructure superstructure;

    private Drivetrain() {

        leftMasterTalon = new TalonSRX(Constants.getInt("LEFT_MASTER_TALON"));
        leftSlaveTalon = new TalonSRX(Constants.getInt("LEFT_SLAVE_TALON"));
        rightMasterTalon = new TalonSRX(Constants.getInt("RIGHT_MASTER_TALON"));
        rightSlaveTalon = new TalonSRX(Constants.getInt("RIGHT_SLAVE_TALON"));
        //pigeon = new PigeonIMU(rightSlaveTalon);
        superstructure = Superstructure.getInstance();

        /* Factory default hardware to prevent unexpected behavior */
        leftMasterTalon.configFactoryDefault();
        leftSlaveTalon.configFactoryDefault();
        rightMasterTalon.configFactoryDefault();
        rightSlaveTalon.configFactoryDefault();

        /* Configure output direction */
        rightMasterTalon.setInverted(true);
        rightSlaveTalon.setInverted(true);

        /* Set to brake mode */
        leftMasterTalon.setNeutralMode(NeutralMode.Brake);
        leftSlaveTalon.setNeutralMode(NeutralMode.Brake);
        rightMasterTalon.setNeutralMode(NeutralMode.Brake);
        rightSlaveTalon.setNeutralMode(NeutralMode.Brake);

        /* Voltage compensation */
        leftMasterTalon.configVoltageCompSaturation(Constants.getInt("MAX_VOLTAGE"), Constants.getInt("TIMEOUT_MS"));
        leftMasterTalon.configVoltageMeasurementFilter(Constants.getInt("VOLTAGE_FILTER"), Constants.getInt("TIMEOUT_MS"));
        leftMasterTalon.enableVoltageCompensation(true);
        rightMasterTalon.configVoltageCompSaturation(Constants.getInt("MAX_VOLTAGE"), Constants.getInt("TIMEOUT_MS"));
        rightMasterTalon.configVoltageMeasurementFilter(Constants.getInt("VOLTAGE_FILTER"), Constants.getInt("TIMEOUT_MS"));
        rightMasterTalon.enableVoltageCompensation(true);

        /* Set slave talons to follow master talons */
        leftSlaveTalon.follow(leftMasterTalon);
        rightSlaveTalon.follow(rightMasterTalon);

        /*
         * Configure a remote encoder sensor. Problematic to do because control loops are now slower since it has to go
         * over the CAN bus. Fix: Swap encoder connections at SFR.
         */
//        rightMasterTalon.configRemoteFeedbackFilter(15, RemoteSensorSource.TalonSRX_SelectedSensor, 0, 10);
//        rightMasterTalon.configSelectedFeedbackSensor(RemoteFeedbackDevice.RemoteSensor0, 1,0);

        leftMasterTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.getInt("PID_LOOP_IDX"), Constants.getInt("TIMEOUT_MS"));
        leftMasterTalon.setSensorPhase(false); // TODO: check

        rightMasterTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.getInt("PID_LOOP_IDX"), Constants.getInt("TIMEOUT_MS"));
        rightMasterTalon.setSensorPhase(false); // TODO: check

    }

    public void setOrientation(Orientation orientation) {

        if(orientation == Orientation.FRONT_HATCH) {
            rightMasterTalon.setInverted(true);
            rightSlaveTalon.setInverted(true);
            rightMasterTalon.setSensorPhase(true); // TODO: check


        } else {
            rightMasterTalon.setInverted(false);
            rightSlaveTalon.setInverted(false);
            rightMasterTalon.setSensorPhase(true); // TODO: check

        }


    }

    public void rawDrive(double left, double right) {

        if(superstructure.getOrientation() == Orientation.FRONT_HATCH) {
            leftMasterTalon.set(ControlMode.PercentOutput, left);
            rightMasterTalon.set(ControlMode.PercentOutput, right);

        } else {
            /* Switch outputs to opposite side */
            leftMasterTalon.set(ControlMode.PercentOutput, right);
            rightMasterTalon.set(ControlMode.PercentOutput, left);

        }

    }

    public void autoAlign(double speed) {

        double turn;

        if(superstructure.getOrientation() == Orientation.FRONT_HATCH) {
            turn = VisionThread.getInstance().getFrontCameraError() * Constants.getDouble("kP_ALIGN");

        } else {
            turn = VisionThread.getInstance().getBackCameraError() * Constants.getDouble("kP_ALIGN");
        }

        arcadeDrive(speed, turn);
    }


    public void arcadeDrive(double speed, double turn) {

        double left = speed - turn;
        double right = speed + turn;

        rawDrive(left + skim(right), right + skim(left));

    }

    private double skim(double v) {

        if (v > 1.0) {
            return -((v - 1.0) * Constants.getDouble("kG_ARCADE"));
        } else if (v < -1.0) {
            return -((v + 1.0) * Constants.getDouble("kG_ARCADE"));
        }

        return 0;
    }

    public void stop() {

        leftMasterTalon.set(ControlMode.PercentOutput, 0);
        rightMasterTalon.set(ControlMode.PercentOutput, 0);

    }

    public void setRawConfig() {


    }

    public void setBrownoutPreventionConfig() {

        // ramping

    }

    public void setVelocity(double left, double right, double acceleration) {

        leftMasterTalon.set(ControlMode.Velocity, RobotMath.feetToTicks(left)/10, DemandType.ArbitraryFeedForward, 0.1 + acceleration * Constants.getDouble("kA_DT"));
        rightMasterTalon.set(ControlMode.Velocity, RobotMath.feetToTicks(right)/10, DemandType.ArbitraryFeedForward, 0.1 + acceleration * Constants.getDouble("kA_DT"));

    }

    public int getleftEncoderCount() {

        return leftMasterTalon.getSelectedSensorPosition();

    }

    public int getRightEncoderCount() {

        return rightMasterTalon.getSelectedSensorPosition();

    }


    /**
     * @return Left side drivetrain velocity in sensor units/100ms
     */
    public double getLeftVelocity() {

        return leftMasterTalon.getSelectedSensorVelocity();

    }

    /**
     * @return Right side drivetrain velocity in sensor units/100ms
     */
    public double getRightVelocity() {

        return rightMasterTalon.getSelectedSensorVelocity();

    }

    /**
     * @return Yaw axis of the robot within -368,640 to +368,640 degrees
     */
    public double getYaw() {

        double[] ypr = new double[3];
        pigeon.getYawPitchRoll(ypr);

        return ypr[0];

    }

    /* Testing shuffleboard stuff */
    private ShuffleboardTab tab = Shuffleboard.getTab("Drive");
    private NetworkTableEntry leftEntry =
            tab.add("Left Drivetrain Velocity", 0).withWidget(BuiltInWidgets.kGraph).getEntry();
    private NetworkTableEntry rightEntry =
            tab.add("Right Drivetrain Velocity", 0).withWidget(BuiltInWidgets.kGraph).getEntry();
    private NetworkTableEntry gyroEntry =
            tab.add("Gyro", 0).withWidget(BuiltInWidgets.kGyro).getEntry();

    public void logDashboard() {

        //leftEntry.setDouble(RobotMath.ticksToFeet(getLeftVelocity()));
        //rightEntry.setDouble(RobotMath.ticksToFeet(getRightVelocity()));
        //gyroEntry.setDouble(getYaw());

    }

    @Override
    public void initDefaultCommand() {

        setDefaultCommand(new DriveControl());

    }

}