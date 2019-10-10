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

    private DigitalInput limit_switch;

    private Elevator() {
        oi = OI.getInstance();
        // elevator_loop = new ElevatorLoop();
        ds = DriverStation.getInstance();

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
        /*
        if (ds.isOperatorControl()) {

        } else if (ds.isAutonomous()) {

        }
        */

        if (!limit_switch.get()) {
            master_talon.setSelectedSensorPosition(0);
        }

        /*
        double voltage = elevator_loop.update(
                master_talon.getSelectedSensorPosition() / Constants.RobotMath.TICKS_PER_REV * Constants.RobotMath.ELEVATOR_SPROCKET_CIRCUMFERENCE,
                !limit_switch.get(),
                ds.isEnabled()
        );

        master_talon.set(ControlMode.PercentOutput, voltage / 12.0);

        if (elevator_loop.getState() == 2) {
            System.out.println("Running");
            System.out.println("Voltage: " + voltage + " Position: " + master_talon.getSelectedSensorPosition() / Constants.RobotMath.TICKS_PER_REV * Constants.RobotMath.ELEVATOR_SPROCKET_CIRCUMFERENCE);
        }
        */

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
