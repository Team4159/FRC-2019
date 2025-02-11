package frc.robot.commands.hooks;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Hooks;

public class HooksControl extends Command {

    private OI oi;
    private Hooks hooks;

    public HooksControl() {

        hooks = Hooks.getInstance();
        oi = OI.getInstance();
        requires(hooks);

    }

    @Override
    protected void initialize() {
        hooks.retract();

    }

    @Override
    protected void execute() {

        if(oi.hooksButtonPressed()) {

            if(hooks.isDeployed()) {
                hooks.retract();

            } else {
                hooks.deploy();

            }
        }
    }

    @Override
    protected boolean isFinished() {

        return false;

    }

    @Override
    protected void end() {

        hooks.close();

    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }
}
