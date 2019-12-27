package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team4159.robot.Constants;
import frc.team4159.robot.OI;

public class Grabber implements Subsystem {
    private static Grabber instance;
    public static Grabber getInstance() {
        if (instance == null) {
            instance = new Grabber();
        }
        return instance;
    }

    private DriverStation ds;
    private OI oi;

    private VictorSPX master_grabber_victor, slave_grabber_victor;

    private Grabber() {
        ds = DriverStation.getInstance();
        oi = OI.getInstance();

        master_grabber_victor = new VictorSPX(Constants.PORTS.GRABBER_MASTER_VICTOR);
        slave_grabber_victor = new VictorSPX(Constants.PORTS.GRABBER_SLAVE_VICTOR);

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

        if (oi.getSecondaryJoy().getRawButton(Constants.BUTTON_IDS.INTAKE)) {
            grabberIntake();
        } else if (oi.getSecondaryJoy().getRawButton(Constants.BUTTON_IDS.OUTTAKE)) {
            grabberOuttake();
        } else {
            stopGrabber();
        }
    }

    private void grabberIntake() {
        master_grabber_victor.set(ControlMode.PercentOutput, -1);
    }

    private void grabberOuttake() {
        master_grabber_victor.set(ControlMode.PercentOutput, +1);
    }

    private void stopGrabber() {
        master_grabber_victor.set(ControlMode.PercentOutput, -0.15);
    }
}
