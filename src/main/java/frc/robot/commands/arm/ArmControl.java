package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Arm;

public class ArmControl extends Command {

    private Arm arm;
    private OI oi;

    public ArmControl() {

        arm = Arm.getInstance();
        oi = OI.getInstance();

        requires(arm);

    }

    @Override
    protected void execute() {

        if(arm.limitSwitchPressed()) {
            arm.resetEncoder();
        }

        if(oi.armButtonPressed()) {

            if(arm.isExtended()) {
                arm.retract();

            } else {
                arm.extend();
            }
        }

    }

    @Override
    protected boolean isFinished() {

        return false;

    }

    @Override
    protected void end() {

        arm.setPercentOutput(0);

    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }
}
