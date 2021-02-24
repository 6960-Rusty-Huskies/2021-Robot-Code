package frc.robot;

import edu.wpi.first.wpilibj.*;

import java.util.*;

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

        // IMU
        public static final int PIGEON_IMU = 16;
    }

    /**
     * Contains DIO port connections to the RoboRIO.
     */
    public static final class Digital {
        // ENCODERS
        public static final int DRIVE_LEFT_ENCODER_A = 2;
        public static final int DRIVE_LEFT_ENCODER_B = 3;

        public static final int DRIVE_RIGHT_ENCODER_A = 4;
        public static final int DRIVE_RIGHT_ENCODER_B = 5;

        public static final int INTAKE_ENCODER_A = 0;
        public static final int INTAKE_ENCODER_B = 1;

        // LIMIT SWITCHES
        public static final int INTAKE_ARM_LIMIT_SWITCH = 6;
        public static final int LIFT_SWITCH_TOP = 10;
        public static final int LIFT_SWITCH_BOTTOM = 11;
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

        public static final int ROTATION_CONTROL = 9;
        public static final int POSITION_CONTROL = 10;

        public static final int INDEX_UP = 1;
        public static final int INDEX_DOWN = 5;
        public static final int INDEX_AUTO_TOGGLE = 2;

        public static final AbstractMap.SimpleEntry<GenericHID.Hand, Integer>
                DRIVE_HALF_SPEED = new AbstractMap.SimpleEntry<GenericHID.Hand, Integer>(GenericHID.Hand.kLeft, 2),
                RAISE_WINCH = new AbstractMap.SimpleEntry<GenericHID.Hand, Integer>(GenericHID.Hand.kRight, 3);
    }

    /**
     * Contains physical constants such as gear ratios and RPMs.
     */
    public static final class Physical {
        public static final double SHOOTER_RPM_NEAR = 2000;
        public static final double SHOOTER_RPM_FAR = 3650;
        public static final double SHOOTER_RPM_AUTO = 2200;

        public static final double LL_AREA_FAR = 0.1;

        public static final double INDEX_SPEED = 1./3.;
        public static final double INDEX_GEAR_RATIO = 10;

        public static final double INTAKE_LOW_ANGLE = 120;
        public static final double INTAKE_HIGH_ANGLE = 0;
        public static final double INTAKE_GEAR_RATIO = 16. / 18.;

        public static final double LIFT_SPEED_UP = 0.5;
        public static final double LIFT_SPEED_DOWN = 0.1;
    }

    /**
     * Contains PID and feedforward values for subsystems that use a PID controller.
     */
    public static final class PID {

        public static final double SHOOTER_P = 0.00035;
        public static final double SHOOTER_FF = 0.00025;

        public static final double INDEX_P = 1;
        public static final double INDEX_FF = 0.138;

        public static final double DRIVE_BASE_P = 0.01;
        public static final double DRIVE_BASE_D = 0;

        public static final double INTAKE_S = 4.52;
        public static final double INTAKE_V = 0.00606;
        public static final double INTAKE_COS = 0.437;
        public static final double INTAKE_A = 9.58E-5;
        public static final double INTAKE_P = 0.000721;
        public static final double INTAKE_D = 0.000339;
    }

}
