package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.util.Constants;

public class Superstructure extends Subsystem {

    private static Superstructure instance;

    public static Superstructure getInstance() {
        if(instance == null)
            instance = new Superstructure();
        return instance;
    }

    private PowerDistributionPanel pdp;
    private Compressor compressor;

    private Superstructure() {

        compressor = new Compressor();
        pdp = new PowerDistributionPanel(Constants.getInt("PDP"));
        // maybe add driverstation class

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

    // add methods as needed. getters for logging


    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}

