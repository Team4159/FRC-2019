package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.arm.ExtendArm;
import frc.robot.commands.arm.RetractArm;
import frc.robot.subsystems.Elevator;


class SafeMoveElevator extends CommandGroup {

    SafeMoveElevator(int targetHeight) {

        // Extend cargo intake arm if current elevator position is below certain threshold and target position is above.
        addParallel(new ConditionalCommand(new ExtendArm()) {
            @Override
            protected boolean condition() {
                return Elevator.getInstance().isBelowCollisionThreshold() && targetHeight > 5000;
            }
        });

        // Retract cargo intake arm if current elevator position is above certain threshold and target position is below.
        addParallel(new ConditionalCommand(new RetractArm()) {
            @Override
            protected boolean condition() {
                return Elevator.getInstance().isAboveCollisionThreshold() && targetHeight < 5000;
            }
        });

        // Force top two commands to take up to 0.2 seconds to allow time for cylinders to actuate.
        addSequential(new WaitCommand(0.2));

        // Finally, move elevator to specified height.
        addSequential(new MoveElevator(targetHeight));

    }

}
