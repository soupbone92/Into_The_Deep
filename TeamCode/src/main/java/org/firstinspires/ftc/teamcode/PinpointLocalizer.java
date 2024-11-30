package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.acmerobotics.roadrunner.Vector2dDual;

import java.util.ArrayList;
import java.util.List;

public class PinpointLocalizer implements Localizer {
    @Override
    public Twist2dDual<Time> update() {

        //? Localizer wants inches?

        DualNum<Time> xAndVelocity = new DualNum<>(new double[]{0.0, 0.0});
        DualNum<Time> yAndVelocity = new DualNum<>(new double[]{0.0, 0.0});
        DualNum<Time> headingAndVelocity = new DualNum<>(new double[]{0.0, 0.0});

        Twist2dDual<Time> location = new Twist2dDual<Time>(
                new Vector2dDual<Time>(xAndVelocity, yAndVelocity),
                headingAndVelocity);

        return location;
    }
}
