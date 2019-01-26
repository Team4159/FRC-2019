package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.cargo.ExtenderControl;
import frc.robot.util.Constants;

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
        extenderSolenoid = new DoubleSolenoid(Constants.getInt("EXTENDER_SOLENOID_A"), Constants.getInt("EXTENDER_SOLENOID_B"));
    }

    private void out(){
        extenderSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void in(){
        extenderSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggle(){
        if (extenderSolenoid.get() == DoubleSolenoid.Value.kForward) {
            in();
        } else {
            out();
        }
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ExtenderControl());
    }

}
