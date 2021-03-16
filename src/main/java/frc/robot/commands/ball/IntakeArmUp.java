package frc.robot.commands.ball;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.ball.*;


public class IntakeArmUp extends CommandBase {

    protected final IntakeArmSystem intakeArmSystem;
    private boolean initialized = false;

    public IntakeArmUp(IntakeArmSystem intakeArmSystem) {
        addRequirements(intakeArmSystem);

        this.intakeArmSystem = intakeArmSystem;
    }

    @Override
    public void initialize() {
        initialized = false;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (!initialized) {
            if (intakeArmSystem.isIntakeArmUp()) {
                intakeArmSystem.setIntakeArmMotorSpeed(0);
                initialized = true;
                intakeArmSystem.initializeSetpoint();
                intakeArmSystem.enable();
            } else {
                intakeArmSystem.setIntakeArmMotorSpeed(-.25);
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intakeArmSystem.disable();
    }

}
