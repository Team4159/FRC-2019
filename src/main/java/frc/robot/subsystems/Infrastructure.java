package frc.robot.subsystems;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.infrastructure.ToggleOrientation;
import frc.robot.util.Constants;

public class Infrastructure extends Subsystem {

    private static Infrastructure instance;
    public static Infrastructure getInstance() {
        if(instance == null)
            instance = new Infrastructure();
        return instance;
    }

    public enum Orientation {
        Front,
        Back
    }

    private static Orientation orientation = Orientation.Front;

    private PowerDistributionPanel pdp;
    private Compressor compressor;
    private DriverStation driverStation;

    private Infrastructure() {

        compressor = new Compressor(Constants.getInt("PCM"));
        pdp = new PowerDistributionPanel(Constants.getInt("PDP"));
        driverStation = DriverStation.getInstance();

    }

    public void toggleState() {
        if (orientation == Orientation.Front) {
            orientation = Orientation.Back;
        } else {
            orientation = Orientation.Front;
        }
    }

    public Orientation getOrientation() {
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
        setDefaultCommand(new ToggleOrientation());
    }

}

