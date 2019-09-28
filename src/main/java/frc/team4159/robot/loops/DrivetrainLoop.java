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

    private double goal = 0.0;

    private State state = State.IDLE;
    private double error = 0.0;
    private double error_velocity = 0.0;
    private double last_error = 0.0;

    public void setGoal(double goal) {
        this.goal = goal;
        last_error = 0.0;
    }

    public double update(double encoder, boolean enabled) {
        switch (state) {
            case IDLE:
                if (enabled) state = State.RUNNING;
                break;
            case RUNNING:
                break;
            case ESTOP:
                break;
        }

        double kP = 100.0;
        double kD = 20.0;

        error = goal - encoder;
        error_velocity = (error - last_error) / Main.dt;
        last_error = error;

        return Math.max(-kMaxVoltage, Math.min(kP * error + kD * error_velocity, kMaxVoltage));
    }

    public double getError() {
        return error;
    }
}
