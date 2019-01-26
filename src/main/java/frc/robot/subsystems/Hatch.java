package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.hatch.HatchControl;
import frc.robot.util.Constants;

public class Hatch extends Subsystem {

    private static Hatch instance;
    public static Hatch getInstance(){
        if(instance == null)
            instance = new Hatch();
        return instance;
    }

    private DoubleSolenoid hatchSolenoid;

    private Hatch() {

        hatchSolenoid = new DoubleSolenoid(Constants.getInt("PCM1"), Constants.getInt("HATCH_A"), Constants.getInt("HATCH_B"));

    }

    public void open() {

        hatchSolenoid.set(DoubleSolenoid.Value.kForward);

    }

    public void close() {

        hatchSolenoid.set(DoubleSolenoid.Value.kReverse);

    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new HatchControl());
    }

}