package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.team4159.robot.Constants;
import frc.team4159.robot.OI;
import frc.team4159.robot.loops.ElevatorLoop;

public class Elevator implements Subsystem {
    private static Elevator instance;
    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }
        return instance;
    }

    private ElevatorLoop elevator_loop;
    private DriverStation ds;
    private OI oi;

    private TalonSRX master_talon;
    private TalonSRX slave_talon;

    private DigitalInput limitSwitch;

    private Elevator() {
        oi = OI.getInstance();
        elevator_loop = new ElevatorLoop();
        ds = DriverStation.getInstance();

        master_talon = new TalonSRX(Constants.Ports.ELEVATOR_MASTER_TALON);
        slave_talon = new TalonSRX(Constants.Ports.ELEVATOR_SLAVE_TALON);

        limitSwitch = new DigitalInput(9);

        master_talon.configFactoryDefault();
        slave_talon.configFactoryDefault();

        master_talon.setNeutralMode(NeutralMode.Coast);
        slave_talon.setNeutralMode(NeutralMode.Coast);

        master_talon.configVoltageCompSaturation(ElevatorLoop.kMaxVoltage);
        master_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        slave_talon.follow(master_talon);
    }

    @Override
    public void iterate() {
        /*
        if (ds.isOperatorControl()) {

        } else if (ds.isAutonomous()) {

        }
        */

        master_talon.set(ControlMode.PercentOutput, oi.getSecondaryJoy().getY());

        /*
        master_talon.set(ControlMode.PercentOutput,
                        elevator_loop.update(
                                master_talon.getSensorCollection().getQuadraturePosition(),
                                limitSwitch.get(),
                                ds.isEnabled()
                        ) / ElevatorLoop.kMaxVoltage);
         */
    }
}
