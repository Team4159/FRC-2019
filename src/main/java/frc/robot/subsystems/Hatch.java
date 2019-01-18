package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.HatchControl;
import frc.robot.util.Constants;

public class Hatch extends Subsystem{

    private static Hatch instance;
    private DoubleSolenoid solenoid1;
    private DoubleSolenoid solenoid2;

    public static Hatch getInstance(){
        if(instance == null)
            instance = new Hatch();
        return instance;
    }

    private Hatch() {

        solenoid1 = new DoubleSolenoid(Constants.getInt("SOLENOID1CHANNEL1"), Constants.getInt("SOLENOID1CHANNEL2"));
        solenoid2 = new DoubleSolenoid(Constants.getInt("SOLENOID2CHANNEL1"), Constants.getInt("SOLENOID2CHANNEL2"));

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