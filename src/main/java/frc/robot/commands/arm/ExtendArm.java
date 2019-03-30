package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Arm;

public class ExtendArm extends Command {

    private Arm arm;

    public ExtendArm() {

        arm = Arm.getInstance();

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        arm.setPercentOutput(-0.5);
        if(arm.isStalling()) {
            arm.resetEncoder();
        }

    }

    @Override
    protected boolean isFinished() {

        return arm.isStalling();

    }

    @Override
    protected void end() {

        arm.stop();

    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }

}
