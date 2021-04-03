package frc.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.sensors.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.*;
import edu.wpi.first.wpilibj.kinematics.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.*;

import static frc.robot.Constants.*;
import static frc.robot.Constants.DriveConstants.leftEncoderReversed;
import static frc.robot.Constants.DriveConstants.rightEncoderReversed;


public class DriveSystem extends SubsystemBase {

    private final DifferentialDrive drive;
    private final PigeonIMU gyro;
    private final Encoder leftEncoder;
    private final Encoder rightEncoder;
    private final SpeedControllerGroup leftMotors;
    private final SpeedControllerGroup rightMotors;
    // Odometry class for tracking robot pose
    private final DifferentialDriveOdometry odometry;

    /**
     * Creates a new DriveBase.
     */
    public DriveSystem() {
        final WPI_TalonSRX leftFront = new WPI_TalonSRX(Constants.CAN.DRIVE_LEFT_FRONT_MOTOR);
        leftFront.setNeutralMode(NeutralMode.Brake);
        final WPI_TalonSRX leftBack = new WPI_TalonSRX(Constants.CAN.DRIVE_LEFT_BACK_MOTOR);
        leftBack.setNeutralMode(NeutralMode.Brake);
        leftMotors = new SpeedControllerGroup(leftFront, leftBack);
        leftMotors.setInverted(true);

        final WPI_TalonSRX rightFront = new WPI_TalonSRX(Constants.CAN.DRIVE_RIGHT_FRONT_MOTOR);
        rightFront.setNeutralMode(NeutralMode.Brake);
        final WPI_TalonSRX rightBack = new WPI_TalonSRX(Constants.CAN.DRIVE_RIGHT_BACK_MOTOR);
        rightBack.setNeutralMode(NeutralMode.Brake);
        rightMotors = new SpeedControllerGroup(rightFront, rightBack);
        rightMotors.setInverted(true);

        leftEncoder = new Encoder(Digital.DRIVE_LEFT_ENCODER_A, Digital.DRIVE_LEFT_ENCODER_B, leftEncoderReversed);
        rightEncoder = new Encoder(Digital.DRIVE_RIGHT_ENCODER_A, Digital.DRIVE_RIGHT_ENCODER_B, rightEncoderReversed);
        leftEncoder.setDistancePerPulse(Constants.DriveConstants.encoderDistancePerPulse);
        rightEncoder.setDistancePerPulse(Constants.DriveConstants.encoderDistancePerPulse);
        resetEncoders();

        drive = new DifferentialDrive(leftMotors, rightMotors);
        drive.setDeadband(.01);

        gyro = new PigeonIMU(rightFront);

        odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

        SmartDashboard.putNumber("Auto P Value", Constants.DriveConstants.kTurnP);
    }

    public void arcadeDrive(double speed, double turn) {
        drive.arcadeDrive(speed, turn * -1);
    }

    public void tankDrive(double leftSide, double rightSide) {
        drive.tankDrive(leftSide, rightSide);
    }

    public void stop() {
        rightMotors.stopMotor();
        leftMotors.stopMotor();
    }

    /**
     * Resets the drive encoders to currently read a position of 0.
     */
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    /**
     * Returns the heading of the robot.
     *
     * @return the robot's heading in degrees, from -180 to 180
     */
    public double getHeading() {
        double heading = gyro.getFusedHeading();
        SmartDashboard.putNumber("Heading", heading);
        return heading;
    }

    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    /**
     * Returns the current wheel speeds of the robot.
     *
     * @return The current wheel speeds.
     */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
    }

    public double encoderDiff() {
        return leftEncoder.getDistance() - rightEncoder.getDistance();
    }

    /** Zeroes the heading of the robot. */
    public void zeroHeading() {
        gyro.setFusedHeading(0);
    }

    /**
     * Controls the left and right sides of the drive directly with voltages.
     *
     * @param leftVolts  the commanded left output
     * @param rightVolts the commanded right output
     */
    public void tankDriveVolts(double leftVolts, double rightVolts) {
        // DifferentialDrive auto sets right side to inverted... hence, right side is inverted.
        // Invert left side instead if we have inverted motors for drive
        leftMotors.setVoltage(-leftVolts);
        rightMotors.setVoltage(rightVolts);
        drive.feed();
    }

    /**
     * Gets the average distance of the two encoders.
     *
     * @return the average of the two encoder readings
     */
    public double getAverageEncoderDistance() {
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
    }

    /**
     * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
     *
     * @param maxOutput the maximum output to which the drive will be constrained
     */
    public void setMaxOutput(double maxOutput) {
        drive.setMaxOutput(maxOutput);
    }

    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        odometry.update(
                Rotation2d.fromDegrees(getHeading()),
                leftEncoder.getDistance(),
                rightEncoder.getDistance());

        SmartDashboard.putNumber("Left Encoder", leftEncoder.getDistance());
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getDistance());
    }
}