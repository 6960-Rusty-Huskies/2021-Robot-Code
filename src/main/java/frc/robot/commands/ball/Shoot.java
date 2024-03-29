package frc.robot.commands.ball;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.ball.*;

import static frc.robot.Constants.PID.SHOOTER_FF;
import static frc.robot.Constants.PID.SHOOTER_P;
import static frc.robot.Constants.Physical.SHOOTER_VELOCITY_15;


public class Shoot extends CommandBase {

    protected final ShooterSystem shooterSystem;
    protected final IndexerSystem upperIndexerSystem;
    protected final IndexerSystem lowerIndexerSystem;

    private int readyStateCount = 0;
    protected int ballCount = 0;

    private int shootVelocity;

    protected boolean lastBeamBreakStatus;

    public Shoot(ShooterSystem shooterSystem, IndexerSystem upperIndexerSystem, IndexerSystem lowerIndexerSystem) {
        addRequirements(shooterSystem, upperIndexerSystem, lowerIndexerSystem);

        this.shooterSystem = shooterSystem;
        this.upperIndexerSystem = upperIndexerSystem;
        this.lowerIndexerSystem = lowerIndexerSystem;
    }

    public Shoot(ShooterSystem shooterSystem, IndexerSystem upperIndexerSystem, IndexerSystem lowerIndexerSystem, int velocity) {
        addRequirements(shooterSystem, upperIndexerSystem, lowerIndexerSystem);

        shootVelocity = velocity;

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
        readyStateCount = 0;
        if (shootVelocity != 0) {
            shooterSystem.setVelocity(shootVelocity);
            SmartDashboard.putNumber("Shooter RPM Set Value", shootVelocity);
        } else {
            shooterSystem.setVelocity((int) SmartDashboard.getNumber("Shooter RPM Set Value", SHOOTER_VELOCITY_15));
        }
        ballCount = (int) SmartDashboard.getNumber("Power Cell Count", 0);
        lastBeamBreakStatus = upperIndexerSystem.isBeamBreakTriggered();
        shooterSystem.calculate();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // Once we hit ready state 20 times in a row... we will not go back into here until a ball is shot
        if (readyStateCount < 20) {
            if (shooterSystem.ready()) {
                readyStateCount++;
            } else {
                readyStateCount = 0;
            }
        } else {
            boolean upperBeamBreakTriggered = upperIndexerSystem.isBeamBreakTriggered();
            if (lastBeamBreakStatus && !upperBeamBreakTriggered) {
                ballCount--;
                SmartDashboard.putNumber("Power Cell Count", ballCount);
                upperIndexerSystem.drive(0);
                lowerIndexerSystem.drive(0);
                readyStateCount = 0;
            } else {
                upperIndexerSystem.drive(.5);
                lowerIndexerSystem.drive(.5);
            }
            lastBeamBreakStatus = upperBeamBreakTriggered;
        }
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
