package frc.robot.subsystems.ball;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.*;
import frc.robot.utils.*;


public class BallSystem extends SubsystemBase {

  private final IndexerSystem upperIndexer;
  private final IndexerSystem lowerIndexer;
  private final IntakeSystem intakeSystem;
  private final ShooterSystem shooterSystem;

  private int ballCount = 0;
  private boolean lastLowerBeamBreakStatus;
  private boolean lastUpperBeamBreakStatus;

  private final BeamBreak lowerBeamBreak;
  private final BeamBreak upperBeamBreak;

  /**
   * Creates a new DriveBase.
   */
  public BallSystem() {
    lowerBeamBreak = new BeamBreak(Constants.Analog.INDEX_LOWER_BEAM_BREAK);
    lastLowerBeamBreakStatus = lowerBeamBreak.triggered();
    upperBeamBreak = new BeamBreak(Constants.Analog.INDEX_UPPER_BEAM_BREAK);
    lastUpperBeamBreakStatus = upperBeamBreak.triggered();

    upperIndexer = new IndexerSystem(Constants.CAN.INDEX_UPPER_MOTOR, true);
    lowerIndexer = new IndexerSystem(Constants.CAN.INDEX_LOWER_MOTOR, false);

    intakeSystem = new IntakeSystem();

    shooterSystem = new ShooterSystem();
  }

  public int getBallCount() {
    return ballCount;
  }

  public boolean upperBeamTriggered() {
    return lastUpperBeamBreakStatus;
  }

  public boolean lowerBeamTriggered() {
    return lastLowerBeamBreakStatus;
  }

  public void setUpperIndexer(double speed) {
    upperIndexer.drive(speed);
  }

  public void setLowerIndexer(double speed) {
    lowerIndexer.drive(speed);
  }

  public void setIntakeArm(double speed) {
    intakeSystem.setIntakeArmMotorSpeed(speed);
  }

  public void setIntake(double speed) {
    intakeSystem.setIntakeMotorSpeed(speed);
  }

  public void stopShooter() {
    shooterSystem.stopShooter();
  }

  public void runShooter() {
    shooterSystem.calculate();
  }

  public boolean shooterIsReady() {
    return shooterSystem.ready();
  }

  public void setShooterFF(double ff) {
    shooterSystem.setShooterFF(ff);
  }

  @Override
  public void periodic() {
    boolean lowerBeamBreakTriggered = lowerBeamBreak.triggered();
    if (lastLowerBeamBreakStatus && !lowerBeamBreakTriggered) {
      ballCount++;
    }
    boolean upperBeamBreakTriggered = upperBeamBreak.triggered();
    if (lastUpperBeamBreakStatus && !upperBeamBreakTriggered) {
      ballCount--;
    }
    lastLowerBeamBreakStatus = lowerBeamBreakTriggered;
    lastUpperBeamBreakStatus = upperBeamBreakTriggered;

    SmartDashboard.putNumber("Power Cell Count", ballCount);
    SmartDashboard.putBoolean("Lower BeamBreak", lastLowerBeamBreakStatus);
    SmartDashboard.putBoolean("Upper BeamBreak", lastUpperBeamBreakStatus);
  }

}