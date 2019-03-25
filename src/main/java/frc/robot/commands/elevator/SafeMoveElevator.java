package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.arm.ExtendArm;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;


class SafeMoveElevator extends CommandGroup {

    private Elevator elevator;
    private Arm arm;
    private boolean isRetracted;

    SafeMoveElevator(int targetHeight) {

        elevator = Elevator.getInstance();
        arm = Arm.getInstance();

        /*
         * Extend cargo intake arm if:
         * 1. Current elevator position is below certain threshold and target position is above, or vice versa AND
         * 2. Arm is currently retracted
         */
        addParallel(new ConditionalCommand(new ExtendArm()) {
            @Override
            protected boolean condition() {
                isRetracted = !arm.isExtended();
                return ((elevator.isBelowCollisionThreshold() && targetHeight > 5000) || (elevator.isAboveCollisionThreshold() && targetHeight < 5000)) && isRetracted;
            }
        });

        // Wait 0.2 seconds to allow time for arm pneumatic cylinder to actuate if it is currently retracted
        addSequential(new ConditionalCommand(new WaitCommand(0.2)) {
            @Override
            protected boolean condition() {
                return isRetracted;
            }
        });

        // Finally, move elevator to specified height.
        addSequential(new MoveElevator(targetHeight));

    }

}
