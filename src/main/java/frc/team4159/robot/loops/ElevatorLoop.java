package frc.team4159.robot.loops;

public class ElevatorLoop {
    private enum State {
        IDLE,
        ZEROING,
        RUNNING,
        ESTOP
    }

    private double m_goal = 0.0;
    private double filtered_goal = 0.0;

    private State m_state = State.IDLE;
    private double lastError = 0.0;

    double voltage = 0.0;

    public void setGoal(double goal) {
        m_goal = goal;
    }

    public double update(double encoder, boolean limitTriggered, boolean enabled) {
        switch (m_state) {
            case IDLE:
                if (enabled) m_state = State.ZEROING;
                break;
            case ZEROING:
                filtered_goal -= 0.05;
                if (limitTriggered) {
                    m_state = State.RUNNING;

                    //TODO: offset
                    filtered_goal = m_goal;
                }
                if (!enabled) m_state = State.IDLE;
                break;
            case RUNNING:
                break;
            case ESTOP:
                break;
        }

        double kP = 10.0;
        double kD = 0.0;

        double error = filtered_goal - encoder;

        voltage = Math.max(-6, Math.min((kP * error + kD * (error-lastError)), 6));

        lastError = error;

        return voltage;
    }

    //TODO: remove these methods and convert variables to local variables
    public double getGoal() {
        return m_goal;
    }

    public double getError() {
        return lastError;
    }

    public double getVoltage() {
        return voltage;
    }
}
