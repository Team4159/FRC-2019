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
    private DoubleSolenoid raiser, hooker;

    private Nose() {
        oi = OI.getInstance();

        raiser = new DoubleSolenoid(0,0,4);
        hooker = new DoubleSolenoid(0,5,1);
    }

    private void raise() {
        raiser.set(DoubleSolenoid.Value.kReverse);
    }

    private void lower() {
        raiser.set(DoubleSolenoid.Value.kForward);
    }

    private void grab() {
        hooker.set(DoubleSolenoid.Value.kForward);
    }

    private void release() {
        hooker.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void iterate() {
        if (oi.getSecondaryJoy().getRawButton(5)) {
            if (raiser.get() == DoubleSolenoid.Value.kForward) {
                raise();
            } else {
                lower();
            }
        }

        if (oi.getSecondaryJoy().getRawButton(10)) {
            if (hooker.get() == DoubleSolenoid.Value.kReverse) {
                grab();
            } else {
                release();
            }
        }
    }
}
