package frc.robot.commands.ball;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.ball.*;

import static frc.robot.Constants.MAX_BALL_COUNT;


public class Intake extends CommandBase {

    protected final IntakeSystem intakeSystem;
    protected final IntakeArmSystem intakeArmSystem;
    protected final IndexerSystem lowerIndexerSystem;
    protected boolean lastBeamBreakStatus;

    public Intake(IntakeSystem intakeSystem, IntakeArmSystem intakeArmSystem, IndexerSystem lowerIndexerSystem) {
        addRequirements(intakeSystem, intakeArmSystem, lowerIndexerSystem);

        this.intakeSystem = intakeSystem;
        this.intakeArmSystem = intakeArmSystem;
        this.lowerIndexerSystem = lowerIndexerSystem;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // set the intake arm going down, will auto switch off with limit switch
        intakeSystem.setIntakeMotorSpeed(.5);
        lastBeamBreakStatus = lowerIndexerSystem.isBeamBreakTriggered();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        boolean lowerBeamBreakTriggered = lowerIndexerSystem.isBeamBreakTriggered();
        if (lastBeamBreakStatus && !lowerBeamBreakTriggered) {
            SmartDashboard.putNumber("Power Cell Count",
                    SmartDashboard.getNumber("Power Cell Count", 0) + 1);
        }
        if (lowerBeamBreakTriggered) {
            lowerIndexerSystem.drive(.5);
        } else {
            lowerIndexerSystem.drive(0);
        }

        lastBeamBreakStatus = lowerBeamBreakTriggered;
        SmartDashboard.putBoolean("Lower BeamBreak", lastBeamBreakStatus);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intakeSystem.setIntakeMotorSpeed(0);
        lowerIndexerSystem.drive(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return SmartDashboard.getNumber("Power Cell Count", 0) >= MAX_BALL_COUNT;
    }

}
