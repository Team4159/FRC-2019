package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.commands.auto.Auto;
import frc.robot.subsystems.*;
import frc.robot.util.CameraThread;
import frc.robot.util.REVDigitBoard;
import frc.robot.util.RobotLogger;
import frc.robot.util.VisionThread;
import frc.robot.util.motion.Odometry;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    private Beak beak;
    private Drivetrain drivetrain;
    private Elevator elevator;
    private Extender extender;
    private Feeder feeder;
    private Grabber grabber;
    private Superstructure superstructure;
    private Pecker pecker;
    private Odometry odometry;
    private RobotLogger robotLogger;
    private CameraThread cameraThread;
    private VisionThread visionThread;
    private OI oi;
//    private REVDigitBoard digitBoard;
    private Command autoCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        drivetrain = Drivetrain.getInstance();
        elevator = Elevator.getInstance();
        extender = Extender.getInstance();
        feeder = Feeder.getInstance();
        grabber = Grabber.getInstance();
        beak = Beak.getInstance();
        superstructure = Superstructure.getInstance();
       // pecker = Pecker.getInstance();
        oi = OI.getInstance();
        //digitBoard = REVDigitBoard.getInstance();

        autoCommand = new Auto();

//        robotLogger = RobotLogger.getInstance();
        visionThread = VisionThread.getInstance();
        cameraThread = CameraThread.getInstance();
//
//        robotLogger.start();
        visionThread.start();
        cameraThread.start();

        odometry = Odometry.getInstance();
//        new Notifier(() -> {
//
//            odometry.setCurrentEncoderPosition((drivetrain.getleftEncoderCount() + drivetrain.getRightEncoderCount()) / 2.0);
//            odometry.setDeltaPosition(RobotMath.ticksToFeet(odometry.getCurrentEncoderPosition() - odometry.getLastPosition()));
//            odometry.setTheta(Math.toRadians(Pathfinder.boundHalfDegrees(drivetrain.getYaw())));
//
//            odometry.addX(Math.cos(odometry.getTheta()) * odometry.getDeltaPosition());
//            odometry.addY(Math.sin(odometry.getTheta()) * odometry.getDeltaPosition());
//
//            odometry.setLastPosition(odometry.getCurrentEncoderPosition());
//
//        }).startPeriodic(0.01);

    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {

        //digitBoard.update();

        Scheduler.getInstance().run();

    }

    @Override
    public void autonomousInit() {

        superstructure.disableCompressor();

//        if (autoCommand != null) {
//            autoCommand.start();
//        }

    }

    @Override
    public void autonomousPeriodic() {

        Scheduler.getInstance().run();

    }

    @Override
    public void teleopInit() {

        if (autoCommand != null) {
            autoCommand.cancel(); // we might not want to cancel autoCommand if our routine takes longer
        }

        superstructure.enableCompressor();

    }

    @Override
    public void teleopPeriodic() {

        Scheduler.getInstance().run();

    }

    @Override
    public void testPeriodic() {
    }

}
