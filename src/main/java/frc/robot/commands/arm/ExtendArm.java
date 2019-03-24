package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class ExtendArm extends InstantCommand {

    public ExtendArm() {

        Arm.getInstance().extend();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
