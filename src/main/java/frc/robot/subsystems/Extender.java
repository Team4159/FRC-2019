package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.extender.ExtenderControl;
import frc.robot.util.Constants;


public class Extender extends Subsystem {

    private static Extender instance;
    public static Extender getInstance() {
        if (instance == null) {
            instance = new Extender();
        }
        return instance;
    }

    private DoubleSolenoid extenderSolenoid;

    private Extender() {

        extenderSolenoid = new DoubleSolenoid(Constants.getInt("PCM"),
                Constants.getInt("EXTENDER_SOLENOID_A"), Constants.getInt("EXTENDER_SOLENOID_B"));

    }

    public void out() {

        extenderSolenoid.set(DoubleSolenoid.Value.kForward);

    }

    public void in() {

        extenderSolenoid.set(DoubleSolenoid.Value.kReverse);

    }

    public DoubleSolenoid.Value getValue() {

        return extenderSolenoid.get();

    }

    @Override
    protected void initDefaultCommand() {

        setDefaultCommand(new ExtenderControl());

    }

}
