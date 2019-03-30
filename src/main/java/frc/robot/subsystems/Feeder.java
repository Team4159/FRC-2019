package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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

    private TalonSRX feederTalon;

    private Feeder() {

        feederTalon = new TalonSRX(Constants.getInt("FEEDER_TALON"));

        /* Factory default to prevent unexpected behavior */
        feederTalon.configFactoryDefault();

        /* Set brake mode */
        feederTalon.setNeutralMode(NeutralMode.Brake);

    }

    public void intake() {

        feederTalon.set(ControlMode.PercentOutput, 1.0);

    }

    public void outtake() {

        feederTalon.set(ControlMode.PercentOutput, -1.0);

    }

    public void stop() {

        feederTalon.set(ControlMode.PercentOutput, 0);

    }

    @Override
    protected void initDefaultCommand() {

        setDefaultCommand(new FeederControl());

    }
}