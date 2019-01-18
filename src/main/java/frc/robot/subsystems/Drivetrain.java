package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.jetbrains.annotations.NotNull;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.commands.DriveControl;
import frc.robot.util.Constants;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {

    private static Drivetrain instance;

    public static Drivetrain getInstance() {
        if(instance == null)
            instance = new Drivetrain();
        return instance;
    }

    private static Constants constants;
    private DrivetrainControlState state;

    private TalonSRX leftMasterTalon, leftSlaveTalon, rightMasterTalon, rightSlaveTalon;

    private Drivetrain() {

        constants = Constants.getInstance();

        leftMasterTalon = new TalonSRX(constants.getInt("LEFT_MASTER_TALON"));
        leftSlaveTalon = new TalonSRX(constants.getInt("LEFT_SLAVE_TALON"));
        leftSlaveTalon.follow(leftMasterTalon);

        rightMasterTalon = new TalonSRX(constants.getInt("RIGHT_MASTER_TALON"));
        rightSlaveTalon = new TalonSRX(constants.getInt("RIGHT_SLAVE_TALON"));
        rightSlaveTalon.follow(rightMasterTalon);

        configSensors();

    }

    public void rawDrive(@NotNull double left, @NotNull double right) {

        leftMasterTalon.set(ControlMode.PercentOutput, left);
        rightMasterTalon.set(ControlMode.PercentOutput, right);

    }

    public void stop() {

        leftMasterTalon.set(ControlMode.PercentOutput, 0);
        rightMasterTalon.set(ControlMode.PercentOutput, 0);

    }

    private void configSensors() {

    }

    public DrivetrainControlState getState() {
        return state;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveControl());
    }

    public enum DrivetrainControlState {
        OPEN_LOOP,
        VISION,
        PATH_FOLLOWING
    }

}