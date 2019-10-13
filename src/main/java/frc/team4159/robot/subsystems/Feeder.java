package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.team4159.robot.CollisionAvoidance;
import frc.team4159.robot.Constants;
import frc.team4159.robot.OI;

public class Feeder implements Subsystem {
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
    private int goal, temporary_goal;
    private boolean zeroing;

    private Feeder() {
        ds = DriverStation.getInstance();
        oi = OI.getInstance();

        limit_switch = new DigitalInput(Constants.LIFTER_LIMIT_SWITCH);

        intake_talon = new TalonSRX(Constants.INTAKE_TALON);
        lifter_talon = new TalonSRX(Constants.LIFTER_TALON);

        intake_talon.configFactoryDefault();
        lifter_talon.configFactoryDefault();

        intake_talon.setNeutralMode(NeutralMode.Brake);
        lifter_talon.setNeutralMode(NeutralMode.Brake);

        // TODO: Tune
        lifter_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        lifter_talon.config_kP(0, 1.0);
        lifter_talon.config_kI(0, 0.0);
        lifter_talon.config_kD(0, 20.0);
        lifter_talon.config_kF(0, 0.3);
        lifter_talon.configNeutralDeadband(0);

        lifter_talon.configMotionCruiseVelocity(5000);
        lifter_talon.configMotionAcceleration(2000);
    }

    @Override
    public void iterate() {
        if (oi.getSecondaryJoy().getRawButton(7)) {
            intake();
        } else {
            stop();
        }

        if (oi.getSecondaryJoy().getRawButton(9)) {
            setGoal(CollisionAvoidance.kFeederPositionUp);
        } else if (oi.getSecondaryJoy().getRawButton(6)) {
            // TODO: Measure
            setGoal(CollisionAvoidance.kFeederPositionDown);
        }

        if (goal == 0) {
            if (CollisionAvoidance.safeFeederUp(Elevator.getInstance().getPosition(), Elevator.getInstance().getGoal())) {
                temporary_goal = goal;
            } else {
                // TODO: Measure
                temporary_goal = CollisionAvoidance.kFeederPositionStaying;
            }
        }

        if (zeroed()) {
            lifter_talon.set(ControlMode.MotionMagic, temporary_goal);
        } else {
            if (zeroed()) {
                zero();
            } else {
                lifter_talon.set(ControlMode.PercentOutput, 0.3);
            }
        }
    }

    private void setGoal(int goal) {
        this.goal = goal;
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

    public int getGoal() {
        return goal;
    }

    public int getPosition() {
        return lifter_talon.getSelectedSensorPosition();
    }
}
