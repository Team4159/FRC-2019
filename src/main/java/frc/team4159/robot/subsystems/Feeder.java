package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.team4159.robot.Constants;
import frc.team4159.robot.OI;
import frc.team4159.robot.loops.LifterLoopBad;

public class Feeder implements Subsystem {
    private static Feeder instance;
    public static Feeder getInstance() {
        if (instance == null) {
            instance = new Feeder();
        }
        return instance;
    }

    private LifterLoopBad lifter_loop;
    private DriverStation ds;

    private OI oi;
    private TalonSRX lifter_talon, intake_talon;

    private Feeder() {
        lifter_loop = new LifterLoopBad();
        ds = DriverStation.getInstance();
        oi = OI.getInstance();

        intake_talon = new TalonSRX(Constants.INTAKE_TALON);
        lifter_talon = new TalonSRX(Constants.LIFTER_TALON);

        intake_talon.configFactoryDefault();
        lifter_talon.configFactoryDefault();

        intake_talon.setNeutralMode(NeutralMode.Brake);
        lifter_talon.setNeutralMode(NeutralMode.Brake);

        lifter_talon.configVoltageCompSaturation(LifterLoopBad.kMaxVoltage);
        lifter_talon.enableVoltageCompensation(true);
    }

    @Override
    public void iterate() {
        if (oi.getSecondaryJoy().getRawButton(7)) {
            intake();
        } else {
            stop();
        }

        if (oi.getSecondaryJoy().getRawButton(9)) {
            lifter_loop.setGoal(LifterLoopBad.Position.DOWN);
        } else if (oi.getSecondaryJoy().getRawButton(6)) {
            lifter_loop.setGoal(LifterLoopBad.Position.UP);
        }

        lifter_talon.set(ControlMode.PercentOutput,
                         lifter_loop.update(ds.isEnabled()) / LifterLoopBad.kMaxVoltage);
    }

    private void intake() {
        intake_talon.set(ControlMode.PercentOutput, 1);
    }

    private void stop() {
        intake_talon.set(ControlMode.PercentOutput, 0);
    }
}
