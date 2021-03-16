package frc.robot.subsystems.ball;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.*;


public class IntakeSystem extends SubsystemBase {

    private final WPI_VictorSPX intakeMotor;

    public IntakeSystem() {
        intakeMotor = new WPI_VictorSPX(Constants.CAN.INTAKE_WHEEL_MOTOR);
        intakeMotor.setInverted(true);
    }

    public void setIntakeMotorSpeed(double speed) {
        intakeMotor.set(speed);
    }

}
