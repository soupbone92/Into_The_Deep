package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PathingMethods;
import org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class RotateMove extends LinearOpMode {
    Hardware hw;

    double endDistance = 12;
    double setPower = 0.2;
    double power = 0.2;


//    RotateMove() throws InterruptedException {
//        hw = new Hardware(hardwareMap);
//    }

    @Override
    public void runOpMode() throws InterruptedException {
        hw = new Hardware(hardwareMap);
        hw.pinpoint.resetPosAndIMU();
        sleep(500);


        double InetH = Math.abs(hw.pinpoint.getHeading());


//        double power = ah.pid(ydelta, endDistance, setPower);

        waitForStart();
        hw.pinpoint.update();

        hw.frontLeft.setPower(0);
        hw.frontRight.setPower(0);
        hw.backLeft.setPower(0);
        hw.backRight.setPower(0);

        // remove this while loop at competition
        while (!gamepad1.a && !isStopRequested()) {
            telemetry.addData("Power", power);
            telemetry.update();
            if(gamepad1.dpad_right)
                power += 0.05;
            if(gamepad1.dpad_left)
                power -= 0.05;
            sleep(200);
        }

        double target = Math.toRadians(90);
        double heading = hw.pinpoint.getHeading();
        double hdelta = heading - target;

        PathingMethods.Rotate(hw, this, 90, power);

        hw.frontLeft.setPower(0);
        hw.frontRight.setPower(0);
        hw.backLeft.setPower(0);
        hw.backRight.setPower(0);
        while (!gamepad1.a && !isStopRequested()) {

            telemetry.addData("Heading Delta", hdelta);
        }

    }
}


