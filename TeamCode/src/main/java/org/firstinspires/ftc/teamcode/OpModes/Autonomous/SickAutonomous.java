package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PathingMethods;
import org.firstinspires.ftc.teamcode.RobotHardware.Hardware;

@TeleOp
public class SickAutonomous extends LinearOpMode {

    // This runs as soon as init it pushed.
    @Override
    public void runOpMode() throws InterruptedException {
        hw = new Hardware(hardwareMap);
        hw.imuPos.reset();

        // wait for start to be pushed.
        waitForStart();

        PathingMethods.WhichAxis strafe = new PathingMethods.StrafeAxisDirection(hw);
        PathingMethods.WhichAxis forward = new PathingMethods.ForwardAxisDirection(hw);

        // Move forward/back
        PathingMethods.driveStraight(forward, this, 30, power);
        PathingMethods.driveStraight(forward, this, -30, power);

        // Strafe left/right
        PathingMethods.driveStraight(strafe, this, 30, power);
        PathingMethods.driveStraight(strafe, this, -30, power);

        //Rotate
        PathingMethods.Rotate(hw, this, 90, power);
        PathingMethods.Rotate(hw, this, -90, power);
    }

    Hardware hw;
    double power = 0.5;
}
