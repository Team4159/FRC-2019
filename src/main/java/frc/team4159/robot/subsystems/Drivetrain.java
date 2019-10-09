package frc.team4159.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.ctre.phoenix.sensors.PigeonIMU;
//hi lol
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
    private CANEncoder left_master_encoder, left_slave_encoder, right_master_encoder, right_slave_encoder;
    private PigeonIMU pigeon;
    private Orientation orientation = Orientation.CARGO;

    private Drivetrain() {
        oi = OI.getInstance();

        left_master_spark = new CANSparkMax(Constants.Ports.LEFT_MASTER_SPARK, CANSparkMax.MotorType.kBrushless);
        left_slave_spark = new CANSparkMax(Constants.Ports.LEFT_SLAVE_SPARK, CANSparkMax.MotorType.kBrushless);
        right_master_spark = new CANSparkMax(Constants.Ports.RIGHT_MASTER_SPARK, CANSparkMax.MotorType.kBrushless);
        right_slave_spark = new CANSparkMax(Constants.Ports.RIGHT_SLAVE_SPARK, CANSparkMax.MotorType.kBrushless);

        left_master_spark.restoreFactoryDefaults();
        left_slave_spark.restoreFactoryDefaults();
        right_master_spark.restoreFactoryDefaults();
        right_slave_spark.restoreFactoryDefaults();

        /* Possible fix, see https://trello.com/c/hgMtrWMB/130-cansparkmax-construction-sets-the-sensor-type-to-nosensor-v140 */
        left_master_encoder = left_master_spark.getEncoder();
        left_slave_encoder = left_slave_spark.getEncoder();
        right_master_encoder = right_master_spark.getEncoder();
        right_slave_encoder = right_slave_spark.getEncoder();

        /* Possible fix for spontaneous inversion of motors */
        left_master_spark.setSmartCurrentLimit(40);
        left_slave_spark.setSmartCurrentLimit(40);
        right_master_spark.setSmartCurrentLimit(40);
        right_slave_spark.setSmartCurrentLimit(40);

        right_master_spark.setInverted(true);
        right_slave_spark.setInverted(true);

        right_master_spark.setIdleMode(CANSparkMax.IdleMode.kCoast);
        right_slave_spark.setIdleMode(CANSparkMax.IdleMode.kCoast);
        left_master_spark.setIdleMode(CANSparkMax.IdleMode.kCoast);
        left_slave_spark.setIdleMode(CANSparkMax.IdleMode.kCoast);

        left_slave_spark.follow(left_master_spark);
        right_slave_spark.follow(right_master_spark);

        left_master_spark.burnFlash();
        left_slave_spark.burnFlash();
        right_master_spark.burnFlash();
        right_slave_spark.burnFlash();
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
        right_master_spark.setInverted(!right_master_spark.getInverted());
        right_slave_spark.setInverted(!right_slave_spark.getInverted());
        left_master_spark.setInverted(!left_master_spark.getInverted());
        left_slave_spark.setInverted(!left_slave_spark.getInverted());
        switch (orientation) {
            case HATCH:
                orientation = Orientation.CARGO;
                break;
            case CARGO:
                orientation = Orientation.HATCH;
                break;
        }
    }
}
