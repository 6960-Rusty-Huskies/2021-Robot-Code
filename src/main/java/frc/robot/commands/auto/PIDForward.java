package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.controller.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.*;
import frc.robot.subsystems.drive.*;

public class PIDForward extends PIDCommand {

    private final DriveSystem drive;

    public PIDForward(DriveSystem drive) {
        super(new PIDController(
                        SmartDashboard.getNumber("Auto P Value", Constants.DriveConstants.kTurnP),
                        Constants.DriveConstants.kTurnI,
                        Constants.DriveConstants.kTurnD),
                drive::getHeading,
                0,
                output -> drive.arcadeDrive(-.5, output),
                drive);
        this.drive = drive;
        drive.zeroHeading();

        // Set the controller to be continuous (because it is an angle controller)
        getController().enableContinuousInput(-180, 180);
        // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
        // setpoint before it is considered as having reached the reference
        getController().setTolerance(
                Constants.DriveConstants.kTurnToleranceDeg,
                Constants.DriveConstants.kTurnRateToleranceDegPerS);
    }

    @Override
    public void initialize() {
        super.initialize();
        SmartDashboard.putString("Auto Stage", "Forward");
    }

    @Override
    public boolean isFinished() {
        return drive.getAverageEncoderDistance() >= 2;
    }
}
