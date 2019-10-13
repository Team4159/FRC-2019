package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import frc.team4159.robot.CollisionAvoidance;
import frc.team4159.robot.Constants;
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

    private boolean goal = false;

    private Nose() {
        oi = OI.getInstance();

        raiser = new DoubleSolenoid(0, Constants.RAISER_FORWARD, Constants.RAISER_REVERSE);
        hooks = new DoubleSolenoid(0, Constants.HOOKS_FORWARD, Constants.HOOKS_REVERSE);
    }

    @Override
    public void iterate() {
        if (oi.getSecondaryJoy().getRawButtonPressed(5)) {
            if (hooks.get() == DoubleSolenoid.Value.kForward) {
                release();
            } else {
                grab();
            }
        }

        if (oi.getSecondaryJoy().getRawButtonPressed(10)) {
            goal = raiser.get() != DoubleSolenoid.Value.kForward;
        }

        boolean filtered_goal = goal;

        if (!goal) {
            if (!CollisionAvoidance.raiserSafeToBeUp(Elevator.getInstance().position(), Elevator.getInstance().goal())) {
                filtered_goal = true;
            }
        }

        if (filtered_goal) {
            lower();
        } else {
            raise();
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

    boolean raised() {
        return raiser.get() == DoubleSolenoid.Value.kReverse;
    }
}
