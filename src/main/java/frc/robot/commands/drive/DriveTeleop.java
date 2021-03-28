package frc.robot.commands.drive;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.*;

public class DriveTeleop extends CommandBase {

    private final DriveSystem driveSystem;
    private final Joystick leftDriverController;
    private final Joystick rightDriverController;

    /**
     * Creates a new ArcadeDrive.
     */
    public DriveTeleop(DriveSystem driveSystem, Joystick leftDriverController, Joystick rightDriverController) {
        addRequirements(driveSystem);

        this.driveSystem = driveSystem;
        this.leftDriverController = leftDriverController;
        this.rightDriverController = rightDriverController;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        driveSystem.arcadeDrive(leftDriverController.getY(), rightDriverController.getX());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
