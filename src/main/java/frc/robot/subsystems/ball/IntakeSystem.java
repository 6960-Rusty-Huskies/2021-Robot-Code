package frc.robot.subsystems.ball;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.*;

/**
 * One section of the Index.
 */
public class IntakeSystem extends SubsystemBase {

  private final WPI_VictorSPX intakeMotor;
  private final WPI_VictorSPX intakeArmMotor;
  private final DigitalInput limitSwitch;

  public IntakeSystem() {
    intakeMotor = new WPI_VictorSPX(Constants.CAN.INTAKE_WHEEL_MOTOR);
    intakeMotor.setInverted(true);

    intakeArmMotor = new WPI_VictorSPX(Constants.CAN.INTAKE_ARM_MOTOR);
    intakeArmMotor.setNeutralMode(NeutralMode.Brake);

    limitSwitch = new DigitalInput(Constants.Digital.INTAKE_ARM_LIMIT_SWITCH);
  }

  public boolean limitSwitchTriggered() {
    return limitSwitch.get();
  }

  public void setIntakeMotorSpeed(double speed) {
    intakeMotor.set(speed);
  }

  public void setIntakeArmMotorSpeed(double speed) {
    intakeArmMotor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // If the limit switch is triggered and the speed shows it's lowering, then stop it
    if (limitSwitchTriggered() && intakeArmMotor.get() < 0) {
      intakeArmMotor.set(0);
    }
  }

}
