package org.firstinspires.ftc.teamcode.RobotHardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

// ImuPostionWrapper implementation for GoBilda Pinpoint computer.
public class PinpointImpl implements ImuPositionWrapper {
    private final GoBildaPinpointDriver pp;

    public PinpointImpl(HardwareMap hwmap) {
        pp = hwmap.get(GoBildaPinpointDriver.class, "pinpoint");
        pp.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.REVERSED);
    }

    @Override
    public void reset() {
        pp.resetPosAndIMU();
    }

    @Override
    public double getHeading() {
        return Math.toDegrees(pp.getHeading());
    }

    @Override
    public double getPosY() {
        return pp.getPosY() * Constants.mmToInch;
    }

    @Override
    public double getPosX() {
        return pp.getPosX() * Constants.mmToInch;
    }

    @Override
    public void update() {
        pp.update();
    }

}
