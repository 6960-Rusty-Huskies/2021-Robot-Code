package frc.robot.commands.ball;

import frc.robot.subsystems.ball.*;

import static frc.robot.Constants.*;


public class IntakeOneBall extends Intake {

  private final int beginningBallCount;

  public IntakeOneBall(BallSystem ballSystem) {
    super(ballSystem);
    beginningBallCount = ballSystem.getBallCount();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ballSystem.getBallCount() >= MAX_BALL_COUNT || ballSystem.getBallCount() > beginningBallCount;
  }

}
