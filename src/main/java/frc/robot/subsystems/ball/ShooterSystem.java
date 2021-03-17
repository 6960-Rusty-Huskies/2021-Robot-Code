package frc.robot.subsystems.ball;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;

import static frc.robot.Constants.CAN.SHOOTER_MOTOR;
import static frc.robot.Constants.PID.SHOOTER_FF;
import static frc.robot.Constants.PID.SHOOTER_P;
import static frc.robot.Constants.Physical.SHOOTER_VELOCITY_15;


public class ShooterSystem extends SubsystemBase {

    private final CANSparkMax shooter;
    private final CANEncoder encoder;
    private final CANPIDController controller;
    private int velocity = 0;
    public static final int MIN_READY_VELOCITY = 10;
    public static final int MAX_READY_VELOCITY = 15;

    public ShooterSystem() {
        shooter = new CANSparkMax(SHOOTER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooter.restoreFactoryDefaults();
        shooter.setIdleMode(CANSparkMax.IdleMode.kCoast);
        encoder = shooter.getEncoder(EncoderType.kHallSensor, 42);
        controller = shooter.getPIDController();
        controller.setP(SHOOTER_P);
        controller.setFF(SHOOTER_FF);
        SmartDashboard.putNumber("Shooter FF Value", SHOOTER_FF);
        shooter.setInverted(true);
        SmartDashboard.putNumber("Shooter RPM Set Value", SHOOTER_VELOCITY_15);
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

    // Called in periodic to keep the shooter at velocity
    public void calculate() {
        controller.setReference(velocity, ControlType.kVelocity);
    }

    public int getVelocity() {
        return (int) encoder.getVelocity();
    }

    public boolean ready() {
        double currentVelocity = encoder.getVelocity();
        return (currentVelocity >= (velocity - MIN_READY_VELOCITY))
                && (currentVelocity < (velocity + MAX_READY_VELOCITY));
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putNumber("Shooter Velocity", (int) encoder.getVelocity());
    }

}
