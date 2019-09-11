package frc.team4159.robot.loops;

import frc.team4159.robot.subsystems.Drivetrain;

public class DrivetrainLoop {
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

    State state;

    private DrivetrainLoop() {
        state = State.IDLE;
    }

    public double update(double encoder, boolean limitTriggered, boolean enabled) {
        switch (state) {
            case IDLE:
                if (enabled) state = State.RUNNING;
                break;
            case RUNNING:
                break;
            case ESTOP:
                break;
        }

        return 0.0;
    }

}
