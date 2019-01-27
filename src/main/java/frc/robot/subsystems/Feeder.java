package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.feeder.FeederControl;
import frc.robot.util.Constants;

public class Feeder extends Subsystem {

    private static Feeder instance;
    public static Feeder getInstance() {
        if (instance == null) {
            instance = new Feeder();
        }
        return instance;
    }

    private VictorSPX feederVictor;

    private Feeder() {

        feederVictor = new VictorSPX(Constants.getInt("FEEDER_VICTOR"));

    }

    public void intake() {

        feederVictor.set(ControlMode.PercentOutput, 1);

    }

    public void outtake() {

        feederVictor.set(ControlMode.PercentOutput, -1);

    }

    public void stop() {

        feederVictor.set(ControlMode.PercentOutput, 0);

    }

    @Override
    protected void initDefaultCommand() {

        setDefaultCommand(new FeederControl());

    }
}

