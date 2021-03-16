package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.ball.*;
import frc.robot.subsystems.ball.*;
import frc.robot.subsystems.drive.*;

public class AutoRun extends SequentialCommandGroup {

    public AutoRun(DriveSystem driveSystem,
                   ShooterSystem shooterSystem,
                   IntakeSystem intakeSystem,
                   IntakeArmSystem intakeArmSystem,
                   IndexerSystem lowerIndexerSystem,
                   IndexerSystem upperIndexerSystem) {
        ParallelCommandGroup driveForwardAndIntake = new ParallelCommandGroup(
                new Intake(intakeSystem, intakeArmSystem, lowerIndexerSystem),
                new MoveForward(driveSystem)
        );
        SmartDashboard.putNumber("Power Cell Count", 3);
        addCommands(new Shoot(shooterSystem, upperIndexerSystem, lowerIndexerSystem), driveForwardAndIntake, new MoveBackward(driveSystem));
    }
}
