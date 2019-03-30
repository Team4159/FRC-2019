package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class RetractArm extends InstantCommand {

    public RetractArm() {

        Arm.getInstance().retract();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
