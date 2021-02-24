package frc.robot.subsystems.ball;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.*;
import edu.wpi.first.wpilibj2.command.*;

/**
 * One section of the Index.
 */
public class IndexerSystem extends SubsystemBase {

  private final CANSparkMax motor;

  public IndexerSystem(int motorId, boolean inverted) {
    motor = new CANSparkMax(motorId, MotorType.kBrushless);
    motor.setInverted(inverted);
  }

  public void drive(double speed) {
    motor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
