package frc.robot.util.motion;


class Velocity {

    private double linear;
    private double angular;

    Velocity(double linear, double angular) {

        this.linear = linear;
        this.angular = angular;

    }

    double getLinear() {

        return linear;

    }

    double getAngular() {

        return angular;

    }

}