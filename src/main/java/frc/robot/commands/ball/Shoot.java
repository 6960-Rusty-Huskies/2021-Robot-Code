package frc.robot.commands.ball;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.ball.*;

import static frc.robot.Constants.PID.SHOOTER_FF;
import static frc.robot.Constants.PID.SHOOTER_P;
import static frc.robot.Constants.Physical.SHOOTER_VELOCITY_15;
import static frc.robot.subsystems.ball.ShooterSystem.MIN_READY_VELOCITY;


public class Shoot extends CommandBase {

    protected final ShooterSystem shooterSystem;
    protected final IndexerSystem upperIndexerSystem;
    protected final IndexerSystem lowerIndexerSystem;

    private boolean initialSetpointHit;
    private int setpointHitCount = 0;
    private int initialSetpoint = 0;
    protected int ballCount = 0;

    protected boolean lastBeamBreakStatus;

    public Shoot(ShooterSystem shooterSystem, IndexerSystem upperIndexerSystem, IndexerSystem lowerIndexerSystem) {
        addRequirements(shooterSystem, upperIndexerSystem, lowerIndexerSystem);

        this.shooterSystem = shooterSystem;
        this.upperIndexerSystem = upperIndexerSystem;
        this.lowerIndexerSystem = lowerIndexerSystem;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // set the intake arm going down, will auto switch off with limit switch
        shooterSystem.setShooterFF(SmartDashboard.getNumber("Shooter FF Value", SHOOTER_FF));
        shooterSystem.setShooterP(SmartDashboard.getNumber("Shooter P Value", SHOOTER_P));
        setpointHitCount = 0;
        initialSetpoint = (int) SmartDashboard.getNumber("Shooter RPM Set Value", SHOOTER_VELOCITY_15);
        shooterSystem.setVelocity(initialSetpoint);
        ballCount = (int) SmartDashboard.getNumber("Power Cell Count", 0);
        lastBeamBreakStatus = upperIndexerSystem.isBeamBreakTriggered();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (setpointHitCount < 10) {
            if (shooterSystem.ready()) {
                setpointHitCount++;
            } else {
                double ffValue = SmartDashboard.getNumber("Shooter FF Value", SHOOTER_FF);
                if (shooterSystem.getVelocity() < (initialSetpoint - MIN_READY_VELOCITY)) {
                    ffValue += .00000005;
                } else {
                    ffValue -= .00000005;
                }
                shooterSystem.setShooterFF(ffValue);
                SmartDashboard.putNumber("Shooter FF Value", ffValue);
            }
        } else {
            boolean upperBeamBreakTriggered = upperIndexerSystem.isBeamBreakTriggered();
            if (lastBeamBreakStatus && !upperBeamBreakTriggered) {
                ballCount--;
                SmartDashboard.putNumber("Power Cell Count", ballCount);
            }
            lastBeamBreakStatus = upperBeamBreakTriggered;

            if (shooterSystem.ready()) {
                upperIndexerSystem.drive(.5);
                lowerIndexerSystem.drive(.5);
            } else {
                upperIndexerSystem.drive(0);
                lowerIndexerSystem.drive(0);
            }
        }

        shooterSystem.calculate();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        upperIndexerSystem.drive(0);
        lowerIndexerSystem.drive(0);
        shooterSystem.stopShooter();
        SmartDashboard.putNumber("Power Cell Count", ballCount);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return ballCount <= 0;
    }

}
