package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.hooks.HooksControl;
import frc.robot.util.Constants;


public class Hooks extends Subsystem {

    private static Hooks instance;
    public static Hooks getInstance() {
        if(instance == null)
            instance = new Hooks();
        return instance;
    }

    private DoubleSolenoid hooksSolenoid;

    private Hooks() {

        hooksSolenoid = new DoubleSolenoid(Constants.getInt("PCM"),
                Constants.getInt("HOOKS_A"), Constants.getInt("HOOKS_B"));

    }

    public void deploy() {

        hooksSolenoid.set(DoubleSolenoid.Value.kForward);

    }

    public void retract() {

        hooksSolenoid.set(DoubleSolenoid.Value.kReverse);

    }

    public boolean isDeployed() {

        return hooksSolenoid.get() == DoubleSolenoid.Value.kForward;

    }

    @Override
    protected void initDefaultCommand() {

        setDefaultCommand(new HooksControl());

    }

}