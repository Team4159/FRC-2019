import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team4159.robot.CollisionAvoidance;
import frc.team4159.robot.Main;
import frc.team4159.robot.loops.ElevatorLoop;
import org.junit.*;

public class CollisionAvoidanceTest {
    private CollisionAvoidance collision_avoidance = new CollisionAvoidance();

    @Before
    public void reset() {
        collision_avoidance = new CollisionAvoidance();
    }

    @Test
    public void SafeMovesElevator() {
        ElevatorTest elevator_test = new ElevatorTest();
        boolean raiser_state = true;
        boolean raiser_goal = true;
        elevator_test.elevator_loop.setGoal(ElevatorLoop.kMaxHeight);

        for (int i = 0; i < 300; i++) {
            double voltage = elevator_test.elevator_loop.update(
                    elevator_test.encoder(),
                    elevator_test.limitSwitch(),
                    true
            );
            elevator_test.simulateTime(voltage, Main.dt);
            raiser_state = collision_avoidance.getRaiserSafeState(
                    raiser_goal,
                    raiser_state,
                    (int) elevator_test.encoder()
            );
            Assert.assertFalse(elevator_test.encoder() > CollisionAvoidance.kRaiserMinDangerousHeight && raiser_state && elevator_test.encoder() < CollisionAvoidance.kRaiserMaxDangerousHeight);
        }
    }
}
