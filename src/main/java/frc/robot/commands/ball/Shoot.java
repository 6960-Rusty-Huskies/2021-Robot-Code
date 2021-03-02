package frc.robot.commands.ball;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.ball.*;

import static frc.robot.Constants.PID.SHOOTER_FF;


public class Shoot extends CommandBase {

    protected final BallSystem ballSystem;

    public Shoot(BallSystem ballSystem) {
        addRequirements(ballSystem);

        this.ballSystem = ballSystem;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // set the intake arm going down, will auto switch off with limit switch
        double ff = SmartDashboard.getNumber("Shooter FF Value", SHOOTER_FF);
        ballSystem.setShooterFF(ff);
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
