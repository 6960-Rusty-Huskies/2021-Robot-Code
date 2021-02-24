package frc.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import static frc.robot.Constants.*;


public class DriveSystem extends SubsystemBase {

  private final DifferentialDrive drive;
  /*
  private final PigeonIMU imu;
  private final Encoder leftEncoder, rightEncoder;
  public boolean isHalfSpeed = false;
   */


  /**
   * Creates a new DriveBase.
   */
  public DriveSystem() {

    final WPI_TalonSRX leftFront = new WPI_TalonSRX(CAN.DRIVE_LEFT_FRONT_MOTOR);
    final WPI_TalonSRX leftBack = new WPI_TalonSRX(CAN.DRIVE_LEFT_BACK_MOTOR);
    final SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftFront, leftBack);

    final WPI_TalonSRX rightFront = new WPI_TalonSRX(CAN.DRIVE_RIGHT_FRONT_MOTOR);
    final WPI_TalonSRX rightBack = new WPI_TalonSRX(CAN.DRIVE_RIGHT_BACK_MOTOR);
    final SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightFront, rightBack);

    //leftEncoder = new Encoder(Digital.DRIVE_LEFT_ENCODER_A, Digital.DRIVE_LEFT_ENCODER_B);
    //rightEncoder = new Encoder(Digital.DRIVE_RIGHT_ENCODER_A, Digital.DRIVE_RIGHT_ENCODER_B);

    //leftEncoder.setDistancePerPulse(2048d * Math.PI * 6d);
    //rightEncoder.setDistancePerPulse(2048d * Math.PI * 6d);

    drive = new DifferentialDrive(leftGroup, rightGroup);
    drive.setDeadband(.05);
    //imu = new PigeonIMU(leftFront);
  }

  public void arcadeDrive(double speed, double turn) {
    /*
    if(isHalfSpeed) {
      speed *= .5;
      turn *= .5;
    }
     */

    speed = MathUtil.clamp(speed, -1, 1);
    turn = MathUtil.clamp(turn, -1, 1);
    drive.arcadeDrive(-speed, turn);
  }

  public void tankDrive(double leftSide, double rightSide) {
    drive.tankDrive(leftSide, rightSide);
  }

  @Override
  public void periodic() {}
}