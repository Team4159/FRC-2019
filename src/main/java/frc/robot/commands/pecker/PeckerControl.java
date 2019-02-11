package frc.robot.commands.pecker;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Pecker;


public class PeckerControl extends Command {

    private Pecker pecker;
    private OI oi;

    public PeckerControl() {

        pecker = Pecker.getInstance();
        oi = OI.getInstance();
        requires(pecker);

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        if(oi.peckerButtonPressed()) {

            if(pecker.getValue() == DoubleSolenoid.Value.kForward) {
                pecker.in();

            } else if (pecker.getValue() == DoubleSolenoid.Value.kReverse) {
                pecker.out();

            } else {
                pecker.in();

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
