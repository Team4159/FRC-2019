package frc.robot.subsystems;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.superstructure.SuperstructureControl;
import frc.robot.util.CameraThread;
import frc.robot.util.Constants;
import frc.robot.util.enums.Orientation;


public class Superstructure extends Subsystem {

    private static Superstructure instance;
    public static Superstructure getInstance() {
        if(instance == null)
            instance = new Superstructure();
        return instance;
    }

    private Orientation orientation;
    private PowerDistributionPanel pdp;
    private Compressor compressor;
    private DriverStation driverStation;

    private Superstructure() {

        orientation = Orientation.FRONT_HATCH;
        compressor = new Compressor(Constants.getInt("PCM"));
        pdp = new PowerDistributionPanel(Constants.getInt("PDP"));
        driverStation = DriverStation.getInstance();

    }

    /*
     * Toggles orientation to cargo side if currently hatch side, and vice versa
     */
    public void reverseOrientation() {

        orientation = (orientation == Orientation.FRONT_HATCH) ? Orientation.BACK_CARGO : Orientation.FRONT_HATCH;
        Drivetrain.getInstance().setOrientation(orientation);
        CameraThread.getInstance().setOrientation(orientation);

    }

    Orientation getOrientation() {

        return orientation;

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

    /**
     * @return "RED1", "RED2", "BLUE3", etc.
     */
    public String getAlliance() {

        return HAL.getAllianceStation().toString();

    }

    public int getMatchNumber() {

        return driverStation.getMatchNumber();

    }

    public String getMode() {

        return driverStation.isDisabled() ? "DISABLED" : (driverStation.isAutonomous() ? "AUTONOMOUS" : "TELEOPERATED");

    }

    @Override
    public void initDefaultCommand() {
      
        setDefaultCommand(new SuperstructureControl());
      
    }

}

