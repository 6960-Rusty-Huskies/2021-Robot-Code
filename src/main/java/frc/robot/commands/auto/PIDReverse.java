package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.controller.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpiutil.math.*;
import frc.robot.subsystems.drive.*;

public class PIDReverse extends CommandBase {

    private final DriveSystem drive;
    private final PIDController driveController;
    private final PIDController headingController;

    public PIDReverse(DriveSystem drive) {
        this.drive = drive;
        addRequirements(drive);

        double kP = SmartDashboard.getNumber("Drive P Value", 1);
        driveController = new PIDController(kP,0,0);
        driveController.setTolerance(.005);

        double kP_heading = SmartDashboard.getNumber("Heading P Value", 1);
        headingController = new PIDController(kP_heading,0,0);
        // should be 2 degrees
        headingController.setTolerance(0);
        headingController.enableContinuousInput(-180, 180);
    }

    @Override
    public void initialize() {
        // Move back to 0 meters out from where we started auto
        driveController.setSetpoint(0);
        // should go straight from reset position of 0 degrees
        headingController.setSetpoint(0);

        SmartDashboard.putString("Auto Stage", "Reverse");
    }

    @Override
    public void execute() {
        double speed = .5 * MathUtil.clamp(driveController.calculate(drive.getAverageEncoderDistance()), -1, 1);
        double turn = .3 * MathUtil.clamp(headingController.calculate(drive.getHeading()), -1, 1);
        drive.arcadeDrive(speed, turn);
    }

    @Override
    public boolean isFinished() {
        return driveController.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

}
