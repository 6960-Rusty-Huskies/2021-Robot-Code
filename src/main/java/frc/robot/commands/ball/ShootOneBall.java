package frc.robot.commands.ball;

import frc.robot.subsystems.ball.*;


public class ShootOneBall extends Shoot {

    private int beginningBallCount;

    public ShootOneBall(ShooterSystem shooterSystem, IndexerSystem upperIndexerSystem, IndexerSystem lowerIndexerSystem) {
        super(shooterSystem, upperIndexerSystem, lowerIndexerSystem);
    }

    @Override
    public void initialize() {
        super.initialize();
        beginningBallCount = ballCount;
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return ballCount <= 0 || ballCount < beginningBallCount;
    }

}
