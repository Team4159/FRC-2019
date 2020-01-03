package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import static frc.team4159.robot.Constants.*;
import frc.team4159.robot.Utils;

public class Elevator extends PIDSubsystem {
    private SpeedControllerGroup motors;

    private DigitalInput limit_switch;
    private SensorCollection encoder;

    private boolean zeroing = true;

    public Elevator() {
        super(new PIDController(ELEVATOR_CONSTANTS.kP, ELEVATOR_CONSTANTS.kI, ELEVATOR_CONSTANTS.kD));
        getController().setTolerance(ELEVATOR_CONSTANTS.TOLERANCE);
        setSetpoint(ELEVATOR_CONSTANTS.ROCKET_HATCH_LEVEL_ONE); // 0

        TalonSRX master_talon, slave_talon;

        master_talon = configureTalonSRX(new WPI_TalonSRX(PORTS.ELEVATOR_MASTER_TALON));
        slave_talon = configureTalonSRX(new WPI_TalonSRX(PORTS.ELEVATOR_SLAVE_TALON));

        limit_switch = new DigitalInput(PORTS.ELEVATOR_LIMIT_SWITCH);
        encoder = master_talon.getSensorCollection();

        motors = new SpeedControllerGroup(
                (WPI_TalonSRX) master_talon,
                (WPI_TalonSRX) slave_talon
        );
    }

    private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
        talonSRX.configFactoryDefault();
        talonSRX.setNeutralMode(NeutralMode.Coast);

        return talonSRX;
    }

    @Override
    public void useOutput(double output, double setpoint) {
        motors.setVoltage(output);
    }

    @Override
    protected double getMeasurement() {
        return encoder.getQuadraturePosition();
    }

    public void setRawSpeed(double speed) {
        motors.set(speed);
    }

    public boolean isZeroed() {
        return !limit_switch.get();
    }

    public void resetEncoder() {
        encoder.setQuadraturePosition(0, 0);
    }

    public static int metersToTicks(double meters) {
        return Utils.metersToTicks(meters, ELEVATOR_CONSTANTS.SPROCKET_RADIUS, ELEVATOR_CONSTANTS.TICKS_PER_REV);
    }
}
