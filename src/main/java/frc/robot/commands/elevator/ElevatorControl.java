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

        elevator.setPercentOutput(oi.getSecondaryY() / 2);

        /*
         * For testing:
         * Use joystick to determine gains
         * Define a button to go about halfway
         *
         */

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
