package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team4159.robot.Constants;
import frc.team4159.robot.OI;
import frc.team4159.robot.Utils;

public class Elevator implements Subsystem {
    private static final int slotIdx = 0;
    private static final double kP = 0.12;
    private static final double kI = 0.0;
    private static final double kD = 4.0;

    private static final double kZeroingVoltage = -0.3;

    private DriverStation ds;
    private OI oi;

    private TalonSRX master_talon;
    private TalonSRX slave_talon;

    private DigitalInput limit_switch;

    private boolean zeroing = true;
    private int goal = 0;

    public Elevator() {
        ds = DriverStation.getInstance();
        oi = OI.getInstance();
        // elevator_loop = new ElevatorLoop();

        master_talon = new TalonSRX(Constants.ELEVATOR_MASTER_TALON);
        slave_talon = new TalonSRX(Constants.ELEVATOR_SLAVE_TALON);

        limit_switch = new DigitalInput(Constants.ELEVATOR_LIMIT_SWITCH);

        master_talon.configFactoryDefault();
        slave_talon.configFactoryDefault();

        master_talon.setNeutralMode(NeutralMode.Coast);
        slave_talon.setNeutralMode(NeutralMode.Coast);

        master_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        master_talon.config_kP(0, 0.12);
        master_talon.config_kI(0, 0);
        master_talon.config_kD(0, 4.0);
        /*
        master_talon.config_kF(0, 0.06);

        master_talon.configMotionCruiseVelocity(10000);
        master_talon.configMotionAcceleration(5000);
        */

        slave_talon.follow(master_talon);
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

        if (oi.getSecondaryJoy().getRawButtonPressed(Constants.CONTROLS.ELEVATOR_TO_CARGO_SHIP_HATCH)) {
            goal = Constants.POSITIONS.CARGO_SHIP_HATCH;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.CONTROLS.ELEVATOR_TO_CARGO_SHIP_PORT)) {
            goal = Constants.POSITIONS.CARGO_SHIP_PORT;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.CONTROLS.ELEVATOR_TO_ROCKET_HATCH_LEVEL_TWO)) {
            goal = Constants.POSITIONS.ROCKET_HATCH_LEVEL_TWO;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.CONTROLS.ELEVATOR_TO_ROCKET_HATCH_LEVEL_THREE)) {
            goal = Constants.POSITIONS.ROCKET_HATCH_LEVEL_THREE;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.CONTROLS.ELEVATOR_TO_ROCKET_PORT_LEVEL_ONE)) {
            goal = Constants.POSITIONS.ROCKET_PORT_LEVEL_ONE;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.CONTROLS.ELEVATOR_TO_ROCKET_PORT_LEVEL_TWO)) {
            goal = Constants.POSITIONS.ROCKET_PORT_LEVEL_TWO;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.CONTROLS.ELEVATOR_TO_ROCKET_PORT_LEVEL_THREE)) {
            goal = Constants.POSITIONS.ROCKET_PORT_LEVEL_THREE;
        }

        if (zeroing) {
            master_talon.set(ControlMode.PercentOutput, -0.3);
        } else {
            if (oi.getSecondaryJoy().getRawButton(Constants.CONTROLS.ELEVATOR_MANUAL_CONTROL)) {
                master_elevator_talon.set(ControlMode.PercentOutput, oi.getSecondaryJoy().getY());
                goal = getElevatorPosition();
            } else {
                master_talon.set(ControlMode.Position, goal);
            }
        }
    }

    int goal() {
        return goal;
    }

    int position() {
        return master_talon.getSelectedSensorPosition();
    }

    private boolean zeroed() {
        return !limit_switch.get();
    }

    private void zero() {
        master_talon.setSelectedSensorPosition(0);
    }

    public static int metersToTicks(double meters) {
        return Utils.metersToTicks(meters, Constants.MATH.ELEVATOR_SPROCKET_RADIUS, Constants.MATH.TICKS_PER_REV);
    }
}
