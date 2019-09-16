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

    private double goal = 0.0;
    private double filtered_goal = 0.0;
    private double offset = 0.0;

    private State state = State.IDLE;
    private double error = 0.0;
    private double error_velocity = 0.0;
    private double last_error = 0.0;

    public DrivetrainLoop() {
        state = State.IDLE;
    }

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

        return 12.0;
    }

}
