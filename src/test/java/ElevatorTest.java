import frc.team4159.robot.loops.ElevatorLoop;
import org.junit.*;

import java.io.File;
import java.io.FileWriter;


public class ElevatorTest {
    // control loop speed in seconds
    private double dt = 0.01;
    // simulation timeslice
    private double timeslice = 0.001;
    // resistance of the motor in ohms
    private double R = 12.0 / 134.0;
    // motor velocity constant in RPM per volt
    private double K_v = Utils.RPMtoRadiansPerSec(18730.0) / (12 - 0.7 * R);
    // motor torque constant in torque per amp
    private double K_t = 0.71 / 134.0;
    // gear ratio
    private double G = 12.8;
    // gear radius in meters
    private double r = Utils.FeettoMeters(1.751 / 12.0);
    // mass of load in kgs
    private double m = Utils.PoundstoKgs(30.0);

    // position in m
    private double position = 0.0;
    // velocity in m/s
    private double velocity = 0.0;

    private double getAcceleration(double voltage) {
        return (voltage - (G * velocity) / (r * K_v)) * (G * K_t) / (R * r * m);
    }

    private void simulateTime(double voltage, double time) {
        while (time > 0) {
            double acceleration = getAcceleration(voltage);
            velocity += acceleration * timeslice;
            position += velocity * timeslice;
            time -= 0.001;
        }
    }

    @Before
    public void reset() {
        position = 0.0;
        velocity = 0.0;
    }

    @Test
    public void Zeroes() {
        try {
            FileWriter csvWriter = new FileWriter(new File("./src/test/java/test.csv"));
            ElevatorLoop elevator = new ElevatorLoop();

            position = 2.0;

            for (int i = 0; i < 400; i++) {
                simulateTime(elevator.update(position, position < 0.01, true), dt);

                //col 0 is time, col 1 is position, col 2 is goal, col 3 is error, col 4 is voltage
                csvWriter.append(Integer.toString(i)+ "," + Double.toString(position) + "," + Double.toString(elevator.getGoal()) + "," + Double.toString(elevator.getError()) + "," + Double.toString(elevator.getVoltage()));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
            Assert.assertTrue("Position is " + position + ", which is " + (position - 0.0) + "away from " + 0.0, Math.abs(position-0.0) < 0.1);

        } catch (Exception e) {
            System.out.println(e);
        }


    }
}
