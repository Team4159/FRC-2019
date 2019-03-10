package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Elevator;

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

        if(oi.elevatorBotHatchButtonPressed()) {
            elevator.setHatchBotPosition();

        } else if(oi.elevatorMidHatchButtonPressed()) {
            elevator.setHatchMidPosition();

        } else if(oi.elevatorTopHatchButtonPressed()) {
            elevator.setHatchTopPosition();

        } else if(oi.elevatorBotCargoButtonPressed()) {
            elevator.setCargoBotPosition();

        } else if(oi.elevatorMidCargoButtonPressed()) {
            elevator.setCargoMidPosition();

        } else if(oi.elevatorTopCargoButtonPressed()) {
            elevator.setCargoTopPosition();

        }

        elevator.updatePosition();

    }


    @Override
    protected boolean isFinished() {

        return false;

    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }

}
