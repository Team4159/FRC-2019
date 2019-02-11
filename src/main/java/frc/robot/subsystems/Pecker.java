package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.pecker.PeckerControl;
import frc.robot.util.Constants;

public class Pecker extends Subsystem {

    private static Pecker instance;
    public static Pecker getInstance() {
        if (instance == null)
            instance = new Pecker();
        return instance;
    }

    private DoubleSolenoid peckerSolenoid;

    private Pecker() {

        peckerSolenoid = new DoubleSolenoid(Constants.getInt("PCM"),
                Constants.getInt("PECKER_A"), Constants.getInt("PECKER_B"));

    }

    public void in() {

        peckerSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void out() {

        peckerSolenoid.set(DoubleSolenoid.Value.kForward);

    }

    public DoubleSolenoid.Value getValue() {

        return peckerSolenoid.get();

    }

    public void initDefaultCommand() {

        setDefaultCommand(new PeckerControl());

    }
}

