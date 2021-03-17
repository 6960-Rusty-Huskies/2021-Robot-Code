package frc.robot.subsystems.ball;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.utils.*;

/**
 * One section of the Index.
 */
public class IndexerSystem extends SubsystemBase {

    private final CANSparkMax motor;
    protected final BeamBreak beamBreak;

    public IndexerSystem(int motorId, boolean inverted, int beamBreakId) {
        motor = new CANSparkMax(motorId, MotorType.kBrushless);
        motor.setInverted(inverted);
        beamBreak = new BeamBreak(beamBreakId);
    }

    public void drive(double speed) {
        motor.set(speed);
    }

    public boolean isBeamBreakTriggered() {
        return beamBreak.triggered();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

}
