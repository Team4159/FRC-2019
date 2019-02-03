package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.util.Vision;

public class SwitchCamera extends InstantCommand {

    public SwitchCamera() {

        Vision.getInstance().switchCamera();

    }

    @Override
    protected boolean isFinished() {

        return true;

    }

}
