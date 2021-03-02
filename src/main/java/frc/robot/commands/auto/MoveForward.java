package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.controller.*;
import edu.wpi.first.wpilibj.geometry.*;
import edu.wpi.first.wpilibj.trajectory.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants;
import frc.robot.subsystems.drive.*;

import java.util.*;

import static frc.robot.Constants.DriveConstants.autoVoltageConstraint;


public class MoveForward extends RamseteCommand {

    private static final TrajectoryConfig config =
            new TrajectoryConfig(
                    Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                    .setKinematics(Constants.DriveConstants.driveKinematics)
                    .addConstraint(autoVoltageConstraint);

    private static final Trajectory trajectory =
            TrajectoryGenerator.generateTrajectory(
                    new Pose2d(0, 0, new Rotation2d(0)),
                    List.of(new Translation2d(1, 0), new Translation2d(2, 0)),
                    new Pose2d(3, 0, new Rotation2d(0)),
                    config);

    public MoveForward(DriveSystem driveSystem) {
        super(trajectory,
                driveSystem::getPose,
                new RamseteController(Constants.AutoConstants.kRamseteB, Constants.AutoConstants.kRamseteZeta),
                new SimpleMotorFeedforward(
                        Constants.DriveConstants.ksVolts,
                        Constants.DriveConstants.kvVoltSecondsPerMeter,
                        Constants.DriveConstants.kaVoltSecondsSquaredPerMeter),
                Constants.DriveConstants.driveKinematics,
                driveSystem::getWheelSpeeds,
                new PIDController(Constants.DriveConstants.kPDriveVel, 0, 0),
                new PIDController(Constants.DriveConstants.kPDriveVel, 0, 0),
                driveSystem::tankDriveVolts,
                driveSystem);
    }

}
