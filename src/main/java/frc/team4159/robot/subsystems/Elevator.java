package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.team4159.robot.loops.ElevatorLoop;

public class Elevator implements Subsystem {
    private static Elevator instance;
    public static Elevator getInstance() {
        if (instance == null) instance = new Elevator();
        return instance;
    }

    private ElevatorLoop elevator_loop;
    private DriverStation ds;

    private TalonSRX master_talon;
    private TalonSRX slave_talon;

    private DigitalInput limitSwitch;

    private Elevator() {
        elevator_loop = new ElevatorLoop();
        ds = DriverStation.getInstance();

        master_talon = new TalonSRX(11);
        slave_talon = new TalonSRX(12);
        slave_talon.follow(master_talon);
        master_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        limitSwitch = new DigitalInput(0);
    }

    @Override
    public void iterate() {
        /*
        if (ds.isOperatorControl()) {

        } else if (ds.isAutonomous()) {

        }
        */

        master_talon.set(ControlMode.PercentOutput,
                        elevator_loop.update(
                                master_talon.getSensorCollection().getQuadraturePosition(),
                                limitSwitch.get(),
                                ds.isEnabled()
                        ) / 12.0);
    }
}
