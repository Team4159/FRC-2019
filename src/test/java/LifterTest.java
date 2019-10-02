import org.junit.*;

import frc.team4159.robot.Main;
import frc.team4159.robot.loops.LifterLoopGood;

public class LifterTest {
    // lifters's control loop
    private LifterLoopGood lifter_loop = new LifterLoopGood();

    // weird "position" in volts / time
    private double position = 0.0;

    private boolean topLimitSwitch() {
        return position >= LifterLoopGood.kMaxVoltage / Main.dt;
    }

    private boolean bottomLimitSwitch() {
        return position <= 0.0;
    }

    private void simulateLoop(double time) {
        while (time > 0) {
            position += lifter_loop.update(topLimitSwitch(), bottomLimitSwitch(), true);
            time -= Main.dt;
        }
    }

    @Before
    public void reset() {
        lifter_loop = new LifterLoopGood();
        position = 0.0;
    }

    @Test
    public void Zeroes() {
        position = 500.0;
        simulateLoop(2.0);

        Assert.assertEquals(0.0, position, 0.0);
    }

    @Test
    public void GoesToTop() {
        position = 250.0;
        lifter_loop.setGoal(LifterLoopGood.Position.UP);
        simulateLoop(4.0);

        Assert.assertEquals(LifterLoopGood.kMaxVoltage / Main.dt, position, 0.0);
    }
}
