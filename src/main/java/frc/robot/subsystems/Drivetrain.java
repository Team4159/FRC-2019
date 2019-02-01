package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.commands.drive.DriveControl;
import frc.robot.util.Constants;

public class Drivetrain extends Subsystem {

    private static Drivetrain instance;
    public static Drivetrain getInstance() {
        if(instance == null)
            instance = new Drivetrain();
        return instance;
    }

    private TalonSRX leftMasterTalon, leftSlaveTalon, rightMasterTalon, rightSlaveTalon;

    private Drivetrain() {

        leftMasterTalon = new TalonSRX(Constants.getInt("LEFT_MASTER_TALON"));
        leftSlaveTalon = new TalonSRX(Constants.getInt("LEFT_SLAVE_TALON"));

        rightMasterTalon = new TalonSRX(Constants.getInt("RIGHT_MASTER_TALON"));
        rightSlaveTalon = new TalonSRX(Constants.getInt("RIGHT_SLAVE_TALON"));

        leftMasterTalon.configFactoryDefault();
        leftSlaveTalon.configFactoryDefault();
        rightMasterTalon.configFactoryDefault();
        rightSlaveTalon.configFactoryDefault();

        rightMasterTalon.setInverted(true);
        rightSlaveTalon.setInverted(true);

        leftMasterTalon.enableVoltageCompensation(true);
        rightMasterTalon.enableVoltageCompensation(true);
        leftSlaveTalon.enableVoltageCompensation(true);
        rightSlaveTalon.enableVoltageCompensation(true);

        leftSlaveTalon.follow(leftMasterTalon);
        rightSlaveTalon.follow(rightMasterTalon);

        configSensors();
    }

    public void rawDrive(double left, double right) {
        leftMasterTalon.set(ControlMode.PercentOutput, left);
        rightMasterTalon.set(ControlMode.PercentOutput, right);

    }

    public void stop() {

        leftMasterTalon.set(ControlMode.PercentOutput, 0);
        rightMasterTalon.set(ControlMode.PercentOutput, 0);

    }

    private void configSensors() {

    }

    public void setRawConfig() {

    }

    public void setPathFollowingConfig() {

    }

    public void setBrownoutPreventionConfig() {

        leftMasterTalon.configOpenloopRamp(leftMasterTalon.getBusVoltage() / 0.25);
        rightMasterTalon.configOpenloopRamp(rightMasterTalon.getBusVoltage() / 0.25);

    }

    /**
     * @return Left side drivetrain velocity in feet/sec
     */
    private double getLeftVelocity() {

        return leftMasterTalon.getSelectedSensorVelocity() * 4.60194236366; // assuming 1 rev = 4096 ticks

    }

    /**
     * @return Right side drivetrain velocity in feet/sec
     */
    private double getRightVelocity() {

        return rightMasterTalon.getSelectedSensorVelocity() * 4.60194236366; // assuming 1 rev = 4096 ticks

    }

    private ShuffleboardTab tab = Shuffleboard.getTab("Drive");
    private NetworkTableEntry leftEntry = tab.add("Left Drivetrain Velocity", 0).withWidget(BuiltInWidgets.kGraph).getEntry();
    private NetworkTableEntry rightEntry = tab.add("Right Drivetrain Velocity", 0).withWidget(BuiltInWidgets.kGraph).getEntry();

    public void logDashboard() {

        leftEntry.setDouble(getLeftVelocity());
        rightEntry.setDouble(getRightVelocity());

    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveControl());
    }

}