package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.robot.subsystems.Arm;


public class ArmControl extends CommandGroup {

    private Arm arm;

    public ArmControl() {

        arm = Arm.getInstance();

    }

    @Override
    protected void initialize() {

        addSequential(new ConditionalCommand(new ExtendArm(), new RetractArm()) {
            @Override
            protected boolean condition() {
                return arm.isRetracted();
            }
        });

    }

}
