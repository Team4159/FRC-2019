package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import frc.team4159.robot.OI;

public class Nose implements Subsystem {
    private static Nose instance;
    public static  Nose getInstance() {
        if (instance == null) {
            instance = new Nose();
        }
        return instance;
    }

    private OI oi;
    private DoubleSolenoid raiser, hooks;

    private Nose() {
        oi = OI.getInstance();

        raiser = new DoubleSolenoid(0, 0, 4);
        hooks = new DoubleSolenoid(0, 5, 1);
    }

    @Override
    public void iterate() {
        if (oi.getSecondaryJoy().getRawButtonPressed(10)) {
            if (raiser.get() == DoubleSolenoid.Value.kForward) {
                raise();
            } else {
                lower();
            }
        }

        if (oi.getSecondaryJoy().getRawButtonPressed(5)) {
            if (hooks.get() == DoubleSolenoid.Value.kForward) {
                release();
            } else {
                grab();
            }
        }
    }

    private void raise() {
        raiser.set(DoubleSolenoid.Value.kReverse);
    }

    private void lower() {
        raiser.set(DoubleSolenoid.Value.kForward);
    }

    private void grab() {
        hooks.set(DoubleSolenoid.Value.kForward);
    }

    private void release() {
        hooks.set(DoubleSolenoid.Value.kReverse);
    }
}
