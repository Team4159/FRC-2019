package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Arm;


class RetractArm extends InstantCommand {

    RetractArm() {

        Arm.getInstance().retract();

    }

}