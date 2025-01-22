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

        PathingMethods.WhichAxis forwardBack = new PathingMethods.ForwardDirection(hw);
        PathingMethods.WhichAxis leftRight = new PathingMethods.StrafeDirection(hw);

        // Move forward/back


//        PathingMethods.driveStraight(forwardBack, this, 18, power);
//        pause();
//        PathingMethods.driveStraight(forwardBack, this, -18, power);
//
//        // Strafe left/right
//        pause();
//        PathingMethods.driveStraight(leftRight, this, 18, power);
//        pause();
//        PathingMethods.driveStraight(leftRight, this, -18, power);
//
//        //Rotate
        pause();
        PathingMethods.Rotate(hw, this, 90, power);
        pause();
        PathingMethods.Rotate(hw, this, -90, power);
    }

    private void pause() {
        while(!gamepad1.b)
            sleep(250);
    }

    Hardware hw;
    double power = 0.5;
}
