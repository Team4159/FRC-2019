package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Hatch;


public class RaiseHatch extends InstantCommand {

    public RaiseHatch() {

        Hatch.getInstance().raise();

    }

}
