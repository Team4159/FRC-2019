package frc.team4159.robot.subsystems;

import frc.team4159.robot.Constants;
import frc.team4159.robot.OI;

import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Grabber implements Subsystem {
    public static final double kIdleVoltage = -0.15;

    private DriverStation ds;
    private OI oi;

    private VictorSPX master_grabber_victor, slave_grabber_victor;

    public Grabber() {
        ds = DriverStation.getInstance();
        oi = OI.getInstance();

        master_grabber_victor = new VictorSPX(Constants.GRABBER_MASTER_VICTOR);
        slave_grabber_victor = new VictorSPX(Constants.GRABBER_SLAVE_VICTOR);

        master_grabber_victor.configFactoryDefault();
        slave_grabber_victor.configFactoryDefault();

        master_grabber_victor.setNeutralMode(NeutralMode.Brake);
        slave_grabber_victor.setNeutralMode(NeutralMode.Brake);

        slave_grabber_victor.setInverted(true);
        slave_grabber_victor.follow(master_grabber_victor);
    }

    @Override
    public void iterate() {
        if (!ds.isEnabled()) {
            return;
        }

        if (oi.getSecondaryJoy().getRawButton(Constants.CONTROLS.INTAKE_CARGO)) {
            intakeCargo();
        } else if (oi.getSecondaryJoy().getRawButton(Constants.CONTROLS.OUTTAKE_CARGO)) {
            outtakeCargo();
        } else {
            stop();
        }
    }

    private void intake() {
        master_grabber_victor.set(ControlMode.PercentOutput, -1);
    }

    private void outtake() {
        master_grabber_victor.set(ControlMode.PercentOutput, +1);
    }

    private void stop() {
        master_grabber_victor.set(ControlMode.PercentOutput, -0.15);
    }
}
