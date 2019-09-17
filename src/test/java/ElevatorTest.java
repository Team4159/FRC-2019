import org.junit.*;
import org.junit.rules.TestName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import frc.team4159.robot.Main;
import frc.team4159.robot.Utils;
import frc.team4159.robot.loops.ElevatorLoop;

/**
 * The Drivetrain uses RevRobotics' 775PRO Motors.
 *
 * Datasheet: https://motors.vex.com/vexpro-motors/775pro
 */

public class ElevatorTest {
    @Rule public TestName name = new TestName();
    // number of motors
    private final double kMotors = 2.0;
    // resistance of the motor in ohms
    private final double kResistance = 12.0 / 134.0;
    // motor velocity constant in RPM per volt
    private final double Kv = Utils.RPMtoRadiansPerSec(18730.0) / (12.0 - 0.7 * kResistance);
    // motor torque constant in torque per amp
    private final double Kt = 0.71 / 134.0;
    // gear ratio
    private final double kG = 12.8;
    // gear radius in meters
    private final double kr = Utils.FeettoMeters(1.751 / 12.0);
    // mass of load in kgs
    private final double kMass = Utils.PoundstoKgs(30.0);

    // position in m
    private double position = 0.0;
    // starting position in m
    private double starting_position = 0.0;
    // velocity in m/s
    private double velocity = 0.0;

    // elevator's control loop
    private ElevatorLoop elevator_loop = new ElevatorLoop();

    private double getAcceleration(double voltage) {
        return (voltage - (kG * velocity) / (kr * Kv)) * (kG * Kt) / (kResistance * kr * kMass);
    }

    private void simulateTime(double voltage, double time) {
        Assert.assertTrue(voltage <= ElevatorLoop.kMaxVoltage);
        Assert.assertTrue(voltage >= -ElevatorLoop.kMaxVoltage);
        // simulation timeslice in seconds
        double kSimTime = 0.0001;
        while (time > 0) {
            double acceleration = getAcceleration(voltage) * kMotors;
            velocity += acceleration * kSimTime;
            position += velocity * kSimTime;
            time -= kSimTime;
        }
    }

    private void simulateLoop(double time) {
        try {
            File file = new File(System.getProperty("java.io.tmpdir"), name.getMethodName().concat(".csv"));
            System.out.println("Dump: " + file.getPath());
            FileWriter csvWriter = new FileWriter(file);

            while (time > 0) {
                double voltage = elevator_loop.update(encoder(), limitSwitch(), true);

                simulateTime(voltage, Main.dt);
                time -= Main.dt;

                csvWriter.append(position + "," + // 1
                        elevator_loop.getGoal() + "," + // 2
                        elevator_loop.getFilteredGoal() + "," + // 3
                        elevator_loop.getError() + "," + // 4
                        voltage + "," +  // 5
                        elevator_loop.getState() + "," + // 6
                        elevator_loop.getErrorVelocity()); // 7
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private boolean limitSwitch() {
        return position <= 0.0;
    }

    private void setStartingPosition(double starting_position) {
        this.starting_position = position =  starting_position;
    }

    private double encoder() {
        return position - starting_position;
    }

    @Before
    public void reset() {
        position = 0.0;
        velocity = 0.0;
        elevator_loop = new ElevatorLoop();
    }

    @Test
    public void Zeros() {
        setStartingPosition(0.2);
        simulateLoop(4.0);

        Assert.assertEquals("Was :" + position + ", Expected: " + 0.0, 0.0, position, 0.01);
    }

    @Test
    public void TooHigh() {
        double goal = ElevatorLoop.kMaxHeight + 0.1;
        // sets an impossibly high goal
        elevator_loop.setGoal(goal);
        simulateLoop(4.0);
        Assert.assertTrue(position < ElevatorLoop.kMaxHeight);
    }

    @Test
    public void TooLow() {
        double goal = ElevatorLoop.kMinHeight - 0.1;
        // sets an impossibly low goal
        elevator_loop.setGoal(goal);
        simulateLoop(4.0);
        Assert.assertTrue(position > ElevatorLoop.kMinHeight);
    }

    @Test
    public void MovesToPosition() {
        double goal = 0.4;
        setStartingPosition(0.2);
        elevator_loop.setGoal(goal);
        simulateLoop(4.0);
        Assert.assertEquals("Was:" + position + ", Expected: " + goal, goal, position, 0.1);
    }
}
