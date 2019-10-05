package frc.team4159.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.ctre.phoenix.sensors.PigeonIMU;
import frc.team4159.robot.OI;

public class Drivetrain implements Subsystem {
    private static Drivetrain instance;
    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }

    private OI oi;
    private CANSparkMax left_master_spark, left_slave_spark, right_master_spark, right_slave_spark;
    private CANEncoder left_master_encoder, left_slave_encoder, right_master_encoder, right_slave_encoder;
    private PigeonIMU pigeon;

    private Drivetrain() {
        oi = OI.getInstance();

        left_master_spark = new CANSparkMax(2, CANSparkMax.MotorType.kBrushless);
        left_slave_spark = new CANSparkMax(3, CANSparkMax.MotorType.kBrushless);
        right_master_spark = new CANSparkMax(4, CANSparkMax.MotorType.kBrushless);
        right_slave_spark = new CANSparkMax(5, CANSparkMax.MotorType.kBrushless);

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
    }

    @Override
    public void iterate() {
        rawDrive(oi.getLeftJoy().getY(), oi.getRightJoy().getY());

        if (oi.getRightJoy().getRawButtonPressed(2)) {
            flipOrientation();
        }
    }

    private void rawDrive(double left, double right) {
        left_master_spark.set(left);
        right_master_spark.set(right);
    }

    private void flipOrientation() {
        right_master_spark.setInverted(!right_master_spark.getInverted());
        right_slave_spark.setInverted(!right_slave_spark.getInverted());
        left_master_spark.setInverted(!left_master_spark.getInverted());
        left_slave_spark.setInverted(!left_slave_spark.getInverted());
    }
}
