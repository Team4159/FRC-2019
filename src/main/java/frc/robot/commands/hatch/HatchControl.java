package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Hatch;


public class HatchControl extends Command {

    private Hatch hatch;
    private OI oi;

    public HatchControl() {

        hatch = Hatch.getInstance();
        oi = OI.getInstance();
        requires(hatch);

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        if(oi.hatchButtonPressed()) {

            if(hatch.isRaised()) {
                hatch.lower();

            } else {
                hatch.raise();

            }
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
