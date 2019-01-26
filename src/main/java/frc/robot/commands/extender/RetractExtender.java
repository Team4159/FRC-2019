package frc.robot.commands.extender;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Extender;

public class RetractExtender extends InstantCommand {

    public RetractExtender() {

        Extender.getInstance().in();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
