package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
    private ArmState state;

    private Arm() {

        armTalon = new TalonSRX(Constants.getInt("ARM_TALON"));
        state = ArmState.RETRACTED;

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

        armTalon.setInverted(InvertType.None); // TODO: maybe true

        /* Config peak and nominal outputs */
        armTalon.configNominalOutputForward(0, 30);
        armTalon.configNominalOutputReverse(0, 30);
        armTalon.configPeakOutputForward(1, 30);
        armTalon.configPeakOutputReverse(-1, 30);


    }

    public void setPercentOutput(double percentOutput) {

        armTalon.set(ControlMode.PercentOutput, percentOutput);

    }

    public void stop() {

        setPercentOutput(0.0);
    }

    public void setState(ArmState state) {

        this.state = state;

    }

    public ArmState getState() {

        return state;

    }

    public boolean isRetracted() {

        return state == ArmState.RETRACTED;

    }

    public double getCurrent() {

        return armTalon.getOutputCurrent();

    }

    public boolean isStalling() {

        return armTalon.getOutputCurrent() > 10; // TODO

    }

    @Override
    protected void initDefaultCommand() {
    }

    public enum ArmState {

        EXTENDED,
        RETRACTED

    }

}
