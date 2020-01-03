package frc.team4159.robot;

public class Utils {
    public static int metersToTicks(double meters, double radius, int ticksPerRev) {
        return (int) (meters / (radius * 2 * Math.PI) * ticksPerRev);
    }
}
