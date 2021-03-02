package frc.robot.commands.ball;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.ball.*;

import static frc.robot.Constants.MAX_BALL_COUNT;


public class Intake extends CommandBase {

    protected final BallSystem ballSystem;

    public Intake(BallSystem ballSystem) {
        addRequirements(ballSystem);

        this.ballSystem = ballSystem;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // set the intake arm going down, will auto switch off with limit switch
        //ballSystem.setIntakeArm(.25);
        ballSystem.setIntake(.5);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (ballSystem.lowerBeamTriggered()) {
            ballSystem.setLowerIndexer(.5);
        } else {
            ballSystem.setLowerIndexer(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        ballSystem.setIntake(0);
        ballSystem.setLowerIndexer(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return ballSystem.getBallCount() >= MAX_BALL_COUNT;
    }

}
