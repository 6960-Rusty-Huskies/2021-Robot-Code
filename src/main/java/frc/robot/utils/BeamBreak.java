package frc.robot.utils;

import edu.wpi.first.wpilibj.*;

/**
 * A beam break sensor on the robot.
 */
public class BeamBreak {

    private AnalogInput internalSensor;

    public BeamBreak(int channel) {
        internalSensor = new AnalogInput(channel);
    }

    public double getVoltage() {
        return internalSensor.getVoltage();
    }

    public boolean triggered() {
        return internalSensor.getVoltage() > 2.5;
    }
}
