package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.cargo.FeederControl;
import frc.robot.util.Constants;

public class Feeder extends Subsystem {

    private VictorSPX feederVictor;
    private static Feeder instance;

    public static Feeder getInstance() {
        if (instance == null) {
            instance = new Feeder();
        }
        return instance;
    }

    private Feeder() {
        feederVictor = new VictorSPX(Constants.getInt("FEEDER_VICTOR"));
    }

    public void stop() {
        feederVictor.set(ControlMode.PercentOutput, 0);
    }

    public void intake() {
        feederVictor.set(ControlMode.PercentOutput, 1);
    }

    public void outtake() {
        feederVictor.set(ControlMode.PercentOutput, -1);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new FeederControl());
    }


}

