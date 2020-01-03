package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
//hi lol
import static frc.team4159.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
    private static final int kSparkMaxCurrentLimit = 40;

    private enum Orientation {
        HATCH,
        CARGO
    }

    private SpeedControllerGroup left_motors, right_motors;
    private PigeonIMU pigeon;

    private Orientation orientation = Orientation.CARGO;

    public Drivetrain() {
        TalonSRX left_master_talon, left_slave_talon, right_master_talon, right_slave_talon;

        left_master_talon = configureTalonSRX(new WPI_TalonSRX(PORTS.LEFT_MASTER_TALON));
        right_master_talon = configureTalonSRX(new WPI_TalonSRX(PORTS.RIGHT_MASTER_TALON));
        left_slave_talon = configureTalonSRX(new WPI_TalonSRX(PORTS.LEFT_SLAVE_TALON));
        right_slave_talon = configureTalonSRX(new WPI_TalonSRX(PORTS.RIGHT_SLAVE_TALON));

        left_motors = new SpeedControllerGroup(
                (WPI_TalonSRX) left_master_talon,
                (WPI_TalonSRX) left_slave_talon
        );

        right_motors = new SpeedControllerGroup(
                (WPI_TalonSRX) right_master_talon,
                (WPI_TalonSRX) right_slave_talon
        );
    }

    private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
        talonSRX.configFactoryDefault();
        talonSRX.setNeutralMode(NeutralMode.Coast);

        return talonSRX;
    }

    public void setRawSpeeds(double left, double right) {
        if (orientation == Orientation.CARGO) {
            left_motors.set(left);
            right_motors.set(right);
        } else if (orientation == Orientation.HATCH) {
            left_motors.set(right);
            right_motors.set(left);
        }
    }

    public void flipOrientation() {
        if (orientation == Orientation.CARGO) {
            orientation = Orientation.HATCH;
        } else if (orientation == Orientation.HATCH) {
            orientation = Orientation.CARGO;
        }
        right_motors.setInverted(orientation == Orientation.CARGO);
        left_motors.setInverted(orientation == Orientation.CARGO);
    }
}
