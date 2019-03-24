package frc.robot.commands.hooks;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Hooks;

public class RetractHooks extends InstantCommand {

    public RetractHooks() {

        Hooks.getInstance().retract();

    }

}
