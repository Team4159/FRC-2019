package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;

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

    private TalonSRX masterTalon;
    private TalonSRX slaveTalon;

    private DigitalInput limitSwitch;

    private Elevator() {
        elevator_loop = new ElevatorLoop();
        ds = DriverStation.getInstance();

        masterTalon = new TalonSRX(11);
        slaveTalon = new TalonSRX(12);
        slaveTalon.follow(masterTalon);
        masterTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        limitSwitch = new DigitalInput(0);
    }

    @Override
    public void iterate() {
        /*
        if (ds.isOperatorControl()) {

        } else if (ds.isAutonomous()) {

        }
        */

        masterTalon.set(ControlMode.PercentOutput,
                        elevator_loop.update(
                                masterTalon.getSensorCollection().getQuadraturePosition(),
                                limitSwitch.get(),
                                ds.isEnabled()
                        ) / 12.0);
    }
}
