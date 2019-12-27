package frc.team4159.robot.subsystems;


import com.ctre.phoenix.sensors.PigeonIMU;
//hi lol
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team4159.robot.OI;
import frc.team4159.robot.Constants;

public class Drivetrain implements Subsystem {
    private static Drivetrain instance;
    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }

    private enum Orientation {
        HATCH,
        CARGO
    }

    private DriverStation ds;
    private OI oi;

    private CANSparkMax left_master_drivetrain_spark, left_slave_drivetrain_spark, right_master_drivetrain_spark, right_slave_drivetrain_spark;
    private PigeonIMU pigeon;

    private Orientation robot_orientation = Orientation.CARGO;

    @SuppressWarnings("ConstantConditions")
    private Drivetrain() {
        ds = DriverStation.getInstance();
        oi = OI.getInstance();

        left_master_drivetrain_spark = configureSparkMax(Constants.PORTS.LEFT_MASTER_SPARK, false, null);
        right_master_drivetrain_spark = configureSparkMax(Constants.PORTS.RIGHT_MASTER_SPARK, false, null);
        left_slave_drivetrain_spark = configureSparkMax(Constants.PORTS.LEFT_SLAVE_SPARK, false, left_master_drivetrain_spark);
        right_slave_drivetrain_spark = configureSparkMax(Constants.PORTS.RIGHT_SLAVE_SPARK, false, right_master_drivetrain_spark);
    }

    private CANSparkMax configureSparkMax(int id, boolean inverted, CANSparkMax master) {
        CANSparkMax spark = new CANSparkMax(id, CANSparkMax.MotorType.kBrushless);
        spark.restoreFactoryDefaults();
        spark.setSmartCurrentLimit(40);
        spark.setInverted(inverted);
        if (master != null) {
            spark.follow(master);
        }
        spark.setIdleMode(CANSparkMax.IdleMode.kCoast);
        spark.burnFlash();
        return spark;
    }

    @Override
    public void iterate() {
        if (!ds.isEnabled()) {
            return;
        }

        if (oi.getRightJoy().getRawButtonPressed(Constants.BUTTON_IDS.ROBOT_ORIENTATION)) {
            flipOrientation();
        }

        setRawSpeeds(oi.getLeftJoy().getY(), oi.getRightJoy().getY());
    }

    private void setRawSpeeds(double left, double right) {
        if (robot_orientation == Orientation.CARGO) {
            left_master_drivetrain_spark.set(left);
            right_master_drivetrain_spark.set(right);
        } else if (robot_orientation == Orientation.HATCH) {
            left_master_drivetrain_spark.set(right);
            right_master_drivetrain_spark.set(left);
        }
    }

    private void flipOrientation() {
        if (robot_orientation == Orientation.CARGO) {
            robot_orientation = Orientation.HATCH;
        } else if (robot_orientation == Orientation.HATCH) {
            robot_orientation = Orientation.CARGO;
        }
        right_master_drivetrain_spark.setInverted(robot_orientation == Orientation.CARGO);
        right_slave_drivetrain_spark.setInverted(robot_orientation == Orientation.CARGO);
        left_master_drivetrain_spark.setInverted(robot_orientation == Orientation.CARGO);
        left_slave_drivetrain_spark.setInverted(robot_orientation == Orientation.CARGO);
    }
}
