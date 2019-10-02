package frc.team4159.robot.loops;

public class LifterLoopGood {
    // max lifter voltage in volts
    public static final double kMaxVoltage = 10.0;
    // max lifter voltage when zeroing in volts
    public static final double kMaxZeroingVoltage = 5.0;

    public enum Position {
        UP,
        DOWN
    }

    private enum State {
        IDLE,
        ZEROING,
        RUNNING,
        ESTOP
    }

    private Position goal = Position.DOWN;
    private Position position = Position.DOWN;
    private State state = State.IDLE;

    public void setGoal(Position goal) {
        this.goal = goal;
    }

    public double update(boolean top_limit_triggered, boolean bottom_limit_triggered, boolean enabled) {
        double voltage = 0.0;

        switch (state) {
            case IDLE:
                if (enabled) state = State.ZEROING;
                break;
            case ZEROING:
                if (bottom_limit_triggered) {
                    state = State.RUNNING;
                } else {
                    voltage = -kMaxZeroingVoltage;
                }
                if (!enabled) state = State.IDLE;
                break;
            case RUNNING:
                if (top_limit_triggered) {
                    position = Position.UP;
                } else if (bottom_limit_triggered) {
                    position = Position.DOWN;
                }

                if (goal != position) {
                    if (goal == Position.DOWN) {
                        voltage = -kMaxVoltage;
                    } else if (goal == Position.UP) {
                        voltage = kMaxVoltage;
                    }
                }
                if (!enabled) state = State.IDLE;
                break;
            case ESTOP:
                break;
        }

        return voltage;
    }

    public State getState() {
        return state;
    }
}
