package frc.robot.subsystems;

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

    public Solenoid solenoid1;
    public Solenoid solenoid2;

    private HatchIntake(){
        solenoid1 = new Solenoid(0);
        solenoid2 = new Solenoid(1);
    }
    @Override
    protected void initDefaultCommand() {

    }
}