import frc.team4159.robot.Main;
import frc.team4159.robot.Utils;
import frc.team4159.robot.loops.DrivetrainLoop;
import org.junit.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Drivetrain uses RevRobotic's NEO Brushless Motors.
 *
 * Datasheet: http://www.revrobotics.com/content/docs/REV-21-1650-DS.pdf
 */


public class DrivetrainTest {
    // number of motors
    private final int kMotors = 4;
    // resistance of the motor in ohms
    private final double kResistance = 12.0 / 105.0;
    // motor velocity constant in RPM per volt
    private final double Kv = Utils.RPMtoRadiansPerSec(5676.0) / (12.0 - 1.8 * kResistance);
    // motor torque constant in torque per amp
    private final double Kt = 2.6 / 105.0;
    // gear ratio
    private final double kG = 7.36;
    // sprocket radius in meters
    private final double kr = Utils.FeettoMeters(1.432 / 12.0);
    // mass of load in kgs
    private final double kMass = Utils.PoundstoKgs(121.4);
    // radius of wheels in meters
    private final double kWheelRadius = Utils.FeettoMeters(3.0 / 12.0);

    // position in m
    private double position = 0.0;
    // starting position in m
    private double starting_position = 0.0;
    // velocity in m/s
    private double angular_velocity = 0.0;

    private DrivetrainLoop drivetrain_loop = new DrivetrainLoop();

    private double getAngularAcceleration(double voltage) {
        return (voltage - (kG * angular_velocity) / Kv) * (kG * Kt) / (kResistance * kMass * kr * kr);
    }

    private void simulateTime(double voltage, double time) {
        double kSimTime = 0.0001;
        while (time > 0) {
            double angular_acceleration = getAngularAcceleration(voltage) * kMotors;
            angular_velocity += angular_acceleration * kSimTime;
            position += (angular_velocity * kSimTime * kWheelRadius);
            time -= kSimTime;
        }
    }

    private void simulateLoop(double time) {
        try {
            FileWriter csvWriter = null;
            csvWriter = new FileWriter(new File("dump.csv"));

            while (time > 0) {
                double voltage = drivetrain_loop.update(position, true);

                simulateTime(voltage, Main.dt);
                time -= Main.dt;


                csvWriter.append(position + "," + voltage + "," + angular_velocity);
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
        position = 0.0;
        angular_velocity = 0.0;
        drivetrain_loop = new DrivetrainLoop();
    }

    @Test
    public void GoForwardOneMeter() {
        drivetrain_loop.setGoal(5.0);
        simulateLoop(7.0);

        Assert.assertEquals(5.0, position, 0.1);
    }
}
