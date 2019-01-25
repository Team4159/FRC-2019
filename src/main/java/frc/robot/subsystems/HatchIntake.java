package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.MoveHatchIntake;
import frc.robot.util.Constants;

public class HatchIntake extends Subsystem {
    
    private static HatchIntake instance;
    public static HatchIntake getInstance(){
        if(instance == null){
            instance = new HatchIntake();
        }
        return instance;
    }

    private DoubleSolenoid solenoid;

    private HatchIntake(){
        solenoid = new DoubleSolenoid(Constants.getInt("HATCH_SOLENOID_A"), Constants.getInt("HATCH_SOLENOID_B"));
    }
    
    public void outSolenoid(){
        
        solenoid.set(DoubleSolenoid.Value.kForward);
       
    }
    public void inSolenoid(){
        solenoid.set(DoubleSolenoid.Value.kReverse);
    }



    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveHatchIntake());
    }
}
