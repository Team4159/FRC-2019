package frc.robot.util.motion;

import frc.robot.util.Constants;
import frc.robot.util.enums.MotionProfileDirection;
import jaci.pathfinder.Trajectory;

@SuppressWarnings("FieldCanBeLocal")
public class RamseteFollower {

    // Should be greater than zero and this increases correction
    private double b = 1.5;

    // Should be between zero and one and this increases dampening
    private double zeta = 0.7;

    // Holds what segment we are on
    private int segmentIndex;
    private Trajectory.Segment current;

    // The trajectory to follow
    private Trajectory trajectory;

    // The robot's x and y position and angle
    private Odometry odometry;

    // Variable used to calculate linear and angular velocity
    private double lastTheta, nextTheta;
    private double k, thetaError, sinThetaErrorOverThetaError;
    private double desiredAngularVelocity, linearVelocity, angularVelocity;
    private double odometryError;

    // Constants
    private static final double EPSILON = 0.00000001;
    private static final double TWO_PI = 2 * Math.PI;

    // Variable for holding velocity for robot to drive on
    private Velocity velocity;
    private DriveSignal driveSignal;
    private double left, right;

    public RamseteFollower(Trajectory trajectory, MotionProfileDirection direction) {
        this.trajectory = direction == MotionProfileDirection.FORWARD ? trajectory : TrajectoryUtil.reversePath(trajectory);

        segmentIndex = 0;
        odometry = Odometry.getInstance();

        driveSignal = new DriveSignal();
    }

    public RamseteFollower(Trajectory trajectory, double b, double zeta, MotionProfileDirection direction) {
        this(trajectory, direction);

        this.b = b;
        this.zeta = zeta;
    }

    public Velocity getVelocity() {
        if(isFinished()){
            return new Velocity(0, 0);
        }

        current = trajectory.get(segmentIndex);

        desiredAngularVelocity = calculateDesiredAngular();

        linearVelocity = calculateLinearVelocity(current.x, current.y, current.heading, current.velocity, desiredAngularVelocity);
        angularVelocity = calculateAngularVelocity(current.x, current.y, current.heading, current.velocity, desiredAngularVelocity);

        return new Velocity(linearVelocity, angularVelocity);
    }

    public DriveSignal getNextDriveSignal() {

        velocity = getVelocity();

        left = (-(velocity.getAngular() * Constants.getDouble("WHEELBASE")) + (2 * velocity.getLinear())) / 2;
        right = ((velocity.getAngular() * Constants.getDouble("WHEELBASE")) + (2 * velocity.getLinear())) / 2;

        driveSignal.setLeft(left);
        driveSignal.setRight(right);

        segmentIndex++;

        return driveSignal;

    }

    private double calculateDesiredAngular() {

        if(segmentIndex < trajectory.length() - 1) {
            lastTheta = trajectory.get(segmentIndex).heading;
            nextTheta = trajectory.get(segmentIndex + 1).heading;
            return boundHalfRadians(nextTheta - lastTheta) / current.dt;

        } else {
            return 0;
        }

    }

    private double calculateLinearVelocity(double desiredX, double desiredY, double desiredTheta, double desiredLinearVelocity, double desiredAngularVelocity) {

        k = calculateK(desiredLinearVelocity, desiredAngularVelocity);
        thetaError = boundHalfRadians(desiredTheta - odometry.getTheta());
        odometryError = (Math.cos(odometry.getTheta()) * (desiredX - odometry.getX())) + (Math.sin(odometry.getTheta()) * (desiredY - odometry.getY()));
        return (desiredLinearVelocity * Math.cos(thetaError)) + (k * odometryError);

    }

    private double calculateAngularVelocity(double desiredX, double desiredY, double desiredTheta, double desiredLinearVelocity, double desiredAngularVelocity) {

        k = calculateK(desiredLinearVelocity, desiredAngularVelocity);
        thetaError = boundHalfRadians(desiredTheta - odometry.getTheta());

        if(Math.abs(thetaError) < EPSILON) {
            //This is for the limit as sin(x)/x approaches zero
            sinThetaErrorOverThetaError = 1;

        } else {
            sinThetaErrorOverThetaError = Math.sin(thetaError)/thetaError;

        }

        odometryError = (Math.cos(odometry.getTheta()) * (desiredY - odometry.getY())) - (Math.sin(odometry.getTheta()) * (desiredX - odometry.getX()));

        return desiredAngularVelocity + (b * desiredLinearVelocity * sinThetaErrorOverThetaError * odometryError) + (k * thetaError);

    }

    private double calculateK(double desiredLinearVelocity, double desiredAngularVelocity) {

        return 2 * zeta * Math.sqrt(Math.pow(desiredAngularVelocity, 2) + (b * Math.pow(desiredLinearVelocity, 2)));

    }

    private double boundHalfRadians(double radians) {

        while (radians >= Math.PI) radians -= TWO_PI;
        while (radians < -Math.PI) radians += TWO_PI;
        return radians;

    }

    public Trajectory.Segment currentSegment() {

        return current;

    }

    public boolean isFinished() {

        return segmentIndex >= trajectory.length();

    }
}