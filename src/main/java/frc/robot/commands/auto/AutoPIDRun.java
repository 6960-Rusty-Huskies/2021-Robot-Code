package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.ball.*;
import frc.robot.subsystems.ball.*;
import frc.robot.subsystems.drive.*;

public class AutoPIDRun extends SequentialCommandGroup {

    public AutoPIDRun(DriveSystem driveSystem, BallSystem ballSystem) {
        SmartDashboard.putString("Auto Stage", "Running Auto");
        ParallelRaceGroup driveForwardAndIntake = new ParallelRaceGroup(
                new Intake(ballSystem),
                new PIDForward(driveSystem)
        );
        //ballSystem.setBallCount(3);
        addCommands(new Shoot(ballSystem, 7.5d),
                driveForwardAndIntake,
                new WaitCommand(.5),
                new PIDReverse(driveSystem),
                new Shoot(ballSystem, 7.5d));
    }
}
