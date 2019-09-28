package frc.team4159.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.ctre.phoenix.sensors.PigeonIMU;

public class Drivetrain implements Subsystem {
    private static Drivetrain instance;
    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }

    private CANSparkMax leftMasterSpark, leftSlaveSpark, rightMasterSpark, rightSlaveSpark;
    private CANEncoder leftMasterEncoder, leftSlaveEncoder, rightMasterEncoder, rightSlaveEncoder;
    private PigeonIMU pigeon;

    private Drivetrain() {
        leftMasterSpark = new CANSparkMax(2, CANSparkMax.MotorType.kBrushless);
        leftSlaveSpark = new CANSparkMax(3, CANSparkMax.MotorType.kBrushless);
        rightMasterSpark = new CANSparkMax(4, CANSparkMax.MotorType.kBrushless);
        rightSlaveSpark = new CANSparkMax(5, CANSparkMax.MotorType.kBrushless);

        /* Factory default hardware to prevent unexpected behavior */
        leftMasterSpark.restoreFactoryDefaults();
        leftSlaveSpark.restoreFactoryDefaults();
        rightMasterSpark.restoreFactoryDefaults();
        rightSlaveSpark.restoreFactoryDefaults();

        /* Possible fix, see https://trello.com/c/hgMtrWMB/130-cansparkmax-construction-sets-the-sensor-type-to-nosensor-v140 */
        leftMasterEncoder = leftMasterSpark.getEncoder();
        leftSlaveEncoder = leftSlaveSpark.getEncoder();
        rightMasterEncoder = rightMasterSpark.getEncoder();
        rightSlaveEncoder = rightSlaveSpark.getEncoder();

        /* Set to brake mode */
        rightMasterSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightSlaveSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
        leftMasterSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
        leftSlaveSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);

        /* Set slave talons to follow master talons */
        leftSlaveSpark.follow(leftMasterSpark);
        rightSlaveSpark.follow(rightMasterSpark);
    }

    @Override
    public void iterate() {

    }

    public void rawDrive(double left, double right) {
        leftMasterSpark.set(left);
        rightMasterSpark.set(right);
    }
}
