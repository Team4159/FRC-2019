package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.team4159.robot.Constants;
import frc.team4159.robot.OI;

public class Elevator implements Subsystem {
    private static Elevator instance;
    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }
        return instance;
    }

    private DriverStation ds;
    private OI oi;

    private TalonSRX master_talon;
    private TalonSRX slave_talon;

    private DigitalInput limit_switch;

    private boolean zeroing = false;

    private Elevator() {
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

        slave_talon.follow(master_talon);
    }

    @Override
    public void iterate() {
        if (zeroing) {
            if (zeroed()) {
                zeroing = false;
                zero();
            } else {
                master_talon.set(ControlMode.PercentOutput, -0.1);
            }
        } else {

        }
    }

    private boolean zeroed() {
        return !limit_switch.get();
    }

    private void zero() {
        master_talon.setSelectedSensorPosition(0);
    }
}
