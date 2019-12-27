package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team4159.robot.Constants;
import frc.team4159.robot.OI;
import frc.team4159.robot.RobotMath;

public class Elevator implements Subsystem {
    private static final int slotIdx = 0;
    private static final double kP = 0.12;
    private static final double kI = 0.0;
    private static final double kD = 4.0;

    private static final double kZeroingVoltage = -0.3;

    private static Elevator instance;
    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }
        return instance;
    }

    private DriverStation ds;
    private OI oi;

    private TalonSRX master_elevator_talon;
    private TalonSRX slave_elevator_talon;

    private DigitalInput limit_switch;

    private boolean zeroing = true;
    private int goal = 0;

    private Elevator() {
        ds = DriverStation.getInstance();
        oi = OI.getInstance();
        // elevator_loop = new ElevatorLoop();

        limit_switch = new DigitalInput(Constants.PORTS.ELEVATOR_LIMIT_SWITCH);

        master_elevator_talon = configureTalon(Constants.PORTS.ELEVATOR_MASTER_TALON, false, null);
        slave_elevator_talon = configureTalon(Constants.PORTS.ELEVATOR_SLAVE_TALON, false, master_elevator_talon);

        master_elevator_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        master_elevator_talon.config_kP(slotIdx, kP);
        master_elevator_talon.config_kI(slotIdx, kI);
        master_elevator_talon.config_kD(slotIdx, kD);
        /*
        master_elevator_talon.config_kF(0, 0.06);

        master_elevator_talon.configMotionCruiseVelocity(10000);
        master_elevator_talon.configMotionAcceleration(5000);
        */

        slave_elevator_talon.follow(master_elevator_talon);
    }

    private TalonSRX configureTalon(int port, boolean inverted, TalonSRX master) {
        TalonSRX talon = new TalonSRX(port);
        talon.configFactoryDefault();
        talon.setNeutralMode(NeutralMode.Coast);
        talon.setInverted(inverted);

        if (master != null) talon.follow(master);

        return talon;
    }

    @Override
    public void iterate() {
        if (!ds.isEnabled()) {
            return;
        }

        if (isElevatorZeroed()) {
            zeroing = false;
            zeroElevatorEncoder();
        }

        if (oi.getSecondaryJoy().getRawButtonPressed(Constants.BUTTON_IDS.ELEVATOR_TO_CARGO_SHIP_HATCH)) {
            goal = Constants.NUMS.CARGO_SHIP_HATCH;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.BUTTON_IDS.ELEVATOR_TO_CARGO_SHIP_PORT)) {
            goal = Constants.NUMS.CARGO_SHIP_PORT;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.BUTTON_IDS.ELEVATOR_TO_ROCKET_HATCH_LEVEL_TWO)) {
            goal = Constants.NUMS.ROCKET_HATCH_LEVEL_TWO;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.BUTTON_IDS.ELEVATOR_TO_ROCKET_HATCH_LEVEL_THREE)) {
            goal = Constants.NUMS.ROCKET_HATCH_LEVEL_THREE;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.BUTTON_IDS.ELEVATOR_TO_ROCKET_PORT_LEVEL_ONE)) {
            goal = Constants.NUMS.ROCKET_PORT_LEVEL_ONE;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.BUTTON_IDS.ELEVATOR_TO_ROCKET_PORT_LEVEL_TWO)) {
            goal = Constants.NUMS.ROCKET_PORT_LEVEL_TWO;
        } else if (oi.getSecondaryJoy().getRawButtonPressed(Constants.BUTTON_IDS.ELEVATOR_TO_ROCKET_PORT_LEVEL_THREE)) {
            goal = Constants.NUMS.ROCKET_PORT_LEVEL_THREE;
        }

        if (zeroing) {
            master_elevator_talon.set(ControlMode.PercentOutput, -kZeroingVoltage);
        } else {
            if (oi.getSecondaryJoy().getRawButton(Constants.BUTTON_IDS.ELEVATOR_MANUAL_CONTROL)) {
                master_elevator_talon.set(ControlMode.PercentOutput, oi.getSecondaryJoy().getY());
                goal = getElevatorPosition();
            } else {
                master_elevator_talon.set(ControlMode.Position, goal);
            }
            /*
            if (CollisionAvoidance.safeToMoveElevator(getElevatorPosition(), getElevatorGoal(), Feeder.getInstance().getElevatorPosition(), Nose.getInstance().isRaiserSolenoidRaised())) {
                master_elevator_talon.set(ControlMode.Position, getElevatorGoal);
            } else {
                master_elevator_talon.set(ControlMode.PercentOutput, 0);
            }
            */
        }
    }

    int getElevatorGoal() {
        return goal;
    }

    int getElevatorPosition() {
        return master_elevator_talon.getSelectedSensorPosition();
    }

    private boolean isElevatorZeroed() {
        return !limit_switch.get();
    }

    private void zeroElevatorEncoder() {
        master_elevator_talon.setSelectedSensorPosition(0);
    }

    public static int MetersToTicks(double meters) {
        return RobotMath.MetersToTicks(meters, Constants.NUMS.ELEVATOR_SPROCKET_RADIUS, Constants.NUMS.TICKS_PER_REV);
    }
}
