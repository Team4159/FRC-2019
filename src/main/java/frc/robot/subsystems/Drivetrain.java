package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.commands.drive.DriveControl;
import frc.robot.util.Constants;
import frc.robot.util.RobotMath;
import frc.robot.util.VisionThread;
import frc.robot.util.enums.Orientation;
import com.revrobotics.CANSparkMax;

public class Drivetrain extends Subsystem {

    private static Drivetrain instance;
    public static Drivetrain getInstance() {
        if(instance == null)
            instance = new Drivetrain();
        return instance;
    }

    // private TalonSRX leftMasterTalon, leftSlaveTalon, rightMasterTalon, rightSlaveTalon;
    private CANSparkMax leftMasterNeo, leftSlaveNeo, rightMasterNeo, rightSlaveNeo;
    private CANEncoder leftMasterEncoder, leftSlaveEncoder, rightMasterEncoder, rightSlaveEncoder;
    private PigeonIMU pigeon;
    private Superstructure superstructure;

    private Drivetrain() {

        leftMasterNeo = new CANSparkMax(Constants.getInt("LEFT_MASTER_NEO"), CANSparkMax.MotorType.kBrushless);
        leftSlaveNeo = new CANSparkMax(Constants.getInt("LEFT_SLAVE_NEO"), CANSparkMax.MotorType.kBrushless);
        rightMasterNeo = new CANSparkMax(Constants.getInt("RIGHT_MASTER_NEO"), CANSparkMax.MotorType.kBrushless);
        rightSlaveNeo = new CANSparkMax(Constants.getInt("RIGHT_SLAVE_NEO"), CANSparkMax.MotorType.kBrushless);
        //pigeon = new PigeonIMU(rightSlaveTalon);;

        superstructure = Superstructure.getInstance();

        /* Factory default hardware to prevent unexpected behavior */
        leftMasterNeo.restoreFactoryDefaults();
        leftSlaveNeo.restoreFactoryDefaults();
        rightMasterNeo.restoreFactoryDefaults();
        rightSlaveNeo.restoreFactoryDefaults();

        /* Possible fix, see https://trello.com/c/hgMtrWMB/130-cansparkmax-construction-sets-the-sensor-type-to-nosensor-v140 */
        leftMasterEncoder = leftMasterNeo.getEncoder();
        leftSlaveEncoder = leftSlaveNeo.getEncoder();
        rightMasterEncoder = rightMasterNeo.getEncoder();
        rightSlaveEncoder = rightSlaveNeo.getEncoder();


        /* Set to brake mode */
        rightMasterNeo.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightSlaveNeo.setIdleMode(CANSparkMax.IdleMode.kCoast);
        leftMasterNeo.setIdleMode(CANSparkMax.IdleMode.kCoast);
        leftSlaveNeo.setIdleMode(CANSparkMax.IdleMode.kCoast);


        /* Voltage compensation */
        /*
        leftMasterTalon.configVoltageCompSaturation(Constants.getInt("MAX_VOLTAGE"), Constants.getInt("TIMEOUT_MS"));
        leftMasterTalon.configVoltageMeasurementFilter(Constants.getInt("VOLTAGE_FILTER"), Constants.getInt("TIMEOUT_MS"));
        leftMasterTalon.enableVoltageCompensation(true);
        rightMasterTalon.configVoltageCompSaturation(Constants.getInt("MAX_VOLTAGE"), Constants.getInt("TIMEOUT_MS"));
        rightMasterTalon.configVoltageMeasurementFilter(Constants.getInt("VOLTAGE_FILTER"), Constants.getInt("TIMEOUT_MS"));
        rightMasterTalon.enableVoltageCompensation(true);
        */
        leftMasterNeo.enableVoltageCompensation(Constants.getInt("MAX_VOLTAGE"));
        rightMasterNeo.enableVoltageCompensation(Constants.getInt("MAX_VOLTAGE"));

        /* Set slave talons to follow master talons */
        leftSlaveNeo.follow(leftMasterNeo);
        rightSlaveNeo.follow(rightMasterNeo);

        /*
         * Configure a remote encoder sensor. Problematic to do because control loops are now slower since it has to go
         * over the CAN bus. Fix: Swap encoder connections at SFR.
         */
//        rightMasterTalon.configRemoteFeedbackFilter(15, RemoteSensorSource.TalonSRX_SelectedSensor, 0, 10);
//        rightMasterTalon.configSelectedFeedbackSensor(RemoteFeedbackDevice.RemoteSensor0, 1,0);
        /*
        leftMasterTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.getInt("PID_LOOP_IDX"), Constants.getInt("TIMEOUT_MS"));
        leftMasterTalon.setSensorPhase(false); // TODO: check

        rightMasterTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.getInt("PID_LOOP_IDX"), Constants.getInt("TIMEOUT_MS"));
        rightMasterTalon.setSensorPhase(false); // TODO: check
*/
    }

    public void setOrientation(Orientation orientation) {

        if(orientation == Orientation.FRONT_HATCH) {
            rightMasterNeo.setInverted(false);
            rightSlaveNeo.follow(rightMasterNeo);
            leftMasterNeo.setInverted(false);
            leftSlaveNeo.follow(rightMasterNeo);
            /*
            rightMasterTalon.setInverted(InvertType.InvertMotorOutput);
            rightSlaveTalon.setInverted(InvertType.FollowMaster);
            leftMasterTalon.setInverted(InvertType.None);
            leftSlaveTalon.setInverted(InvertType.FollowMaster);
            rightMasterTalon.setSensorPhase(true); // TODO: check
            */

        } else {
            rightMasterNeo.setInverted(false);
            rightSlaveNeo.follow(rightMasterNeo);
            leftMasterNeo.setInverted(false);
            leftSlaveNeo.follow(rightMasterNeo);
            /*
            rightMasterTalon.setInverted(InvertType.None);
            rightSlaveTalon.setInverted(InvertType.FollowMaster);
            leftMasterTalon.setInverted(InvertType.InvertMotorOutput);
            leftSlaveTalon.setInverted(InvertType.FollowMaster);
            rightMasterTalon.setSensorPhase(true); // TODO: check
            */
        }


    }

    public void rawDrive(double left, double right) {
        if(superstructure.getOrientation() == Orientation.FRONT_HATCH) {
            leftMasterNeo.set(left);
            System.out.println(leftMasterNeo.get());
            rightMasterNeo.set(right);
            System.out.println(rightMasterNeo.get());
        } else {
            /* Switch outputs to opposite side */
            System.out.println(leftMasterNeo.get());
            System.out.println(rightMasterNeo.get());
            leftMasterNeo.set(right);
            rightMasterNeo.set(left);

        }

    }

   /* public void autoAlign(double speed) {

        double turn;

        if(superstructure.getOrientation() == Orientation.FRONT_HATCH) {
            turn = (VisionThread.getInstance().getFrontCameraError()+.38) * Constants.getDouble("kP_ALIGN");

        } else {
            turn = VisionThread.getInstance().getBackCameraError() * Constants.getDouble("kP_ALIGN");
        }

        System.out.println("turn: " + turn);

        arcadeDrive(speed, turn);
    } */


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

        leftMasterNeo.set(0);
        rightMasterNeo.set(0);

    }

    public void setRawConfig() {


    }

    public void setBrownoutPreventionConfig() {

        // ramping

    }
/*
    public void setVelocity(double left, double right, double acceleration) {

        leftMasterTalon.set(ControlMode.Velocity, RobotMath.feetToTicks(left)/10, DemandType.ArbitraryFeedForward, 0.1 + acceleration * Constants.getDouble("kA_DT"));
        rightMasterTalon.set(ControlMode.Velocity, RobotMath.feetToTicks(right)/10, DemandType.ArbitraryFeedForward, 0.1 + acceleration * Constants.getDouble("kA_DT"));

    }
    */
/*
    public int getleftEncoderCount() {

        return leftMasterTalon.getSelectedSensorPosition();

    }

    public int getRightEncoderCount() {

        return rightMasterTalon.getSelectedSensorPosition();

    }
*/

    /**
     * @return Left side drivetrain velocity in sensor units/100ms
     */
    /*
    public double getLeftVelocity() {

        return leftMasterTalon.getSelectedSensorVelocity();

    }
*/
    /**
     * @return Right side drivetrain velocity in sensor units/100ms
     */
    /*
    public double getRightVelocity() {

        return rightMasterTalon.getSelectedSensorVelocity();

    }
*/
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
