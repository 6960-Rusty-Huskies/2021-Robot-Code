package frc.robot;

import edu.wpi.first.wpilibj.controller.*;
import edu.wpi.first.wpilibj.kinematics.*;
import edu.wpi.first.wpilibj.trajectory.constraint.*;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final int MAX_BALL_COUNT = 3;

    /**
     * Contains CAN Id constants.
     */
    public static final class CAN {
        // MOTORS
        public static final int DRIVE_LEFT_FRONT_MOTOR = 1;
        public static final int DRIVE_LEFT_BACK_MOTOR = 2;
        public static final int DRIVE_RIGHT_FRONT_MOTOR = 3;
        public static final int DRIVE_RIGHT_BACK_MOTOR = 4;

        public static final int INTAKE_ARM_MOTOR = 8;
        public static final int INTAKE_WHEEL_MOTOR = 7;

        public static final int INDEX_LOWER_MOTOR = 12;
        public static final int INDEX_UPPER_MOTOR = 11;

        public static final int SHOOTER_MOTOR = 15;
    }

    /**
     * Contains DIO port connections to the RoboRIO.
     */
    public static final class Digital {
        // ENCODERS
        public static final int INTAKE_ARM_ENCODER_A = 0;
        public static final int INTAKE_ARM_ENCODER_B = 1;
        public static final int DRIVE_RIGHT_ENCODER_A = 2;
        public static final int DRIVE_RIGHT_ENCODER_B = 3;
        public static final int DRIVE_LEFT_ENCODER_A = 4;
        public static final int DRIVE_LEFT_ENCODER_B = 5;


        // LIMIT SWITCHES
        public static final int INTAKE_ARM_LIMIT_SWITCH = 6;
    }

    /**
     * Contains analog port connections to the RoboRIO.
     */
    public static final class Analog {
        // BEAM BREAKS
        public static final int INDEX_UPPER_BEAM_BREAK = 1;
        public static final int INDEX_LOWER_BEAM_BREAK = 0;
    }

    /**
     * Contains PWM port connections to the RoboRIO.
     */
    public static final class PWM {
        // LED strips
        public static final int LED_1 = 0;
        public static final int LED_2 = 1;
    }

    /**
     * Contains USB connections (usually controllers) to the laptop running the robot.
     */
    public static final class USB {
        public static final int DRIVER_JOYSTICK_LEFT = 0;
        public static final int DRIVER_JOYSTICK_RIGHT = 1;

        public static final int OPERATOR_JOYSTICK_LEFT = 2;
        public static final int OPERATOR_JOYSTICK_RIGHT = 3;
    }

    /**
     * Button constants.
     */
    public static final class Button {
        // Button values start at 1.
        public static final int SHOOT_FAR = 7;
        public static final int SHOOT_NEAR = 8;
    }

    /**
     * Contains physical constants such as gear ratios and RPMs.
     */
    public static final class Physical {
        public static final double SHOOTER_VELOCITY_15 = 2900;
    }

    /**
     * Contains PID and feedforward values for subsystems that use a PID controller.
     */
    public static final class PID {
        public static final double SHOOTER_P = 0.00005;
        public static final double SHOOTER_FF = 0.000179;

        public static final double INDEX_P = 1;
        public static final double INDEX_FF = 0.138;
    }

    public static final class DriveConstants {
        public static final boolean leftEncoderReversed = false;
        public static final boolean rightEncoderReversed = true;

        public static final int encoderCPR = 2048;
        public static final double wheelDiameterMeters = 0.152;
        public static final double encoderDistancePerPulse =
                // Assumes the encoders are directly mounted on the wheel shafts
                (wheelDiameterMeters * Math.PI) / (double) encoderCPR;

        public static final boolean kGyroReversed = false;

        // These characterization values MUST be determined either experimentally or theoretically
        // for *your* robot's drive.
        // The Robot Characterization Toolsuite provides a convenient tool for obtaining these
        // values for your robot.
        public static final double ksVolts = 0.878;
        public static final double kvVoltSecondsPerMeter = 3.16;
        public static final double kaVoltSecondsSquaredPerMeter = 0.261;

        // Example value only - as above, this must be tuned for your drive!
        public static final double kPDriveVel = .5;

        public static final double trackwidthMeters = 0.63;
        public static final DifferentialDriveKinematics driveKinematics =
                new DifferentialDriveKinematics(trackwidthMeters);

        public static final DifferentialDriveVoltageConstraint autoVoltageConstraint =
                new DifferentialDriveVoltageConstraint(
                        new SimpleMotorFeedforward(ksVolts, kvVoltSecondsPerMeter, kaVoltSecondsSquaredPerMeter),
                        driveKinematics,
                        10);

        public static final double kTurnP = .001;
        public static final double kTurnI = 0;
        public static final double kTurnD = 0;
        public static final double kTurnToleranceDeg = 5;
        public static final double kTurnRateToleranceDegPerS = 10; // degrees per second
    }

    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = .6;
        public static final double kMaxAccelerationMetersPerSecondSquared = .6;

        // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
    }

}
