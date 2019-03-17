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
        limitSwitch = new DigitalInput(Constants.getInt("LIMIT_SWITCH"));

        /* Factory default hardware to prevent unexpected behavior */
        elevatorMasterTalon.configFactoryDefault();
        elevatorSlaveTalon.configFactoryDefault();

        /* Set brake mode */
        elevatorMasterTalon.setNeutralMode(NeutralMode.Brake);
        elevatorSlaveTalon.setNeutralMode(NeutralMode.Brake);

        /* Current limiting */
        elevatorMasterTalon.configPeakCurrentLimit(Constants.getInt("PEAK_CURRENT_AMPS"), Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.configPeakCurrentDuration(Constants.getInt("PEAK_TIME_MS"), Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.configContinuousCurrentLimit(Constants.getInt("CONTIN_CURRENT_AMPS"), Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.enableCurrentLimit(true);
        elevatorSlaveTalon.configPeakCurrentLimit(Constants.getInt("PEAK_CURRENT_AMPS"), Constants.getInt("TIMEOUT_MS"));
        elevatorSlaveTalon.configPeakCurrentDuration(Constants.getInt("PEAK_TIME_MS"), Constants.getInt("TIMEOUT_MS"));
        elevatorSlaveTalon.configContinuousCurrentLimit(Constants.getInt("CONTIN_CURRENT_AMPS"), Constants.getInt("TIMEOUT_MS"));
        elevatorSlaveTalon.enableCurrentLimit(true);

        /* Voltage compensation */
        elevatorMasterTalon.configVoltageCompSaturation(10, Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.configVoltageMeasurementFilter(Constants.getInt("VOLTAGE_FILTER"), Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.enableVoltageCompensation(true);

        /* Configure Sensor Source for Primary PID */
        elevatorMasterTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.getInt("PID_LOOP_IDX"), Constants.getInt("TIMEOUT_MS"));

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

        /* Set relevant frame periods to be at least as fast as periodic rate */
        elevatorMasterTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.getInt("TIMEOUT_MS"));

        /* Set the peak and nominal outputs */
        elevatorMasterTalon.configNominalOutputForward(0, Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.configNominalOutputReverse(0, Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.configPeakOutputForward(1, Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.configPeakOutputReverse(-1, Constants.getInt("TIMEOUT_MS"));

        /* Set Motion Magic gains in slot0 - see documentation */
        elevatorMasterTalon.selectProfileSlot(Constants.getInt("SLOT_IDX"), Constants.getInt("PID_LOOP_IDX"));
        elevatorMasterTalon.config_kF(Constants.getInt("SLOT_IDX"), Constants.getDouble("kF_ELEVATOR"), Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.config_kP(Constants.getInt("SLOT_IDX"), Constants.getDouble("kP_ELEVATOR"), Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.config_kI(Constants.getInt("SLOT_IDX"), Constants.getDouble("kI_ELEVATOR"), Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.config_kD(Constants.getInt("SLOT_IDX"), Constants.getDouble("kD_ELEVATOR"), Constants.getInt("TIMEOUT_MS"));

        /* Set acceleration and cruise velocity - see documentation */
        elevatorMasterTalon.configMotionCruiseVelocity(Constants.getInt("ELEVATOR_CRUISE_VELOCITY"), Constants.getInt("TIMEOUT_MS"));
        elevatorMasterTalon.configMotionAcceleration(Constants.getInt("ELEVATOR_ACCELERATION"), Constants.getInt("TIMEOUT_MS"));

        /* Zero the sensor */
        elevatorMasterTalon.setSelectedSensorPosition(0, Constants.getInt("PID_LOOP_IDX"), Constants.getInt("TIMEOUT_MS"));
      
    }

    public void setPercentOutput(double output) {

        elevatorMasterTalon.set(ControlMode.PercentOutput, output);

    }

    public void updatePosition() {

        if(limitSwitchPressed()) {
            elevatorMasterTalon.setSelectedSensorPosition(0);
        }

        elevatorMasterTalon.set(ControlMode.MotionMagic, targetPosition);

    }

    public double getMotionMagicError() {

        return elevatorMasterTalon.getClosedLoopError(Constants.getInt("PID_IDX"));

    }

    public void setHatchBotPosition() {

        targetPosition = Constants.getInt("HATCH_LOW_HEIGHT");

    }

    public void setHatchMidPosition() {

        targetPosition = Constants.getInt("HATCH_MED_HEIGHT");

    }

    public void setHatchTopPosition() {

        targetPosition = Constants.getInt("HATCH_HIGH_HEIGHT");

    }

    public void setCargoBotPosition() {

        targetPosition = Constants.getInt("CARGO_LOW_HEIGHT");

    }

    public void setCargoMidPosition() {

        targetPosition = Constants.getInt("CARGO_MED_HEIGHT");

    }

    public void setCargoTopPosition() {

        targetPosition = Constants.getInt("CARGO_HIGH_HEIGHT");

    }

    public boolean limitSwitchPressed() {

        return limitSwitch.get();

    }

    public int getEncoderPosition() {

        return elevatorMasterTalon.getSelectedSensorPosition();

    }

    @Override
    public void initDefaultCommand() {

        setDefaultCommand(new ElevatorControl());

    }

}

