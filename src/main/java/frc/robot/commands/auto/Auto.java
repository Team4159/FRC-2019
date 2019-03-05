package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.util.REVDigitBoard;


public class Auto extends InstantCommand {

    @Override
    protected void initialize() {

        int autoMode = REVDigitBoard.getInstance().getCounter();

        switch(autoMode) {
            case 1:
                // new ExampleCommand.start();
                break;
            case 2:
                // etc.
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }

    }

}
