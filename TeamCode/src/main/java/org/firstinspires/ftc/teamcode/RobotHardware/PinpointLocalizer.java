package org.firstinspires.ftc.teamcode.RobotHardware;

import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.acmerobotics.roadrunner.Vector2dDual;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PinpointLocalizer implements Localizer {
    public GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer
    public PinpointLocalizer (HardwareMap hardwareMap, double TicksPerInch)
    {
        odo = hardwareMap.get(GoBildaPinpointDriver.class,"pinpoint");
        odo.setEncoderResolution(8192.0/100.53);
    }

    @Override
    public Twist2dDual<Time> update() {

        //? Localizer wants inches?

        double xloc = odo.getPosX()/25.4;
        double yloc = odo.getPosY()/25.4;
        double xvol = odo.getVelX()/25.4;
        double yvol = odo.getVelY()/25.4;
        double head = odo.getHeading();
        double hvol = odo.getHeadingVelocity();
        DualNum<Time> xAndVelocity = new DualNum<>(new double[]{xloc, xvol});
        DualNum<Time> yAndVelocity = new DualNum<>(new double[]{yloc, yvol});
        DualNum<Time> headingAndVelocity = new DualNum<>(new double[]{head, hvol});

        Twist2dDual<Time> location = new Twist2dDual<Time>(
                new Vector2dDual<Time>(xAndVelocity, yAndVelocity),
                headingAndVelocity);

        return location;
    }
}