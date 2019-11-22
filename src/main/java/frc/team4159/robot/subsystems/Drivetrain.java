package frc.team4159.robot.subsystems;


import com.ctre.phoenix.sensors.PigeonIMU;
//hi lol
import com.revrobotics.CANSparkMax;
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

    private OI oi;
    private CANSparkMax left_master_spark, left_slave_spark, right_master_spark, right_slave_spark;
    private PigeonIMU pigeon;
    private Orientation orientation = Orientation.CARGO;

    @SuppressWarnings("ConstantConditions")
    private Drivetrain() {
        oi = OI.getInstance();

        left_master_spark = configureSparkMax(Constants.LEFT_MASTER_SPARK, false, null);
        right_master_spark = configureSparkMax(Constants.RIGHT_MASTER_SPARK, false, null);
        left_slave_spark = configureSparkMax(Constants.LEFT_SLAVE_SPARK, false, left_master_spark);
        right_slave_spark = configureSparkMax(Constants.RIGHT_SLAVE_SPARK, false, right_master_spark);
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
        if (oi.getRightJoy().getRawButtonPressed(2)) {
            flipOrientation();
        }

        rawDrive(oi.getLeftJoy().getY(), oi.getRightJoy().getY());
    }

    private void rawDrive(double left, double right) {
        if (orientation == Orientation.CARGO) {
            left_master_spark.set(left);
            right_master_spark.set(right);
        } else if (orientation == Orientation.HATCH) {
            left_master_spark.set(right);
            right_master_spark.set(left);
        }
    }

    private void flipOrientation() {
        if (orientation == Orientation.CARGO) {
            orientation = Orientation.HATCH;
        } else if (orientation == Orientation.HATCH) {
            orientation = Orientation.CARGO;
        }
        right_master_spark.setInverted(orientation == Orientation.CARGO);
        right_slave_spark.setInverted(orientation == Orientation.CARGO);
        left_master_spark.setInverted(orientation == Orientation.CARGO);
        left_slave_spark.setInverted(orientation == Orientation.CARGO);
    }
}
