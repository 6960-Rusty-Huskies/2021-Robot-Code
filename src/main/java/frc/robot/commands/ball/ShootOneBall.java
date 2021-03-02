package frc.robot.commands.ball;

import frc.robot.subsystems.ball.*;


public class ShootOneBall extends Shoot {

    private int beginningBallCount;

    public ShootOneBall(BallSystem ballSystem) {
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
        return ballSystem.getBallCount() <= 0 || ballSystem.getBallCount() < beginningBallCount;
    }

}
