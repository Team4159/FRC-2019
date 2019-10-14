package frc.team4159.robot;

public class RobotMath {
    public static double FeetToMeters(double feet) {
        return feet / 3.281;
    }
    public static double MetersToTicks(double meters, double radius, int ticksPerRev) {
        return meters / (radius * 2 * Math.PI) * ticksPerRev;
    }
}
