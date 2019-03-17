package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.grabber.GrabberControl;
import frc.robot.util.Constants;

public class Grabber extends Subsystem {

    private static Grabber instance;
    public static Grabber getInstance() {
        if (instance == null) {
            instance = new Grabber();
        }
        return instance;
    }

    private TalonSRX grabberTalon1, grabberTalon2;

    private Grabber() {

        grabberTalon1 = new TalonSRX(Constants.getInt("GRABBER_TALON_1"));
        grabberTalon2 = new TalonSRX(Constants.getInt("GRABBER_TALON_2"));

        /* Factory default hardware to prevent unexpected behavior */
        grabberTalon1.configFactoryDefault();
        grabberTalon2.configFactoryDefault();

        /* Set brake mode */
        grabberTalon1.setNeutralMode(NeutralMode.Brake);
        grabberTalon2.setNeutralMode(NeutralMode.Brake);

    }

    public void intake() {

        grabberTalon1.set(ControlMode.PercentOutput, 1.0);
        grabberTalon2.set(ControlMode.PercentOutput, -1.0);

    }

    public void outtake() {

        grabberTalon1.set(ControlMode.PercentOutput, -0.25);
        grabberTalon2.set(ControlMode.PercentOutput, 0.25);

    }

    public void stop() {

        grabberTalon1.set(ControlMode.PercentOutput, 0);
        grabberTalon2.set(ControlMode.PercentOutput, 0);

    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new GrabberControl());
    }

}
