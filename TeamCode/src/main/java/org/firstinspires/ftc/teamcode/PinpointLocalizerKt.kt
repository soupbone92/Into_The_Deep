package org.firstinspires.ftc.teamcode

import com.acmerobotics.roadrunner.DualNum
import com.acmerobotics.roadrunner.Time
import com.acmerobotics.roadrunner.Twist2dDual
import com.acmerobotics.roadrunner.Vector2dDual
import com.qualcomm.robotcore.hardware.HardwareMap

class PinpointLocalizerKt(private val hardwareMap: HardwareMap) : Localizer {

    private val odo : GoBildaPinpointDriver = hardwareMap.get(GoBildaPinpointDriver::class.java, "odo");

    override fun update(): Twist2dDual<Time> {

        // ? odo uses millimeters
        // ? localizer want inches ???

        val xAndVelVelocity = DoubleArray(2);
        xAndVelVelocity[0] = odo.posX;
        xAndVelVelocity[1] = odo.velX;

        val yAndVelVelocity = DoubleArray(2);
        yAndVelVelocity[0] = odo.posY;
        yAndVelVelocity[1] = odo.velY;

        val headingAndVelocity = DoubleArray(2);
        headingAndVelocity[0] = odo.heading;
        headingAndVelocity[1] = odo.headingVelocity;

        val xDual = DualNum<Time>(xAndVelVelocity);
        val yDual = DualNum<Time>(yAndVelVelocity);
        val hDual = DualNum<Time>(headingAndVelocity);

        val td = Twist2dDual<Time>(
            Vector2dDual(xDual, yDual),
            hDual);
        return td;
    }
}