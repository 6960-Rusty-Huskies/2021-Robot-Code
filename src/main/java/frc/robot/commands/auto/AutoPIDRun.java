package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.ball.*;
import frc.robot.subsystems.ball.*;
import frc.robot.subsystems.drive.*;

public class AutoPIDRun extends SequentialCommandGroup {

    public AutoPIDRun(DriveSystem driveSystem,
                      ShooterSystem shooterSystem,
                      IntakeSystem intakeSystem,
                      IntakeArmSystem intakeArmSystem,
                      IndexerSystem lowerIndexerSystem,
                      IndexerSystem upperIndexerSystem) {
        SmartDashboard.putString("Auto Stage", "Auto Running");
        ParallelRaceGroup driveForwardAndIntake = new ParallelRaceGroup(
                new Intake(intakeSystem, intakeArmSystem, lowerIndexerSystem),
                new PIDForward(driveSystem)
        );
        SmartDashboard.putNumber("Power Cell Count", 3);
        addCommands(new Shoot(shooterSystem, upperIndexerSystem, lowerIndexerSystem),
                driveForwardAndIntake,
                new WaitCommand(.5),
                new PIDReverse(driveSystem),
                new Shoot(shooterSystem, upperIndexerSystem, lowerIndexerSystem));
    }
}
