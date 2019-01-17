package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.commands.Cargo.ControlCargo;

public class Cargo extends Subsystem {
    private static Cargo instance;
    //TODO:Determine type of motor controller and configuration
    //private static VictorSPX victor;
    private static TalonSRX intakeTalon;

    private Cargo() {
        intakeTalon = new TalonSRX(3);
    }

    public static Cargo getInstance() {
        if (instance == null) {
            instance = new Cargo();
        }
        return instance;
    }

    @Override
    protected void initDefaultCommand() {setDefaultCommand(new ControlCargo());}

    public void setSpeed(double speed) {
        intakeTalon.set(ControlMode.PercentOutput, speed);
    }

    public void stopCargo() {
        setSpeed(0);
    }

    public void intakeCargo() { //TODO:Determine motor speed
        setSpeed(1);
    }

    public void outtakeCargo() { //TODO:Determine motor speed
        setSpeed(-1);
    }

}
