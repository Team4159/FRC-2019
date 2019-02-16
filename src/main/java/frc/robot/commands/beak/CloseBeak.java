package frc.robot.commands.beak;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Beak;

public class CloseBeak extends InstantCommand {

    public CloseBeak() {

        Beak.getInstance().intake();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
