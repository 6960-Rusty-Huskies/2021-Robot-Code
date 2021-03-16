package frc.robot.subsystems.ball;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.*;


public class IntakeArmSystem extends PIDSubsystem {

    private final WPI_VictorSPX intakeArmMotor;
    private final DigitalInput limitSwitch;
    private final Encoder armEncoder;

    public IntakeArmSystem() {
        super(new PIDController(.005, 0, 0));
        getController().setTolerance(10);

        intakeArmMotor = new WPI_VictorSPX(Constants.CAN.INTAKE_ARM_MOTOR);
        intakeArmMotor.setNeutralMode(NeutralMode.Brake);

        limitSwitch = new DigitalInput(Constants.Digital.INTAKE_ARM_LIMIT_SWITCH);

        armEncoder = new Encoder(Constants.Digital.INTAKE_ARM_ENCODER_A, Constants.Digital.INTAKE_ARM_ENCODER_B, false);
    }

    public void initializeSetpoint() {
        setSetpoint(armEncoder.get());
    }

    public boolean isIntakeArmUp() {
        return limitSwitch.get();
    }

    public void setIntakeArmMotorSpeed(double speed) {
        intakeArmMotor.set(speed);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putNumber("Intake Arm Encoder", armEncoder.get());
        SmartDashboard.putBoolean("Intake Arm LimitSwitch", isIntakeArmUp());
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        intakeArmMotor.setVoltage(output);
    }

    @Override
    protected double getMeasurement() {
        return armEncoder.get();
    }

}
