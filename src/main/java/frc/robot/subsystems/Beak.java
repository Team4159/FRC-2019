package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.beak.BeakControl;
import frc.robot.util.Constants;

public class Beak extends Subsystem {

    private static Beak instance;
    public static Beak getInstance(){
        if(instance == null)
            instance = new Beak();
        return instance;
    }

    private DoubleSolenoid hatchSolenoid;

    private Beak() {

        hatchSolenoid = new DoubleSolenoid(Constants.getInt("PCM"),
                Constants.getInt("BEAK_A"), Constants.getInt("BEAK_B"));

    }

    public void intake() {

        hatchSolenoid.set(DoubleSolenoid.Value.kForward);

    }

    public void outtake() {

        hatchSolenoid.set(DoubleSolenoid.Value.kReverse);

    }

    public DoubleSolenoid.Value getValue() {

        return hatchSolenoid.get();

    }

    @Override
    protected void initDefaultCommand() {

        setDefaultCommand(new BeakControl());

    }

}