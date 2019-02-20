package frc.robot.commands.infrastructure;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Infrastructure;

public class ToggleOrientation extends Command {

    private Infrastructure infrastructure;
    private OI oi;

    public ToggleOrientation() {

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
