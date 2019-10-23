package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import com.ctre.phoenix.sensors.PigeonIMU;
//hi lol
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

    private TalonSRX left_master_talon, left_slave_talon, right_master_talon, right_slave_talon;
    private PigeonIMU pigeon;

    private Orientation orientation = Orientation.CARGO;

    @SuppressWarnings("ConstantConditions")
    private Drivetrain() {
        ds = DriverStation.getInstance();
        oi = OI.getInstance();

        left_master_talon = new TalonSRX(Constants.LEFT_MASTER_TALON);
        right_master_talon = new TalonSRX(Constants.RIGHT_MASTER_TALON);
        left_slave_talon = new TalonSRX(Constants.LEFT_SLAVE_TALON);
        right_slave_talon = new TalonSRX(Constants.RIGHT_SLAVE_TALON);

        left_master_talon.configFactoryDefault();
        right_master_talon.configFactoryDefault();
        left_slave_talon.configFactoryDefault();
        right_slave_talon.configFactoryDefault();

        left_master_talon.setNeutralMode(NeutralMode.Coast);
        left_slave_talon.setNeutralMode(NeutralMode.Coast);
        right_master_talon.setNeutralMode(NeutralMode.Coast);
        right_slave_talon.setNeutralMode(NeutralMode.Coast);

        left_slave_talon.follow(left_master_talon);
        right_slave_talon.follow(right_master_talon);
    }

    @Override
    public void iterate() {
        if (!ds.isEnabled()) {
            return;
        }

        if (oi.getRightJoy().getRawButtonPressed(2)) {
            flipOrientation();
        }

        rawDrive(oi.getLeftJoy().getY(), oi.getRightJoy().getY());
    }

    private void rawDrive(double left, double right) {
        if (orientation == Orientation.CARGO) {
            left_master_talon.set(ControlMode.PercentOutput, left);
            right_master_talon.set(ControlMode.PercentOutput, right);
        } else if (orientation == Orientation.HATCH) {
            left_master_talon.set(ControlMode.PercentOutput, right);
            right_master_talon.set(ControlMode.PercentOutput, left);
        }
    }

    private void flipOrientation() {
        if (orientation == Orientation.CARGO) {
            orientation = Orientation.HATCH;
        } else if (orientation == Orientation.HATCH) {
            orientation = Orientation.CARGO;
        }
        right_master_talon.setInverted(orientation == Orientation.CARGO);
        right_slave_talon.setInverted(orientation == Orientation.CARGO);
        left_master_talon.setInverted(orientation == Orientation.CARGO);
        left_slave_talon.setInverted(orientation == Orientation.CARGO);
    }
}
