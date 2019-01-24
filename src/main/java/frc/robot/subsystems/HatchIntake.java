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
        solenoid1 = new DoubleSolenoid(constants.getInt("SOLENOID1CHANNEL1"),constants.getInt("SOLENOID1CHANNEL2"));
        solenoid2 = new DoubleSolenoid(constants.getInt("SOLENOID2CHANNEL1"),constants.getInt("SOLENOID2CHANNEL2"));
    }
    public void outSolenoids(){

        solenoid1.set(DoubleSolenoid.Value.kForward);
        solenoid2.set(DoubleSolenoid.Value.kForward);


    }
    public void inSolenoids(){

        solenoid1.set(DoubleSolenoid.Value.kReverse);
        solenoid2.set(DoubleSolenoid.Value.kReverse);

    }



    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveHatchIntake());
    }
}