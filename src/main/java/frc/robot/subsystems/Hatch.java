package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.hatch.HatchControl;
import frc.robot.util.Constants;

public class Hatch extends Subsystem {

    private static Hatch instance;
    private DoubleSolenoid solenoid1;
    private DoubleSolenoid solenoid2;

    public static Hatch getInstance(){
        if(instance == null)
            instance = new Hatch();
        return instance;
    }

    private DoubleSolenoid solenoid;

    private Hatch() {

        solenoid = new DoubleSolenoid(Constants.getInt("PCM1"), Constants.getInt("HATCH_A"), Constants.getInt("HATCH_B"));

    }

    public void open() {
        solenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void close() {
        solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new HatchControl());
    }
}