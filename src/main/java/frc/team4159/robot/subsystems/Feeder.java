package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.team4159.robot.Constants;
import frc.team4159.robot.OI;

public class Feeder implements Subsystem {
    private static final int slotIdx = 0;
    private static final double kP = 1.0;
    private static final double kI = 0.0;
    private static final double kD = 20.0;
    private static final double kF = 0.3;

    private static final double kDeadband = 0;

    private static final int kCruiseVelocityCap = 5000;
    private static final int kAccelerationCap = 2000;

    private static final double kZeroingVoltage = 0.4;

    private static Feeder instance;
    public static Feeder getInstance() {
        if (instance == null) {
            instance = new Feeder();
        }
        return instance;
    }

    private DriverStation ds;
    private OI oi;

    private TalonSRX lifter_talon, intake_talon;
    private DigitalInput limit_switch;

    private boolean zeroing = true;
    private int goal = 0;

    private Feeder() {
        ds = DriverStation.getInstance();
        oi = OI.getInstance();

        limit_switch = new DigitalInput(Constants.PORTS.LIFTER_LIMIT_SWITCH);

        intake_talon = new TalonSRX(Constants.PORTS.INTAKE_TALON);
        lifter_talon = new TalonSRX(Constants.PORTS.LIFTER_TALON);

        intake_talon.configFactoryDefault();
        lifter_talon.configFactoryDefault();

        intake_talon.setNeutralMode(NeutralMode.Brake);
        lifter_talon.setNeutralMode(NeutralMode.Brake);

        // TODO: Tune
        lifter_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        lifter_talon.config_kP(slotIdx, kP);
        lifter_talon.config_kI(slotIdx, kI);
        lifter_talon.config_kD(slotIdx, kD);
        lifter_talon.config_kF(slotIdx, kF);
        lifter_talon.configNeutralDeadband(kDeadband);

        lifter_talon.configMotionCruiseVelocity(kCruiseVelocityCap);
        lifter_talon.configMotionAcceleration(kAccelerationCap);

        zero();
    }

    @Override
    public void iterate() {
        if (!ds.isEnabled()) {
            return;
        }

        if (zeroed()) {
            zeroing = false;
            zero();
        }

        if (oi.getSecondaryJoy().getRawButton(Constants.BUTTON_IDS.INTAKE)) {
            intake();
        } else {
            stop();
        }

        if (oi.getSecondaryJoy().getRawButton(Constants.BUTTON_IDS.LOWER_FEEDER)) {
            goal = Constants.NUMS.FEEDER_DOWN;
        } else if (oi.getSecondaryJoy().getRawButton(Constants.BUTTON_IDS.RAISE_FEEDER)) {
            goal = Constants.NUMS.FEEDER_UP;
        }

        int filtered_goal = goal;

        /*
        if (getElevatorGoal == Constants.FEEDER_UP) {
            if (!CollisionAvoidance.feederSafeToBeUp(Elevator.getInstance().getElevatorPosition(), Elevator.getInstance().getElevatorGoal())) {
                filtered_goal = Constants.FEEDER_DOWN;
            }
        }*/

        if (zeroing) {
            lifter_talon.set(ControlMode.PercentOutput, kZeroingVoltage);
        } else {
            lifter_talon.set(ControlMode.MotionMagic, filtered_goal);
        }
    }

    private boolean zeroed() {
        return !limit_switch.get();
    }

    private void zero() {
        lifter_talon.setSelectedSensorPosition(0);
    }

    private void intake() {
        intake_talon.set(ControlMode.PercentOutput, 1);
    }

    private void stop() {
        intake_talon.set(ControlMode.PercentOutput, 0);
    }

    public int position() {
        return lifter_talon.getSelectedSensorPosition();
    }
}
