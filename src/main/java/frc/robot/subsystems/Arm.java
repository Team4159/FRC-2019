package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.util.Constants;


public class Arm extends Subsystem {

    private static Arm instance;
    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    private TalonSRX armTalon;

    private Arm() {

        armTalon = new TalonSRX(Constants.getInt("ARM_TALON"));

        /* Factory default hardware to prevent unexpected behavior */
        armTalon.configFactoryDefault();

        /* Set brake mode */
        armTalon.setNeutralMode(NeutralMode.Brake);

        /* Current limiting */
        armTalon.configPeakCurrentLimit(Constants.getInt("PEAK_CURRENT_AMPS"), Constants.getInt("TIMEOUT_MS"));
        armTalon.configPeakCurrentDuration(Constants.getInt("PEAK_TIME_MS"), Constants.getInt("TIMEOUT_MS"));
        armTalon.configContinuousCurrentLimit(Constants.getInt("CONTIN_CURRENT_AMPS"), Constants.getInt("TIMEOUT_MS"));
        armTalon.enableCurrentLimit(true);

        /* Voltage compensation */
        armTalon.configVoltageCompSaturation(10, Constants.getInt("TIMEOUT_MS"));
        armTalon.configVoltageMeasurementFilter(Constants.getInt("VOLTAGE_FILTER"), Constants.getInt("TIMEOUT_MS"));
        armTalon.enableVoltageCompensation(true);

        /* Configure Sensor Source for Primary PID */
        armTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.getInt("PID_LOOP_IDX"), Constants.getInt("TIMEOUT_MS"));

        armTalon.setInverted(InvertType.None); // TODO: maybe true

        armTalon.setSensorPhase(false);  // TODO: also maybe true

        /* Config peak and nominal outputs */
        armTalon.configNominalOutputForward(0, 30);
        armTalon.configNominalOutputReverse(0, 30);
        armTalon.configPeakOutputForward(1, 30);
        armTalon.configPeakOutputReverse(-1, 30);

        armTalon.configAllowableClosedloopError(Constants.getInt("SLOT_IDX"), Constants.getInt("PID_LOOP_IDX"), 30);

        armTalon.config_kF(Constants.getInt("PID_LOOP_IDX"), Constants.getInt("kF_ARM"), Constants.getInt("TIMEOUT_MS"));
        armTalon.config_kP(Constants.getInt("PID_LOOP_IDX"), Constants.getInt("kP_ARM"), Constants.getInt("TIMEOUT_MS"));
        armTalon.config_kI(Constants.getInt("PID_LOOP_IDX"), Constants.getInt("kI_ARM"), Constants.getInt("TIMEOUT_MS"));
        armTalon.config_kD(Constants.getInt("PID_LOOP_IDX"), Constants.getInt("kD_ARM"), Constants.getInt("TIMEOUT_MS"));

//        /*
//          Grab the 360 degree position of the MagEncoder's absolute
//          position, and intitally set the relative sensor to match.
//         */
//        int absolutePosition = _talon.getSensorCollection().getPulseWidthPosition();
//
//        /* Mask out overflows, keep bottom 12 bits */
//        absolutePosition &= 0xFFF;
//        if (Constants.kSensorPhase) { absolutePosition *= -1; }
//        if (Constants.kMotorInvert) { absolutePosition *= -1; }
//
//        /* Set the quadrature (relative) sensor to match absolute */
//        _talon.setSelectedSensorPosition(absolutePosition, Constants.kPIDLoopIdx, Constants.kTimeoutMs);


    }

    public void setPercentOutput(double percentOutput) {

        armTalon.set(ControlMode.PercentOutput, percentOutput);

    }

    public void stop() {

        setPercentOutput(0.0);
    }

    public void retract() {

        armTalon.set(ControlMode.Position, Constants.getInt("ARM_TOP_HEIGHT"));

    }


    public void resetEncoder() {

        armTalon.setSelectedSensorPosition(Constants.getInt("ARM_BOT_HEIGHT"), Constants.getInt("PID_LOOP_IDX"), Constants.getInt("TIMEOUT_MS"));

    }

    public boolean isRetracted() {

        return armTalon.getSelectedSensorPosition(Constants.getInt("PID_LOOP_IDX")) > 4000; // TODO: experimentally determine threshold

    }

    public boolean isStalling() {

        return armTalon.getOutputCurrent() > 10; // TODO

    }

    @Override
    protected void initDefaultCommand() {
    }

}
