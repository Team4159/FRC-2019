package frc.robot.util;


public class RobotMath {

    public static double feetToTicks(double feet) {

        return (feet * Constants.getInt("TICKS_PER_REV")) / (Math.PI * Constants.getDouble("WHEEL_DIAMETER"));

    }

    public static double ticksToFeet(double ticks) {

        return (ticks / Constants.getInt("TICKS_PER_REV")) * (Math.PI * Constants.getDouble("WHEEL_DIAMETER"));

    }

}