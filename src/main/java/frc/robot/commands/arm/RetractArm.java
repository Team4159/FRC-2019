package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.subsystems.Arm;


public class RetractArm extends TimedCommand {

    private Arm arm;

    public RetractArm() {

        super(1);
        arm = Arm.getInstance();
        requires(arm);

    }

    @Override
    protected void initialize() {

        if(arm.getState() == Arm.ArmState.RETRACTED) {
            cancel();
        }

        arm.setState(Arm.ArmState.RETRACTED);

    }

    @Override
    protected void execute() {

        arm.setPercentOutput(1);

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