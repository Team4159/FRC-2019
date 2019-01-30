package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Hatch;

public class OuttakeHatch extends InstantCommand {

    public OuttakeHatch() {

        Hatch.getInstance().outtake();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
