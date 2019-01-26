package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Hatch;

public class OpenHatch extends InstantCommand {

    public OpenHatch() {

        Hatch.getInstance().open();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
