package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
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
    private final IndexerSystem upperIndexerSystem;
    private final IndexerSystem lowerIndexerSystem;
    private final IntakeSystem intakeSystem;
    private final IntakeArmSystem intakeArmSystem;
    private final ShooterSystem shooterSystem;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        leftDriverController = new Joystick(Constants.USB.DRIVER_JOYSTICK_LEFT);
        rightDriverController = new Joystick(Constants.USB.DRIVER_JOYSTICK_RIGHT);

        driveSystem = new DriveSystem();
        driveSystem.setDefaultCommand(new DriveTeleop(driveSystem, leftDriverController, rightDriverController));

        upperIndexerSystem = new IndexerSystem(
                Constants.CAN.INDEX_UPPER_MOTOR,
                true,
                Constants.Analog.INDEX_UPPER_BEAM_BREAK) {

            @Override
            public void periodic() {
                // This method will be called once per scheduler run
                SmartDashboard.putBoolean("Upper BeamBreak", isBeamBreakTriggered());
            }

        };

        lowerIndexerSystem = new IndexerSystem(
                Constants.CAN.INDEX_LOWER_MOTOR,
                false,
                Constants.Analog.INDEX_LOWER_BEAM_BREAK) {

            @Override
            public void periodic() {
                // This method will be called once per scheduler run
                SmartDashboard.putBoolean("Lower BeamBreak", isBeamBreakTriggered());
            }

        };

        intakeSystem = new IntakeSystem();

        intakeArmSystem = new IntakeArmSystem();
        //intakeArmSystem.setDefaultCommand(new IntakeArmUp(intakeArmSystem));

        shooterSystem = new ShooterSystem();

        // Configure the button bindings
        configureButtonBindings();

        SmartDashboard.putNumber("PDrive Value", Constants.DriveConstants.kPDriveVel);
        SmartDashboard.putString("Auto Stage", "Auto Not Running");
        SmartDashboard.putNumber("Power Cell Count", 0);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {

        // Intake up to 3 balls and cancel button
        Command intakeCommand = new StartEndCommand(() -> intakeArmSystem.setIntakeArmMotorSpeed(.25),
                () -> intakeArmSystem.setIntakeArmMotorSpeed(0),
                intakeArmSystem)
                .withTimeout(.25)
                .andThen(new Intake(intakeSystem, intakeArmSystem, lowerIndexerSystem));
        JoystickButton startIntake = new JoystickButton(leftDriverController, 3);
        startIntake.whenPressed(intakeCommand);
        JoystickButton cancelIntake = new JoystickButton(leftDriverController, 2);
        cancelIntake.cancelWhenPressed(intakeCommand);

        // Intake a single ball and cancel button
        Command intakeOneBallCommand = new InstantCommand(() -> intakeArmSystem.setIntakeArmMotorSpeed(.25), intakeArmSystem)
                .withTimeout(.25)
                .andThen(new IntakeOneBall(intakeSystem, intakeArmSystem, lowerIndexerSystem));
        JoystickButton startIntakeOneBall = new JoystickButton(leftDriverController, 4);
        startIntakeOneBall.whenPressed(intakeOneBallCommand);
        JoystickButton cancelIntakeOneBall = new JoystickButton(leftDriverController, 5);
        cancelIntakeOneBall.cancelWhenPressed(intakeOneBallCommand);

        // Shoot all balls and cancel button
        Command shootAllCommand = new Shoot(shooterSystem, upperIndexerSystem, lowerIndexerSystem);
        JoystickButton shootAll = new JoystickButton(rightDriverController, 3);
        shootAll.whenPressed(shootAllCommand);
        JoystickButton cancelShootAll = new JoystickButton(rightDriverController, 2);
        cancelShootAll.cancelWhenPressed(shootAllCommand);

        // Shoot single ball and cancel button
        Command shootOneBallCommand = new ShootOneBall(shooterSystem, upperIndexerSystem, lowerIndexerSystem);
        JoystickButton startShootOneBall = new JoystickButton(rightDriverController, 4);
        startShootOneBall.whenPressed(shootOneBallCommand);
        JoystickButton cancelShootOneBall = new JoystickButton(rightDriverController, 5);
        cancelShootOneBall.cancelWhenPressed(shootOneBallCommand);

        // Setup buttons for start and stop of Auto command for testing
        Command auto = new AutoPIDRun(driveSystem, shooterSystem, intakeSystem, intakeArmSystem, lowerIndexerSystem, upperIndexerSystem);
        JoystickButton autoStart = new JoystickButton(leftDriverController, 11);
        autoStart.whenPressed(auto);
        JoystickButton autoStop = new JoystickButton(leftDriverController, 10);
        autoStop.cancelWhenPressed(auto);

        // Setup buttons for moving Intake Arm up and down
        JoystickButton intakeArmUp = new JoystickButton(rightDriverController, 11);
        intakeArmUp.whenHeld(new StartEndCommand(() -> intakeArmSystem.setIntakeArmMotorSpeed(.25), () -> intakeArmSystem.setIntakeArmMotorSpeed(0)));
        JoystickButton intakeArmDown = new JoystickButton(rightDriverController, 10);
        intakeArmDown.whenHeld(new StartEndCommand(() -> intakeArmSystem.setIntakeArmMotorSpeed(-.35), () -> intakeArmSystem.setIntakeArmMotorSpeed(0)));

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
