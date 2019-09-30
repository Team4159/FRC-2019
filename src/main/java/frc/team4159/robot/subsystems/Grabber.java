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
    private VictorSPX grabber_victor_one, grabber_victor_two;

    private Grabber() {
        oi = OI.getInstance();
        grabber_victor_one = new VictorSPX(8);
        grabber_victor_two = new VictorSPX(5);

        grabber_victor_one.configFactoryDefault();
        grabber_victor_two.configFactoryDefault();

        grabber_victor_one.setNeutralMode(NeutralMode.Brake);
        grabber_victor_two.setNeutralMode(NeutralMode.Brake);
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

    private void intake() {
        grabber_victor_one.set(ControlMode.PercentOutput, -1);
        grabber_victor_two.set(ControlMode.PercentOutput, +1);
    }

    private void outtake() {
        grabber_victor_one.set(ControlMode.PercentOutput, +1);
        grabber_victor_two.set(ControlMode.PercentOutput, -1);
    }

    private void stop() {
        grabber_victor_one.set(ControlMode.PercentOutput, 0);
        grabber_victor_two.set(ControlMode.PercentOutput, 0);
    }
}
