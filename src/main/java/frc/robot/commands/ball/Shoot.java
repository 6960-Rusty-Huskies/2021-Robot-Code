package frc.robot.commands.ball;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.ball.*;

import static frc.robot.Constants.PID.SHOOTER_FF;
import static frc.robot.Constants.Physical.SHOOTER_VELOCITY_15;


public class Shoot extends CommandBase {

    protected final ShooterSystem shooterSystem;
    protected final IndexerSystem upperIndexerSystem;
    protected final IndexerSystem lowerIndexerSystem;

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
        shooterSystem.setVelocity((int) SmartDashboard.getNumber("Shooter RPM Set Value", SHOOTER_VELOCITY_15));
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        boolean upperBeamBreakTriggered = upperIndexerSystem.isBeamBreakTriggered();
        if (lastBeamBreakStatus && !upperBeamBreakTriggered) {
            SmartDashboard.putNumber("Power Cell Count",
                    SmartDashboard.getNumber("Power Cell Count", 1) - 1);
        }
        lastBeamBreakStatus = upperBeamBreakTriggered;
        SmartDashboard.putBoolean("Upper BeamBreak", lastBeamBreakStatus);

        shooterSystem.calculate();
        if (shooterSystem.ready()) {
            upperIndexerSystem.drive(.5);
            lowerIndexerSystem.drive(.5);
        } else {
            upperIndexerSystem.drive(0);
            lowerIndexerSystem.drive(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        upperIndexerSystem.drive(0);
        lowerIndexerSystem.drive(0);
        shooterSystem.stopShooter();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return SmartDashboard.getNumber("Power Cell Count", 0) <= 0;
    }

}
