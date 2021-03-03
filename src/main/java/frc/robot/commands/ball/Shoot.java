package frc.robot.commands.ball;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.ball.*;

import static frc.robot.Constants.PID.SHOOTER_FF;


public class Shoot extends CommandBase {

    protected final BallSystem ballSystem;
    protected double distance = 0;

    public Shoot(BallSystem ballSystem, double distance) {
        addRequirements(ballSystem);
        this.distance = distance;

        this.ballSystem = ballSystem;
    }

    public Shoot(BallSystem ballSystem) {
        this(ballSystem, 0);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // set the intake arm going down, will auto switch off with limit switch
        ShootingConfig shootingConfig = ballSystem.getShootingConfig(distance);
        if (shootingConfig == null) {
            double ff = SmartDashboard.getNumber("Shooter FF Value", SHOOTER_FF);
            ballSystem.setShooterFF(ff);
            int velocity = (int) SmartDashboard.getNumber("Shooter RPM Set Value", 2000);
            ballSystem.setVelocity(velocity);
        } else {
            ballSystem.setShooterFF(shootingConfig.getFf());
            ballSystem.setVelocity(shootingConfig.getSpeed());
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        ballSystem.runShooter();
        if (ballSystem.shooterIsReady()) {
            ballSystem.setUpperIndexer(.5);
            ballSystem.setLowerIndexer(.5);
        } else {
            ballSystem.setUpperIndexer(0);
            ballSystem.setLowerIndexer(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        ballSystem.setUpperIndexer(0);
        ballSystem.setLowerIndexer(0);
        ballSystem.stopShooter();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return ballSystem.getBallCount() <= 0;
    }

}
