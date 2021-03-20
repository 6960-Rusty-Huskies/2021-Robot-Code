package frc.robot.commands.ball;

import frc.robot.subsystems.ball.*;

import static frc.robot.Constants.*;


public class IntakeOneBall extends Intake {

    private int beginningBallCount;

    public IntakeOneBall(IntakeSystem intakeSystem, IntakeArmSystem intakeArmSystem, IndexerSystem lowerIndexerSystem) {
        super(intakeSystem, intakeArmSystem, lowerIndexerSystem);
    }

    @Override
    public void initialize() {
        super.initialize();
        beginningBallCount = ballCount;
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return ballCount >= MAX_BALL_COUNT || ballCount > beginningBallCount;
    }

}
