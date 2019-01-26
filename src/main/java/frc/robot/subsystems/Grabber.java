package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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

    private VictorSPX grabberVictor1, grabberVictor2;

    private Grabber() {

        grabberVictor1 = new VictorSPX(Constants.getInt("GRABBER_VICTOR_1"));
        grabberVictor2 = new VictorSPX(Constants.getInt("GRABBER_VICTOR_2"));

    }

    public void intake() {

        grabberVictor1.set(ControlMode.PercentOutput, 1);
        grabberVictor2.set(ControlMode.PercentOutput, -1);

    }

    public void outtake() {

        grabberVictor1.set(ControlMode.PercentOutput, -1);
        grabberVictor2.set(ControlMode.PercentOutput, 1);

    }

    public void stop() {

        grabberVictor1.set(ControlMode.PercentOutput, 0);
        grabberVictor2.set(ControlMode.PercentOutput, 0);

    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new GrabberControl());
    }


}
