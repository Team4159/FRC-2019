import org.junit.*;
import org.junit.rules.TestName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import frc.team4159.robot.Main;
import frc.team4159.robot.Utils;
import frc.team4159.robot.loops.DrivetrainLoop;

/**
 * The Drivetrain uses RevRobotics' NEO Brushless Motors.
 *
 * Datasheet: https://motors.vex.com/vexpro-motors/775pro
 */


public class DrivetrainTest {
    @Rule public TestName name = new TestName();
    // number of motors per side
    private final double kMotors = 2.0;
    // resistance of the motor in ohms
    private final double kResistance = 12.0 / 105.0;
    // motor velocity constant in RPM per volt
    private final double Kv = Utils.RPMtoRadiansPerSec(5676.0) / (12.0 - 1.8 * kResistance);
    // motor torque constant in torque per amp
    private final double Kt = 2.6 / 105.0;
    // gear ratio
    private final double kG = 7.36;
    // wheel radius in meters
    private final double kr = Utils.FeettoMeters(3.0 / 12.0);
    // mass of load in kgs
    private final double kMass = Utils.PoundstoKgs(121.4);
    // width of robot in meters
    private final double width = Utils.FeettoMeters(27.0 / 12.0);

    // position in m
    private double displacement = 0.0;
    private double x_position = 0.0;
    private double y_position = 0.0;
    // starting position in m
    private double starting_position = 0.0;
    // left side of drivetrain velocity in m/s
    private double left_velocity = 0.0;
    // right side of drivetrain velocity in m/s;
    private double right_velocity = 0.0;
    // angular velocity of the drivetrain
    private double angular_velocity = 0.0;
    // angle of drivetrain
    private double angle = 0.0;

    private DrivetrainLoop drivetrain_loop = new DrivetrainLoop();

    private double getAcceleration(double voltage, double velocity) {
        return (voltage - (kG * velocity) / (kr * Kv)) * (kG * Kt) / (kResistance * kMass * kr);
    }

    private double getLeftAcceleration(double voltage) {
        return getAcceleration(voltage, left_velocity);
    }

    private double getRightAcceleration(double voltage) {
        return getAcceleration(voltage, right_velocity);
    }

    private void simulateTime(double left_voltage, double right_voltage, double time) {
        double kSimTime = 0.0001;
        while (time > 0) {
            double left_acceleration = getLeftAcceleration(left_voltage) * kMotors;
            double right_acceleration = getRightAcceleration(right_voltage) * kMotors;

            double angular_acceleration = (right_acceleration - left_acceleration) / (width * 2.0);
            angular_velocity += angular_acceleration * kSimTime;
            angle += angular_velocity * kSimTime;

            left_velocity += left_acceleration * kSimTime;
            right_velocity += right_acceleration * kSimTime;

            x_position += ((left_velocity + right_velocity) / 2.0) * (Math.cos(angle) * kSimTime);
            y_position += ((left_velocity + right_velocity) / 2.0) * (Math.sin(angle) * kSimTime);

            displacement = Math.sqrt(x_position*x_position + y_position*y_position);

            time -= kSimTime;
        }
    }

    private void simulateLoop(double time) {
        try {
            File file = File.createTempFile(name.getMethodName(), ".csv");
            System.out.println("Dump: " + file.getPath());
            FileWriter csvWriter = new FileWriter(file);

            while (time > 0) {
                double[] voltage = drivetrain_loop.update(displacement, angle, true);

                simulateTime(voltage[0], voltage[1], Main.dt);
                time -= Main.dt;

                csvWriter.append(x_position + "," +
                        voltage[0] + "," +
                        voltage[1] + "," +
                        drivetrain_loop.getError());
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    @Before
    public void reset() {
        x_position = 0.0;
        y_position = 0.0;

        left_velocity = 0.0;
        right_velocity = 0.0;

        angle = 0.0;
        angular_velocity = 0.0;

        drivetrain_loop = new DrivetrainLoop();
    }

    @Test
    public void GoForwardFiveMeters() {
        drivetrain_loop.setForwardGoal(5.0);
        simulateLoop(7.0);

        Assert.assertEquals(5.0, x_position, 0.01);
    }

    @Test
    public void RightAngleTurn() {
        drivetrain_loop.setThetaGoal(Math.PI / 2.0);
        simulateLoop(7.0);

        Assert.assertEquals(Math.PI / 2.0, angle, 0.1);
        Assert.assertEquals(0.0, displacement, 0.0);
    }

    @Test
    public void GoDiagonallyFiveMeters() {
        drivetrain_loop.setThetaGoal(Math.atan(4.0/3.0));
        drivetrain_loop.setForwardGoal(5.0);
        simulateLoop(7.0);

        Assert.assertEquals(5.0, displacement, 0.2);
        Assert.assertEquals(3.0, x_position, 0.2);
        Assert.assertEquals(4.0, y_position, 0.2);
    }
}
