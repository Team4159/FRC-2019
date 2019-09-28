package frc.team4159.robot.loops;

import frc.team4159.robot.Main;

public class DrivetrainLoop {
    // max drivetrain voltage in volts
    public static final double kMaxVoltage = 12.0;

    private enum State {
        IDLE(0),
        RUNNING(1),
        ESTOP(2);

        int value;
        State(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }


    private State state = State.IDLE;

    private double goal = 0.0;
    private double error = 0.0;
    private double error_velocity = 0.0;
    private double last_error = 0.0;

    private double theta_goal = 0.0;
    private double theta_error = 0.0;
    private double theta_error_velocity = 0.0;
    private double theta_last_error = 0.0;

    public void setForwardGoal(double goal) {
        this.goal = goal;
        last_error = 0.0;
    }

    public void setThetaGoal(double radians) {
        theta_goal = radians;
        theta_last_error = 0.0;
    }

    public void setGoal(double x, double y) {
        theta_goal = Math.atan(y / x);
        goal = y / Math.sin(theta_goal);
    }

    public double[] update(double encoder, double gyro, boolean enabled) {
        switch (state) {
            case IDLE:
                if (enabled) state = State.RUNNING;
                break;
            case RUNNING:
                break;
            case ESTOP:
                break;
        }

        double kP, kD, voltage;

        theta_error = theta_goal - gyro;
        theta_error_velocity = (theta_error - theta_last_error) / Main.dt;
        theta_last_error = theta_error;

        error = goal - encoder;
        error_velocity = (error - last_error) / Main.dt;
        last_error = error;

        if (Math.abs(theta_error) > 0.1) {
            kP = 100.0;
            kD = 50.0;

            voltage = Math.max(-kMaxVoltage, Math.min(kP * theta_error + kD * theta_error_velocity, kMaxVoltage));
            return new double[] {-voltage, voltage};
        } else {
            kP = 100.0;
            kD = 20.0;

            voltage = Math.max(-kMaxVoltage, Math.min(kP * error + kD * error_velocity, kMaxVoltage));
            return new double[] {voltage, voltage};
        }
    }

    public double getError() {
        return error;
    }
}
