package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Hatch;


public class LowerHatch extends InstantCommand {

    public LowerHatch() {

        Hatch.getInstance().lower();

    }

}
