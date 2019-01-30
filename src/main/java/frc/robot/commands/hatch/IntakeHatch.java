package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Hatch;

public class IntakeHatch extends InstantCommand {

    public IntakeHatch() {

        Hatch.getInstance().intake();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
