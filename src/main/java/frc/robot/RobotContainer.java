package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.commands.auto.*;
import frc.robot.commands.ball.*;
import frc.robot.commands.drive.*;
import frc.robot.subsystems.ball.*;
import frc.robot.subsystems.drive.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    private final Joystick leftDriverController;
    private final Joystick rightDriverController;
    private final DriveSystem driveSystem;
    private final BallSystem ballSystem;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        leftDriverController = new Joystick(Constants.USB.DRIVER_JOYSTICK_LEFT);
        rightDriverController = new Joystick(Constants.USB.DRIVER_JOYSTICK_RIGHT);

        driveSystem = new DriveSystem();
        driveSystem.setDefaultCommand(new DriveTeleop(driveSystem, leftDriverController, rightDriverController));

        ballSystem = new BallSystem();

        // Configure the button bindings
        configureButtonBindings();

        SmartDashboard.putNumber("PDrive Value", Constants.DriveConstants.kPDriveVel);
        SmartDashboard.putString("Auto Stage", "Auto Not Running");
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        Command intakeCommand = new Intake(ballSystem);
        JoystickButton startIntake = new JoystickButton(leftDriverController, 3);
        startIntake.whenPressed(intakeCommand);
        JoystickButton cancelIntake = new JoystickButton(leftDriverController, 2);
        cancelIntake.cancelWhenPressed(intakeCommand);

        Command intakeOneBallCommand = new IntakeOneBall(ballSystem);
        JoystickButton startIntakeOneBall = new JoystickButton(leftDriverController, 4);
        startIntakeOneBall.whenPressed(intakeOneBallCommand);
        JoystickButton cancelIntakeOneBall = new JoystickButton(leftDriverController, 5);
        cancelIntakeOneBall.cancelWhenPressed(intakeOneBallCommand);

        Command shootAllCommand = new Shoot(ballSystem);
        JoystickButton shootAll = new JoystickButton(rightDriverController, 3);
        shootAll.whenPressed(shootAllCommand);
        JoystickButton cancelShootAll = new JoystickButton(rightDriverController, 2);
        cancelShootAll.cancelWhenPressed(shootAllCommand);

        Command shootOneBallCommand = new ShootOneBall(ballSystem);
        JoystickButton startShootOneBall = new JoystickButton(rightDriverController, 4);
        startShootOneBall.whenPressed(shootOneBallCommand);
        JoystickButton cancelShootOneBall = new JoystickButton(rightDriverController, 5);
        cancelShootOneBall.cancelWhenPressed(shootOneBallCommand);

        Command auto = new AutoPIDRun(driveSystem, ballSystem);
        JoystickButton autoStart = new JoystickButton(leftDriverController, 11);
        autoStart.whenPressed(auto);
        JoystickButton autoStop = new JoystickButton(leftDriverController, 10);
        autoStop.cancelWhenPressed(auto);

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return null;
    }
}
