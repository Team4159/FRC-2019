package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ElevatorControl;
import frc.robot.util.Constants;

public class Elevator extends Subsystem {

    private static Elevator instance;

    public static Elevator getInstance() {
        if(instance == null)
            instance = new Elevator();
        return instance;
    }

    private ElevatorControlState state;
    private static Constants constants;


    private TalonSRX elevatorTalon;
    private VictorSPX elevatorVictor1, elevatorVictor2, elevatorVictor3;


    private Elevator() {

        constants = Constants.getInstance();

        elevatorTalon = new TalonSRX(constants.getInt("ELEVATOR_TALON"));
        elevatorVictor1 = new VictorSPX(constants.getInt("ELEVATOR_VICTOR_1"));
        elevatorVictor2 = new VictorSPX(constants.getInt("ELEVATOR_VICTOR_2"));
        elevatorVictor3 = new VictorSPX(constants.getInt("ELEVATOR_VICTOR_3"));

        state = ElevatorControlState.OPEN_LOOP;

    }

    public void rawControl(double speed) {

    }

    public void setLowPosition() {

    }

    public void setMediumPosition() {

    }

    public void setHighPosition() {

    }

    public ElevatorControlState getState() {
        return state;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ElevatorControl());
    }

    private enum ElevatorControlState {
        OPEN_LOOP,
        MOTION_MAGIC
    }
}

