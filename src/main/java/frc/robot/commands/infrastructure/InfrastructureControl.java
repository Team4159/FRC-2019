package frc.robot.commands.infrastructure;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Infrastructure;

public class InfrastructureControl extends Command {

    private Infrastructure infrastructure;
    private OI oi;

    public InfrastructureControl() {

        infrastructure = Infrastructure.getInstance();
        oi = OI.getInstance();
        requires(infrastructure);

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        if (oi.getFlipButtonPressed()) {
            infrastructure.toggleOrientation();
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
