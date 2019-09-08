package frc.team4159.robot.loops;

public class ElevatorLoop {
    private enum State {
        IDLE,
        ZEROING,
        RUNNING,
        ESTOP
    }

    private double m_goal = 0.0;
    private State m_state = State.IDLE;

    public void setGoal(double goal) {
        m_goal = goal;
    }

    public double update(int encoder, boolean limitTriggered, boolean enabled) {
        switch (m_state) {
            case IDLE:
                break;
            case ZEROING:
                break;
            case RUNNING:
                break;
            case ESTOP:
                break;
        }

        return 0.0;
    }
}
