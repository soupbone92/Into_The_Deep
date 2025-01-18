package org.firstinspires.ftc.teamcode.RobotHardware;

// Interface for accessing hardware that provides
// header and position information.
// Implemented by ImuImpl and PinpointImpl.
public interface ImuPositionWrapper {

    // Reset the IMU heading and position if possible.
    public void reset();
    // Get heading in degrees.
    public double getHeading();
    // Get Y position in inches.  +Y is forward -Y is backward relative to robot.
    public double getPosY();
    // Get X position in inches.  +X is left -X is right relative to robot.
    public double getPosX();
    // Called to read information from hardware if needed.
    // Users of ImuPositionWrapper should always call this before reading new values.
    public void update();

}

