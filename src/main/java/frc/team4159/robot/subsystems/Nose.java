package frc.team4159.robot.subsystems;

import frc.team4159.robot.CollisionAvoidance;
import frc.team4159.robot.Constants;
import frc.team4159.robot.OI;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;

public class Nose implements Subsystem {
    private static Nose instance;
    public static  Nose getInstance() {
        if (instance == null) {
            instance = new Nose();
        }
        return instance;
    }

    private DriverStation ds;
    private OI oi;

    private DoubleSolenoid raiser, hooks;

    private boolean goal = false;

    public Nose() {
        ds = DriverStation.getInstance();
        oi = OI.getInstance();

        hooks = new DoubleSolenoid(0, Constants.RAISER_FORWARD, Constants.RAISER_REVERSE);
        raiser = new DoubleSolenoid(0, Constants.HOOKS_FORWARD, Constants.HOOKS_REVERSE);
    }

    @Override
    public void iterate() {
        if (!ds.isEnabled()) {
            return;
        }

        if (oi.getSecondaryJoy().getRawButtonPressed(Constants.CONTROLS.TOGGLE_HOOKS)) {
            if (hooks_solenoid.get() == DoubleSolenoid.Value.kForward) {
                hooksSolenoidRelease();
            } else {
                grab();
            }
        }

        if (oi.getSecondaryJoy().getRawButtonPressed(Constants.CONTROLS.TOGGLE_RAISER)) {
            goal = !goal;
        }

        boolean filtered_goal = goal;

        if (filtered_goal) {
            raise();
        } else {
            lower();
        }
    }

    private void raise() {
        raiser.set(DoubleSolenoid.Value.kForward);
    }

    private void lower() {
        raiser.set(DoubleSolenoid.Value.kReverse);
    }

    private void grab() {
        hooks.set(DoubleSolenoid.Value.kForward);
    }

    private void release() {
        hooks.set(DoubleSolenoid.Value.kReverse);
    }

    boolean raised() {
        return raiser.get() == DoubleSolenoid.Value.kReverse;
    }
}
