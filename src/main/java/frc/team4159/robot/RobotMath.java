package frc.team4159.robot;

public class RobotMath {
    public static double RPMtoRadiansPerSec(double RPM) {
        return RPM * 2 * Math.PI / 60;
    }
    public static double FeettoMeters(double feet) {
        return feet / 3.281;
    }
    public static double PoundstoKgs(double pounds) {
        return pounds / 2.205;
    }
    public static double MeterstoTicks(double meters, double radius, int ticksPerRev) {
        return meters / (radius * 2 * Math.PI) * ticksPerRev;
    }
}
