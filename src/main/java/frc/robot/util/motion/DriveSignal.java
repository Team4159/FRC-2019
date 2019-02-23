package frc.robot.util.motion;


public class DriveSignal {

    private double left, right;

    DriveSignal() {

        this.left = 0;
        this.right = 0;

    }

    public DriveSignal(double left, double right) {

        this.left = left;
        this.right = right;

    }

    public double getLeft() {

        return left;

    }

    public double getRight() {

        return right;

    }

    void setLeft(double left) {

        this.left = left;

    }

    void setRight(double right) {

        this.right = right;

    }

}