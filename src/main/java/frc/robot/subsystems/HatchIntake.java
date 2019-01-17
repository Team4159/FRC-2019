package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.MoveHatchIntake;
import frc.robot.util.Constants;

public class HatchIntake extends Subsystem{
    private static HatchIntake instance;
    public static HatchIntake getInstance(){
        if(instance==null){
            instance = new HatchIntake();
        }
        return instance;
    }

    public DoubleSolenoid solenoid1;
    public DoubleSolenoid solenoid2;
    public Constants constants;

    private HatchIntake(){
        constants = Constants.getInstance();
        solenoid1 = new DoubleSolenoid(constants.getInt("Solenoid1Channell"),constants.getInt("Solenoid1Channel2"));
        solenoid2 = new DoubleSolenoid(constants.getInt("Solenoid2Channel1"),constants.getInt("Solenoid2Channel2"));
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
        setDefaultCommand(new MoveHatchIntake());
    }
}