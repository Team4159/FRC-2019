package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Beak;

public class OuttakeHatch extends InstantCommand {

    public OuttakeHatch() {

        Beak.getInstance().outtake();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
