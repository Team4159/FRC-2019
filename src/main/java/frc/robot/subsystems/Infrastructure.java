package frc.robot.subsystems;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.util.Constants;

public class Infrastructure extends Subsystem {

    private static Infrastructure instance;
    public static Infrastructure getInstance() {
        if(instance == null)
            instance = new Infrastructure();
        return instance;
    }

    private PowerDistributionPanel pdp;
    private Compressor compressor;
    private DriverStation driverStation;

    private Infrastructure() {

        compressor = new Compressor();
        pdp = new PowerDistributionPanel(Constants.getInt("PDP"));
        driverStation = DriverStation.getInstance();

    }

    public void disableCompressor() {

        compressor.setClosedLoopControl(false);

    }

    public void enableCompressor() {

        compressor.setClosedLoopControl(true);

    }

    public double getRobotVoltage() {

        return pdp.getVoltage();

    }

    /* Return RED1, RED2, BLUE3, etc. */
    public String getAlliance() {

        return HAL.getAllianceStation().toString();

    }

    public int getMatchNumber() {

        return driverStation.getMatchNumber();

    }

    public String getMode() {

        return driverStation.isDisabled() ? "DISABLED" : (driverStation.isAutonomous() ? "AUTONOMOUS" : "TELEOPERATED");

    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}

