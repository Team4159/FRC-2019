package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import static frc.team4159.robot.Constants.*;

public class Grabber extends SubsystemBase {
    private SpeedControllerGroup motors;

    public Grabber() {
        VictorSPX master_grabber_victor, slave_grabber_victor;

        master_grabber_victor = configureVictorSPX(new WPI_VictorSPX(PORTS.GRABBER_MASTER_VICTOR));
        slave_grabber_victor = configureVictorSPX(new WPI_VictorSPX(PORTS.GRABBER_SLAVE_VICTOR));
        slave_grabber_victor.setInverted(true);

        motors = new SpeedControllerGroup(
                (WPI_VictorSPX) master_grabber_victor,
                (WPI_VictorSPX) slave_grabber_victor
        );
    }

    private VictorSPX configureVictorSPX(VictorSPX victorSPX) {
        victorSPX.configFactoryDefault();
        victorSPX.setNeutralMode(NeutralMode.Brake);

        return victorSPX;
    }

    public void intakeCargo() {
        motors.set(-1);
    }

    public void outtakeCargo() {
        motors.set(1);
    }

    public void stopTaking() {
        motors.set(GRABBER_CONSTANTS.IDLE_SPEED);
    }
}
