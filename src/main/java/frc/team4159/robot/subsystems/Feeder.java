package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import static frc.team4159.robot.Constants.*;

public class Feeder extends PIDSubsystem {
    private TalonSRX lifter_talon, intake_talon;

    private DigitalInput limit_switch;

    public Feeder() {
        super(new PIDController(FEEDER_CONSTANTS.kP, FEEDER_CONSTANTS.kI, FEEDER_CONSTANTS.kD));
        getController().setTolerance(FEEDER_CONSTANTS.TOLERANCE);
        setSetpoint(FEEDER_CONSTANTS.FEEDER_UP);

        lifter_talon = configureTalonSRX(new TalonSRX(PORTS.LIFTER_TALON));
        lifter_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        intake_talon = configureTalonSRX(new TalonSRX(PORTS.INTAKE_TALON));

        limit_switch = new DigitalInput(PORTS.LIFTER_LIMIT_SWITCH);
    }

    private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
        talonSRX.configFactoryDefault();
        talonSRX.setNeutralMode(NeutralMode.Brake);

        return talonSRX;
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        lifter_talon.set(ControlMode.PercentOutput, output / 12.0);
    }

    @Override
    protected double getMeasurement() {
        return lifter_talon.getSelectedSensorPosition();
    }

    public boolean isZeroed() {
        return !limit_switch.get();
    }

    public void resetEncoder() {
        lifter_talon.setSelectedSensorPosition(0);
    }

    public void setRawLifterSpeed(double speed) {
        lifter_talon.set(ControlMode.PercentOutput, speed);
    }

    public void intakeCargo() {
        intake_talon.set(ControlMode.PercentOutput, 1);
    }

    public void stopIntaking() {
        intake_talon.set(ControlMode.PercentOutput, 0);
    }
}
