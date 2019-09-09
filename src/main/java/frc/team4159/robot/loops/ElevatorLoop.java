package frc.team4159.robot.loops;

import frc.team4159.robot.Main;

public class ElevatorLoop {
    private static double kZeroingVelocity = 0.25;
    private static double kMaxZeroingVoltage = 6.0;
    public static double kMaxVoltage = 12.0;

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
    private double last_error = 0.0;

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public double update(double encoder, boolean limitTriggered, boolean enabled) {
        double position = encoder - offset;
        double max_voltage = kMaxVoltage;
        switch (state) {
            case IDLE:
                if (enabled) state = State.ZEROING;
                break;
            case ZEROING:
                filtered_goal -= kZeroingVelocity * Main.dt;
                max_voltage = kMaxZeroingVoltage;
                if (limitTriggered) {
                    state = State.RUNNING;
                    max_voltage = kMaxVoltage;
                    offset = encoder;
                }
                if (!enabled) state = State.IDLE;
                break;
            case RUNNING:
                filtered_goal = goal;
                if (!enabled) state = State.IDLE;
                break;
            case ESTOP:
                break;
        }

        double kP = 50.0;
        double kD = 25.0;

        double error = filtered_goal - position;
        double error_velocity = (error - last_error) / Main.dt;
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
        return last_error;
    }

    public int getState() {
        return state.getValue();
    }
}
