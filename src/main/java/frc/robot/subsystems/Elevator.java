package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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

    private TalonSRX elevatorTalon;
    private VictorSPX elevatorVictor1, elevatorVictor2, elevatorVictor3;
    private DigitalInput limitSwitch;

    private double targetPosition;

    private Elevator() {

        elevatorTalon = new TalonSRX(Constants.getInt("ELEVATOR_TALON"));
        elevatorVictor1 = new VictorSPX(Constants.getInt("ELEVATOR_VICTOR_1"));
        elevatorVictor2 = new VictorSPX(Constants.getInt("ELEVATOR_VICTOR_2"));
        elevatorVictor3 = new VictorSPX(Constants.getInt("ELEVATOR_VICTOR_3"));

        limitSwitch = new DigitalInput(10);

        /* Factory default hardware to prevent unexpected behavior */
        elevatorTalon.configFactoryDefault();
        elevatorVictor1.configFactoryDefault();
        elevatorVictor2.configFactoryDefault();
        elevatorVictor3.configFactoryDefault();

        /* Configure Sensor Source for Primary PID */
        elevatorTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.getInt("PID_LOOP_IDX"), Constants.getInt("TIMEOUT_MS"));

        /* Set Victors to follow Talon output */
        elevatorVictor1.follow(elevatorTalon);
        elevatorVictor2.follow(elevatorTalon);
        elevatorVictor3.follow(elevatorTalon);

        /*
          Configure Talon SRX Output and Sensor direction accordingly
          Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
          Phase sensor to have positive increment when driving Talon Forward (Green LED)
         */
        elevatorTalon.setSensorPhase(true);
        elevatorTalon.setInverted(false);
        // TODO: Invert elevatorVictors as needed also

        /* Set relevant frame periods to be at least as fast as periodic rate */
        elevatorTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.getInt("TIMEOUT_MS"));
        elevatorTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.getInt("TIMEOUT_MS"));

        /* Set the peak and nominal outputs */
        elevatorTalon.configNominalOutputForward(0, Constants.getInt("TIMEOUT_MS"));
        elevatorTalon.configNominalOutputReverse(0, Constants.getInt("TIMEOUT_MS"));
        elevatorTalon.configPeakOutputForward(1, Constants.getInt("TIMEOUT_MS"));
        elevatorTalon.configPeakOutputReverse(-1, Constants.getInt("TIMEOUT_MS"));

        /* Set Motion Magic gains in slot0 - see documentation */
        elevatorTalon.selectProfileSlot(Constants.getInt("SLOT_IDX"), Constants.getInt("PID_LOOP_IDX"));
        elevatorTalon.config_kF(Constants.getInt("SLOT_IDX"), Constants.getDouble("kF_ELEVATOR"), Constants.getInt("TIMEOUT_MS"));
        elevatorTalon.config_kP(Constants.getInt("SLOT_IDX"), Constants.getDouble("kP_ELEVATOR"), Constants.getInt("TIMEOUT_MS"));
        elevatorTalon.config_kI(Constants.getInt("SLOT_IDX"), Constants.getDouble("kI_ELEVATOR"), Constants.getInt("TIMEOUT_MS"));
        elevatorTalon.config_kD(Constants.getInt("SLOT_IDX"), Constants.getDouble("kD_ELEVATOR"), Constants.getInt("TIMEOUT_MS"));

        /* Set acceleration and vcruise velocity - see documentation */
        elevatorTalon.configMotionCruiseVelocity(Constants.getInt("ELEVATOR_CRUISE_VELOCITY"), Constants.getInt("TIMEOUT_MS"));
        elevatorTalon.configMotionAcceleration(Constants.getInt("ELEVATOR_ACCELERATION"), Constants.getInt("TIMEOUT_MS"));

        /* Zero the sensor */
        elevatorTalon.setSelectedSensorPosition(0, Constants.getInt("PID_LOOP_IDX"), Constants.getInt("TIMEOUT_MS"));
    }

    public void setPercentOutput(double output) {

        elevatorTalon.set(ControlMode.PercentOutput, output);

    }

    public void updatePosition() {

        elevatorTalon.set(ControlMode.MotionMagic, targetPosition);

    }

    public double getMotionMagicError() {

        return elevatorTalon.getClosedLoopError(Constants.getInt("PID_IDX"));

    }

    public void setHatchLowPosition() {

        targetPosition = Constants.getInt("HATCH_LOW_HEIGHT");

    }

    public void setHatchMedPosition() {

        targetPosition = Constants.getInt("HATCH_MED_HEIGHT");

    }

    public void setHatchHighPosition() {

        targetPosition = Constants.getInt("HATCH_HIGH_HEIGHT");

    }

    public void setCargoLowPosition() {

        targetPosition = Constants.getInt("CARGO_LOW_HEIGHT");

    }

    public void setCargoMedPosition() {

        targetPosition = Constants.getInt("CARGO_MED_HEIGHT");

    }

    public void setCargoHighPosition() {

        targetPosition = Constants.getInt("CARGO_HIGH_HEIGHT");

    }

    public boolean limitSwitchPressed() {

        return limitSwitch.get();

    }

    public int getEncoderPosition() {

        return elevatorTalon.getSelectedSensorPosition();

    }

    @Override
    public void initDefaultCommand() {

        setDefaultCommand(new ElevatorControl());

    }

}

