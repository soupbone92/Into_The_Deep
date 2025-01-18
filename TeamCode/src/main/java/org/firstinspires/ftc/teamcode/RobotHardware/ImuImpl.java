package org.firstinspires.ftc.teamcode.RobotHardware;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;


// Implement an ImuWrapper for internal IMU and encoders.
public class ImuImpl implements ImuPositionWrapper {
    public ImuImpl(HardwareMap hwmap)
    {
        imu = hwmap.get(IMU.class, "imu");;
        yEncoder = hwmap.dcMotor.get("TBD");
        xEncoder = hwmap.dcMotor.get("TBD");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);
    }
    @Override
    public void reset() {
        imu.resetYaw();
        yEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public double getHeading() {
        YawPitchRollAngles angels =  imu.getRobotYawPitchRollAngles();
        return Math.toDegrees(angels.getYaw());
    }

    @Override
    public double getPosY() {
        return yEncoder.getCurrentPosition() * ticsPerInch;
    }

    @Override
    public double getPosX() {
        return xEncoder.getCurrentPosition() * ticsPerInch;
    }

    @Override
    public void update() {
        // not needed for IMU and encoders.
    }
    private final IMU imu;
    private final DcMotor yEncoder;
    private final DcMotor xEncoder;
    private final double ticsPerInch = 72;
}
