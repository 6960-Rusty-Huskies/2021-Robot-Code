package frc.robot.subsystems.ball;

public class ShootingConfig {

    private int speed;
    private double ff;

    public ShootingConfig(int speed, double ff) {
        this.speed = speed;
        this.ff = ff;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getFf() {
        return ff;
    }

    public void setFf(double ff) {
        this.ff = ff;
    }
}
