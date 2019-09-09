package frc.team4159.robot.loops;

import frc.team4159.robot.Main;

public class ElevatorLoop {
    private static final double kZeroingVelocity = 0.25;
    private static final double kMaxZeroingVoltage = 6.0;
    public static final double kMaxVoltage = 12.0;

    private enum State {
        IDLE(0),
        ZEROING(1),
        RUNNING(2),
        ESTOP(3);

        int value;
        State(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    private double goal = 0.0;
    private double filtered_goal = 0.0;
    private double offset = 0.0;

    private State state = State.IDLE;
    private double error = 0.0;
    private double error_velocity = 0.0;
    private double last_error = 0.0;

    public void setGoal(double goal) {
        this.goal = goal;
        last_error = 0.0;
    }

    public double update(double encoder, boolean limitTriggered, boolean enabled) {
        double position = encoder - offset;
        double max_voltage = kMaxVoltage;
        switch (state) {
            case IDLE:
                if (enabled) state = State.ZEROING;
                filtered_goal = 0.0;
                break;
            case ZEROING:
                filtered_goal -= kZeroingVelocity * Main.dt;
                max_voltage = kMaxZeroingVoltage;
                if (limitTriggered) {
                    state = State.RUNNING;
                    max_voltage = kMaxVoltage;
                    offset = encoder;
                    position = 0.0;
                    filtered_goal = goal;
                    last_error = 0.0;
                }
                if (!enabled) state = State.IDLE;
                break;
            case RUNNING:
               // if (!enabled) state = State.IDLE;
                filtered_goal = goal;
                break;
            case ESTOP:
                break;
        }

        double kP = 100.0;
        double kD = 25.0;

        error = filtered_goal - position;
        error_velocity = (error - last_error) / Main.dt;
        last_error = error;

        return Math.max(-max_voltage, Math.min(kP * error + kD * error_velocity, max_voltage));
    }

    // TODO: remove these methods and convert variables to local variables
    public double getGoal() {
        return goal;
    }

    public double getFilteredGoal() {
        return filtered_goal;
    }

    public double getError() {
        return error;
    }

    public double getErrorVelocity() {
        return error_velocity;
    }

    public int getState() {
        return state.getValue();
    }
}
