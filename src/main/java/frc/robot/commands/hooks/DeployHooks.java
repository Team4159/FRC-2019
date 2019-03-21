package frc.robot.commands.hooks;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Hooks;


public class DeployHooks extends InstantCommand {

    public DeployHooks() {

        Hooks.getInstance().retract();

    }

}
