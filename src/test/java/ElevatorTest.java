import org.junit.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import frc.team4159.robot.Main;
import frc.team4159.robot.loops.ElevatorLoop;

public class ElevatorTest {
    // number of motors
    private double kMotors = 2.0;
    // resistance of the motor in ohms
    private double kResistance = 12.0 / 134.0;
    // motor velocity constant in RPM per volt
    private double Kv = Utils.RPMtoRadiansPerSec(18730.0) / (12.0 - 0.7 * kResistance);
    // motor torque constant in torque per amp
    private double Kt = 0.71 / 134.0;
    // gear ratio
    private double kG = 12.8;
    // gear radius in meters
    private double kr = Utils.FeettoMeters(1.751 / 12.0);
    // mass of load in kgs
    private double kMass = Utils.PoundstoKgs(30.0);
    // test starting position in meters
    private double kTestPosition = 0.2;
    // position in m
    private double position = 0.0;
    // starting position in m
    private double starting_position = 0.0;
    // velocity in m/s
    private double velocity = 0.0;
    // goal in meters
    private double goal = 0.4;
    // max height in meters
    private double kMaxheight = Utils.FeettoMeters(62.0 / 12);
    // min height in meters
    private double kMinHeight = 0.0;
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
    public void Zeroes() {
        try {
            FileWriter csvWriter = new FileWriter(new File("./build/tmp/dump.csv"));

            setStartingPosition(kTestPosition);

            for (int i = 0; i < 400; i++) {
                double voltage = elevator_loop.update(encoder(), limitSwitch(), true);

                simulateTime(voltage, Main.dt);

                csvWriter.append(position + "," +
                                 elevator_loop.getGoal() + "," +
                                 elevator_loop.getFilteredGoal() + "," +
                                 elevator_loop.getError() + "," +
                                 voltage + "," +
                                 elevator_loop.getState() + "," +
                                 elevator_loop.getErrorVelocity());
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();

            Assert.assertEquals("Was :" + position + ", Expected: " + 0.0, 0.0, position, 0.01);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    @Test
    public void outOfBounds(){
        Assert.assertTrue(goal < kMaxheight && goal > kMinHeight);
    }
    @Test
    public void MoveToPosition() {
            elevator_loop.setGoal(goal);
            for (int i = 0; i < 400; i++) {
                double voltage = elevator_loop.update(encoder(), limitSwitch(), true);
                simulateTime(voltage, Main.dt);
            }
            Assert.assertEquals("Was:" + position + ", Expected: " + goal, goal, position, 0.1);
    }
}
