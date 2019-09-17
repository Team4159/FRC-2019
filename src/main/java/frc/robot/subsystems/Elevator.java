package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.elevator.ElevatorControl;
import frc.robot.util.Constants;

public class Elevator extends Subsystem {

    private static Elevator instance;
    public static Elevator getInstance() {
        if(instance == null)
            instance = new Elevator();
        return instance;
    }

    private TalonSRX elevatorMasterTalon, elevatorSlaveTalon;
    private DigitalInput limitSwitch;

    private double targetPosition;

    private Elevator() {

        elevatorMasterTalon = new TalonSRX(Constants.getInt("ELEVATOR_MASTER_TALON"));
        elevatorSlaveTalon = new TalonSRX(Constants.getInt("ELEVATOR_SLAVE_TALON"));
        limitSwitch = new DigitalInput(Constants.getInt("ELEVATOR_LIMIT_SWITCH"));

        /* Factory default hardware to prevent unexpected behavior */
        elevatorMasterTalon.configFactoryDefault();
        elevatorSlaveTalon.configFactoryDefault();

        /* Set brake mode */
        elevatorMasterTalon.setNeutralMode(NeutralMode.Brake);
        elevatorSlaveTalon.setNeutralMode(NeutralMode.Brake);

        /* Set Slave Talon to follow Master Talon output */
        elevatorSlaveTalon.follow(elevatorMasterTalon);


        /*
          Configure Talon SRX Output and Sensor direction accordingly
          Invert Motor to have green LEDs when driving Talon Forward / Requesting Positive Output
          Phase sensor to have positive increment when driving Talon Forward (Green LED)
         */
        elevatorMasterTalon.setInverted(InvertType.None);
        elevatorSlaveTalon.setInverted(InvertType.FollowMaster);
        //elevatorMasterTalon.setSensorPhase(true);

        elevatorMasterTalon.configVoltageCompSaturation(11.5);
        elevatorSlaveTalon.configVoltageCompSaturation(11.5);
    }

    public void setPercentOutput(double output) {

        elevatorMasterTalon.set(ControlMode.PercentOutput, output);

    }

    public double getMotionMagicError() {

        return elevatorMasterTalon.getClosedLoopError(Constants.getInt("PID_LOOP_IDX"));

    }

    public void setTargetPosition(int targetPosition) {

        this.targetPosition = targetPosition;
        elevatorMasterTalon.set(ControlMode.MotionMagic, targetPosition);

    }

    /**
     * @return True if the absolute value of the difference between the current position and target position is less than
     * a certain threshold. False otherwise.
     */
    public boolean isMotionMagicFinished() {

        return Math.abs(getEncoderPosition() - targetPosition) < 100; // TODO: adjust as needed

    }

    public void checkLimitSwitch() {

        if(limitSwitchPressed()) {
            elevatorMasterTalon.setSelectedSensorPosition(0, Constants.getInt("PID_LOOP_IDX"), Constants.getInt("TIMEOUT_MS"));
        }

    }

    private boolean limitSwitchPressed() {

        return limitSwitch.get();

    }

    private int getEncoderPosition() {

        return elevatorMasterTalon.getSelectedSensorPosition();

    }

    public boolean isBelowCollisionThreshold() {

        return getEncoderPosition() < 5000; // TODO: measure

    }

    public boolean isAboveCollisionThreshold() {

        return getEncoderPosition() > 5000; // TODO: measure

    }

    @Override
    public void initDefaultCommand() {

        setDefaultCommand(new ElevatorControl());

    }

}

