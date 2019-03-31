package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.subsystems.Arm;

public class ExtendArm extends TimedCommand {

    private Arm arm;

    public ExtendArm() {

        super(1.5);
        arm = Arm.getInstance();
        requires(arm);

    }

    @Override
    protected void initialize() {

        if(arm.getState() == Arm.ArmState.EXTENDED) {
            cancel();
        }

        arm.setState(Arm.ArmState.EXTENDED);
    }

    @Override
    protected void execute() {

        arm.setPercentOutput(-1.0);

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
