package frc.robot.commands.ball;

import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.subsystems.ball.*;


public class ShootOneBall extends Shoot {

    private int beginningBallCount;

    public ShootOneBall(ShooterSystem shooterSystem, IndexerSystem upperIndexerSystem, IndexerSystem lowerIndexerSystem) {
        super(shooterSystem, upperIndexerSystem, lowerIndexerSystem);
    }

    @Override
    public void initialize() {
        super.initialize();
        beginningBallCount = (int) SmartDashboard.getNumber("Power Cell Count", 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        int currentBallCount = (int) SmartDashboard.getNumber("Power Cell Count", 0);
        return currentBallCount <= 0 || currentBallCount < beginningBallCount;
    }

}
