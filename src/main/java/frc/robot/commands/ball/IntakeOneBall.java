package frc.robot.commands.ball;

import edu.wpi.first.wpilibj.smartdashboard.*;
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
        beginningBallCount = (int) SmartDashboard.getNumber("Power Cell Count", 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        int currentBallCount = (int) SmartDashboard.getNumber("Power Cell Count", 0);
        return currentBallCount >= MAX_BALL_COUNT || currentBallCount > beginningBallCount;
    }

}
