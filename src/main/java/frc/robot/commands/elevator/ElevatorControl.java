package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Elevator;
import frc.robot.util.Constants;


public class ElevatorControl extends Command {

    private Elevator elevator;
    private OI oi;

    public ElevatorControl() {

        elevator = Elevator.getInstance();
        oi = OI.getInstance();
        requires(elevator);

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

//        if(oi.elevatorBotHatchButtonPressed()) {
//            new SafeMoveElevator(Constants.getInt("HATCH_LOW_HEIGHT")).start();
//
//        } else if(oi.elevatorMidHatchButtonPressed()) {
//            new SafeMoveElevator(Constants.getInt("HATCH_MID_HEIGHT")).start();
//
//        } else if(oi.elevatorTopHatchButtonPressed()) {
//            new SafeMoveElevator(Constants.getInt("HATCH_TOP_HEIGHT")).start();
//
//        } else if(oi.elevatorBotCargoButtonPressed()) {
//            new SafeMoveElevator(Constants.getInt("CARGO_LOW_HEIGHT")).start();
//
//        } else if(oi.elevatorMidCargoButtonPressed()) {
//            new SafeMoveElevator(Constants.getInt("CARGO_MID_HEIGHT")).start();
//
//        } else if(oi.elevatorTopCargoButtonPressed()) {
//            new SafeMoveElevator(Constants.getInt("CARGO_TOP_HEIGHT")).start();
//
//        }

        elevator.setPercentOutput(oi.getSecondaryY());

    }


    @Override
    protected boolean isFinished() {

        return false;

    }

    @Override
    protected void end() {

        elevator.setPercentOutput(0);

    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }

}
