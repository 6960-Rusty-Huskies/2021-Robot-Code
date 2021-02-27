package frc.robot.commands.ball;

import frc.robot.subsystems.ball.*;

import static frc.robot.Constants.*;


public class IntakeOneBall extends Intake {

  private int beginningBallCount;

  public IntakeOneBall(BallSystem ballSystem) {
    super(ballSystem);
  }

  @Override
  public void initialize() {
    super.initialize();
    beginningBallCount = ballSystem.getBallCount();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ballSystem.getBallCount() >= MAX_BALL_COUNT || ballSystem.getBallCount() > beginningBallCount;
  }

}
