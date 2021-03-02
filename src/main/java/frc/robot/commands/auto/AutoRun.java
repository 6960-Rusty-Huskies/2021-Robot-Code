package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.ball.*;
import frc.robot.subsystems.ball.*;
import frc.robot.subsystems.drive.*;

public class AutoRun extends SequentialCommandGroup {

    public AutoRun(DriveSystem driveSystem, BallSystem ballSystem) {
        ParallelCommandGroup driveForwardAndIntake = new ParallelCommandGroup(
                new Intake(ballSystem),
                new MoveForward(driveSystem)
        );
        ballSystem.setBallCount(3);
        addCommands(new Shoot(ballSystem), driveForwardAndIntake, new MoveBackward(driveSystem));
    }
}
