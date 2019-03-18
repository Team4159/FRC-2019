package frc.robot.commands.beak;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.subsystems.Beak;

public class BeakControl extends Command {

    private OI oi;
    private Beak beak;

    public BeakControl() {

        beak = Beak.getInstance();
        oi = OI.getInstance();
        requires(beak);

    }

    @Override
    protected void initialize() {
        beak.outtake();

    }

    @Override
    protected void execute() {

//        if(oi.beakInButtonPressed()) {
//            beak.intake();
//
//        } else if (oi.beakOutButtonPressed()) {
//            beak.outtake();
//
//        }
//
//        DoubleSolenoid.Value value = beak.getValue();


    }

    @Override
    protected boolean isFinished() {

        return false;

    }

    @Override
    protected void end() {

        beak.close();

    }

    @Override
    protected void interrupted() {

        super.interrupted();

    }
}
