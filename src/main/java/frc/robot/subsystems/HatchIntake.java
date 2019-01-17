package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchIntake extends Subsystem{
    private static HatchIntake instance;
    public HatchIntake getInstance(){
        if(instance==null){
            instance = new HatchIntake();
        }
        return instance;
    }

    public DoubleSolenoid solenoid1;
    public DoubleSolenoid solenoid2;

    private HatchIntake(){
        solenoid1 = new DoubleSolenoid(0,1);
        solenoid2 = new DoubleSolenoid(2,3);
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

    }
}