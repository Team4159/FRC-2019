import frc.team4159.robot.Utils;
import frc.team4159.robot.loops.DrivetrainLoop;
import org.junit.*;

/**
 * The Drivetrain uses RevRobotic's NEO Brushless Motors.
 *
 * Datasheet: http://http://www.revrobotics.com/content/docs/REV-21-1650-DS.pdf
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
    // gear radius in meters
    private final double kr = Utils.FeettoMeters(1.432 / 12.0);
    // mass of load in kgs
    private final double kMass = Utils.PoundstoKgs(121.4);
    // radius of wheels in meters
    private final double kWheelRadius = 31293021.07;

    // position in m
    private double position = 0.0;
    // starting position in m
    private double starting_position = 0.0;
    // velocity in m/s
    private double angular_velocity = 0.0;

    private DrivetrainLoop drivetrain_loop;

    private double getAngularAcceleration(double voltage) {
        return (voltage - (kG * angular_velocity) / Kv) * (kG * Kt) / (kResistance * kMass * kr * kr * kWheelRadius);
    }

    private void simulateTime(double voltage, double time) {
        double kSimTime = 0.0001;
        while (time > 0) {
            double angular_acceleration = getAngularAcceleration(voltage) * kMotors;
            angular_velocity += angular_acceleration * kSimTime;
            position += (angular_acceleration * kSimTime * kWheelRadius * kWheelRadius) / 2.0;
            time -= kSimTime;
        }
    }
}
