package frc.robot.commands.superstructure;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Superstructure;

public class SuperstructureControl extends Command {

    private Superstructure superstructure;
    private OI oi;

    public SuperstructureControl() {

        superstructure = Superstructure.getInstance();
        oi = OI.getInstance();
        requires(superstructure);

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        if (oi.reverseButtonPressed()) {
            superstructure.reverseOrientation();
        }

    }

    @Override
    protected boolean isFinished() {

        return false;

    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }
}
