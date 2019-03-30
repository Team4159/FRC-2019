package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.robot.subsystems.Arm;


public class ArmControl extends CommandGroup {

    private Arm arm;

    public ArmControl() {

        arm = Arm.getInstance();

        addSequential(new ConditionalCommand(new RetractArm(), new ExtendArm()) {
            @Override
            protected boolean condition() {
                return arm.isRetracted();
            }
        });

    }

}
