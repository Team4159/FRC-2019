package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.team4159.robot.OI;

public class Grabber implements Subsystem {
    private static Grabber instance;
    public static Grabber getInstance() {
        if (instance == null) {
            instance = new Grabber();
        }
        return instance;
    }

    private OI oi;
    private VictorSPX master_grabber_victor, slave_grabber_victor;

    private Grabber() {
        oi = OI.getInstance();
        master_grabber_victor = new VictorSPX(8);
        slave_grabber_victor = new VictorSPX(5);

        master_grabber_victor.configFactoryDefault();
        slave_grabber_victor.configFactoryDefault();

        master_grabber_victor.setNeutralMode(NeutralMode.Brake);
        slave_grabber_victor.setNeutralMode(NeutralMode.Brake);

        slave_grabber_victor.setInverted(true);
        slave_grabber_victor.follow(master_grabber_victor);
    }

    private void intake() {
        master_grabber_victor.set(ControlMode.PercentOutput, -1);
    }

    private void outtake() {
        master_grabber_victor.set(ControlMode.PercentOutput, +1);
    }

    private void stop() {
        master_grabber_victor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void iterate() {
        if (oi.getSecondaryJoy().getRawButton(7)) {
            intake();
        } else if (oi.getSecondaryJoy().getRawButton(8)) {
            outtake();
        } else {
            stop();
        }
    }
}
