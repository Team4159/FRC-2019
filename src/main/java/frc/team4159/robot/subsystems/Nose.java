package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import static frc.team4159.robot.Constants.*;

public class Nose extends SubsystemBase {
    private DoubleSolenoid raiser, hooks;

    public Nose() {
        hooks = new DoubleSolenoid(0, PORTS.RAISER_FORWARD, PORTS.RAISER_REVERSE);
        raiser = new DoubleSolenoid(0, PORTS.HOOKS_FORWARD, PORTS.HOOKS_REVERSE);
    }

    public void raiseIntake() {
        raiser.set(DoubleSolenoid.Value.kReverse);
    }

    public void lowerIntake() {
        raiser.set(DoubleSolenoid.Value.kForward);
    }

    public void grabHatch() {
        hooks.set(DoubleSolenoid.Value.kForward);
    }

    public void releaseHatch() {
        hooks.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean isRaised() {
        return raiser.get() == DoubleSolenoid.Value.kReverse;
    }

    public boolean isGrabbing() {
        return hooks.get() == DoubleSolenoid.Value.kForward;
    }
}
