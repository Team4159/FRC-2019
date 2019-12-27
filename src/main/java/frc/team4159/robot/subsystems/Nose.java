package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team4159.robot.Constants;
import frc.team4159.robot.OI;

public class Nose implements Subsystem {
    private static Nose instance;

    public static Nose getInstance() {
        if (instance == null) {
            instance = new Nose();
        }
        return instance;
    }

    private DriverStation ds;
    private OI oi;

    private DoubleSolenoid raiser_solenoid, hooks_solenoid;

    private boolean goal = false;

    private Nose() {
        ds = DriverStation.getInstance();
        oi = OI.getInstance();

        hooks_solenoid = new DoubleSolenoid(0, Constants.PORTS.RAISER_FORWARD, Constants.PORTS.RAISER_REVERSE);
        raiser_solenoid = new DoubleSolenoid(0, Constants.PORTS.HOOKS_FORWARD, Constants.PORTS.HOOKS_REVERSE);
    }

    @Override
    public void iterate() {
        if (!ds.isEnabled()) {
            return;
        }

        if (oi.getSecondaryJoy().getRawButtonPressed(Constants.BUTTON_IDS.TOGGLE_HOOKS)) {
            if (hooks_solenoid.get() == DoubleSolenoid.Value.kForward) {
                hooksSolenoidRelease();
            } else {
                hooksSolenoidGrab();
            }
        }

        if (oi.getSecondaryJoy().getRawButtonPressed(Constants.BUTTON_IDS.TOGGLE_RAISER)) {
            goal = !goal;
        }

        boolean filtered_goal = goal;

        /*
        if (!CollisionAvoidance.raiserSafeToBeUp(Elevator.getInstance().getElevatorPosition(), Elevator.getInstance().getElevatorGoal())) {
            filtered_goal = false;
        }
         */

        if (filtered_goal) {
            raiserSolenoidRaise();
        } else {
            raiserSolenoidLower();
        }
    }

    private void raiserSolenoidRaise() {
        raiser_solenoid.set(DoubleSolenoid.Value.kForward);
    }

    private void raiserSolenoidLower() {
        raiser_solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    private void hooksSolenoidGrab() {
        hooks_solenoid.set(DoubleSolenoid.Value.kForward);
    }

    private void hooksSolenoidRelease() {
        hooks_solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    boolean isRaiserSolenoidRaised() {
        return raiser_solenoid.get() == DoubleSolenoid.Value.kReverse;
    }
}
