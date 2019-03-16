package frc.robot.commands.beak;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Beak;

public class OpenBeak extends InstantCommand {

    public OpenBeak() {

        Beak.getInstance().outtake();

    }

}
