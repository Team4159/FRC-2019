package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.HatchControl;
import frc.robot.util.Constants;

public class Hatch extends Subsystem{

    private static Hatch instance;

    public static Hatch getInstance(){
        if(instance == null)
            instance = new Hatch();
        return instance;
    }

    private Constants constants;

    private DoubleSolenoid solenoid1;
    private DoubleSolenoid solenoid2;

    private Hatch() {

        constants = Constants.getInstance();

        solenoid1 = new DoubleSolenoid(constants.getInt("Solenoid1Channell"), constants.getInt("Solenoid1Channel2"));
        solenoid2 = new DoubleSolenoid(constants.getInt("Solenoid2Channel1"), constants.getInt("Solenoid2Channel2"));

    }

    public void outSolenoid1(){
        solenoid1.set(DoubleSolenoid.Value.kForward);
    }

    public void inSolenoid1(){
        solenoid1.set(DoubleSolenoid.Value.kReverse);
    }

    public void outSolenoid2(){
        solenoid2.set(DoubleSolenoid.Value.kForward);
    }

    public void inSolenoid2(){
        solenoid2.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new HatchControl());
    }
}