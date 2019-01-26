package frc.robot.commands.extender;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Extender;

public class ExtendExtender extends InstantCommand {

    public ExtendExtender() {

        Extender.getInstance().out();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
