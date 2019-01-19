package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.CargoControl;
import frc.robot.util.Constants;

public class Cargo extends Subsystem {

    private static Cargo instance;

    private VictorSPX intakeVictor;

    private Cargo() {
        intakeVictor = new VictorSPX(Constants.getInt("CARGO_VICTOR_1"));
    }

    public static Cargo getInstance() {
        if (instance == null) {
            instance = new Cargo();
        }
        return instance;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new CargoControl());
    }

    public void stopCargo() {
        intakeVictor.set(ControlMode.PercentOutput, 0);
    }

    public void intakeCargo() {
        intakeVictor.set(ControlMode.PercentOutput, 1);
    }

    public void outtakeCargo() {
        intakeVictor.set(ControlMode.PercentOutput, -1);
    }

}
