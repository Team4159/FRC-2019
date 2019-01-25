package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.cargo.ExtenderControl;

public class CargoExtender extends Subsystem {

    private DoubleSolenoid extenderSolenoid;
    private static CargoExtender instance;

    public static CargoExtender getInstance() {
        if (instance == null) {
            instance = new CargoExtender();
        }
        return instance;
    }

    private CargoExtender() {
        extenderSolenoid = new DoubleSolenoid(4, 5);
    }

    public void out(){
        extenderSolenoid.set(DoubleSolenoid.Value.kForward);

    }
    public void in(){
        extenderSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ExtenderControl());
    }


}
