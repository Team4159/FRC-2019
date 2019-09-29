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

    private Position goal = null;
    private Position position = null;
    private State state = State.IDLE;

    public void setGoal(Position goal) {
        if (goal != position) {
            this.goal = goal;
        }
    }

    public double update(boolean top_limit_triggered, boolean bottom_limit_triggered, boolean enabled) {
        double voltage = 0.0;

        switch (state) {
            case IDLE:
                if (enabled) state = State.ZEROING;
                break;
            case ZEROING:
                voltage = -kMaxZeroingVoltage;
                if (bottom_limit_triggered) state = State.RUNNING;
                if (!enabled) state = State.IDLE;
                break;
            case RUNNING:
                if (top_limit_triggered) {
                    position = Position.UP;
                    goal = null;

                } else if (bottom_limit_triggered) {
                    position = Position.DOWN;
                    goal = null;
                }

                if (goal == Position.DOWN) {
                    voltage = -kMaxVoltage;
                } else if (goal == Position.UP) {
                    voltage = kMaxVoltage;
                }
                if (!enabled) state = State.IDLE;
                break;
            case ESTOP:
                break;
        }

        return voltage;
    }

    public Position getPosition() {
        return position;
    }

    public int getState() {
        return state.getValue();
    }
}
