package frc.robot.subsystems.ball;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;

import java.util.*;

import static frc.robot.Constants.CAN.SHOOTER_MOTOR;
import static frc.robot.Constants.PID.SHOOTER_FF;
import static frc.robot.Constants.PID.SHOOTER_P;


public class ShooterSystem extends SubsystemBase {

    private final CANSparkMax shooter;
    private final CANEncoder encoder;
    private final CANPIDController controller;
    private int velocity = 0;
    private Map<Double, ShootingConfig> shooterValues = new HashMap<>();

    public ShooterSystem() {
        shooter = new CANSparkMax(SHOOTER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooter.restoreFactoryDefaults();
        shooter.setIdleMode(CANSparkMax.IdleMode.kCoast);
        encoder = shooter.getEncoder(EncoderType.kHallSensor, 42);
        controller = shooter.getPIDController();
        controller.setP(SHOOTER_P);
        controller.setFF(SHOOTER_FF);
        SmartDashboard.putNumber("Shooter FF Value", 0);
        shooter.setInverted(true);
        SmartDashboard.putNumber("Shooter RPM Set Value", 36500);
        shooterValues.put(7.5d, new ShootingConfig(2800, .000207));
    }

    // Velocity in RPM
    public void setVelocity(int velocity) {
        this.velocity = velocity;
        SmartDashboard.putNumber("Shooter RPM Set Value", velocity);
    }

    public void setShooterFF(double ff) {
        controller.setFF(ff);
        SmartDashboard.putNumber("Shooter FF Value", ff);
    }

    // Used to naturally slow the motor rather than drive it to zero with controller
    public void stopShooter() {
        velocity = 0;
        shooter.stopMotor();
    }

    public ShootingConfig getShootingConfig(double distance) {
        return shooterValues.getOrDefault(distance, null);
    }

    // Called in periodic to keep the shooter at velocity
    public void calculate() {
        controller.setReference(velocity, ControlType.kVelocity);
    }

    public boolean ready() {
        double currentVelocity = encoder.getVelocity();
        return (currentVelocity >= (velocity - 10)) && (currentVelocity < (velocity + 50));
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putNumber("Shooter Velocity", (int) encoder.getVelocity());
    }

}
