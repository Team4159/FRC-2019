package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Hatch;

public class CloseHatch extends InstantCommand {

    public CloseHatch() {

        Hatch.getInstance().close();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
