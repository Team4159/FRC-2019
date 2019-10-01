package frc.team4159.robot.loops;

import frc.team4159.robot.Main;

// temporary until we put limits on the lifter
public class LifterLoopBad {
    // max lifter voltage in volts
    public static final double kMaxVoltage = 7.0;
    // lifter time in seconds
    private static final double kLifterTime = 0.8;

    public enum Position {
        UP,
        DOWN
    }

    private enum State {
        IDLE,
        RUNNING
    }

    private double timesteps_since_start_move = 0.0;
    private Position goal = null;
    private Position position = Position.DOWN;
    private State state = State.IDLE;

    public void setGoal(Position goal) {
        if (goal != position) {
            this.goal = goal;
            timesteps_since_start_move = 0.0;
        }
    }

    public double update(boolean enabled) {
        Position filtered_goal = null;
        double voltage = 0.0;

        switch (state) {
            case IDLE:
                if (enabled) state = State.RUNNING;
                break;
            case RUNNING:
                filtered_goal = goal;
                if (!enabled) state = State.IDLE;
                break;
        }

        if (filtered_goal != null) {
            timesteps_since_start_move++;

            if (timesteps_since_start_move >= kLifterTime / Main.dt) {
                goal = null;
                timesteps_since_start_move = 0.0;
                switch (position) {
                    case UP:
                        position = Position.DOWN;
                        break;
                    case DOWN:
                        position = Position.UP;
                        break;
                }
            }

            if (filtered_goal == Position.UP) {
                voltage = kMaxVoltage;
            } else if (filtered_goal == Position.DOWN) {
                voltage = -kMaxVoltage;
            }
        }

        return voltage;
    }
}
