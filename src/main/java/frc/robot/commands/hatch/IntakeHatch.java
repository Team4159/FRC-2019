package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Beak;

public class IntakeHatch extends InstantCommand {

    public IntakeHatch() {

        Beak.getInstance().intake();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
