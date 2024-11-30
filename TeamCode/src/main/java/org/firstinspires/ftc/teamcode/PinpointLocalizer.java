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
        DualNum<Double> xAndVel = new DualNum<>(new double[]{0.0, 0.0});
        DualNum<Double> yAndVel = new DualNum<>(new double[]{0.0, 0.0});
        DualNum<Double> headingAndVel = new DualNum<>(new double[]{0.0, 0.0});

        Twist2dDual<Double> location = new Twist2dDual<Double>(
                new Vector2dDual<Double>(xAndVel, yAndVel),
                headingAndVel);

        return null;
    }
}
