package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.ArrayList;


@TeleOp

public class HighTel extends LinearOpMode {
    Hardware hw;
    double powerScale = 1.0;
    @Override
    public void runOpMode() throws InterruptedException {
        double powerScale = 1.0;
     hw = new Hardware(hardwareMap);

        hw.blackGrip.setPosition(0.5);
        hw.blueGrip.setPosition(0.5);
        hw.blackExtend.setPosition(0.1);
        hw.blueExtend.setPosition(0.9);
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            if (isStopRequested()) return;
            drive(-gamepad1.left_stick_y,gamepad1.left_stick_x, gamepad1.right_stick_x);

            if (gamepad1.options) {
                hw.imu.resetYaw();
            }

            int bluePos = hw.blueLift.getCurrentPosition();

            boolean downSafe = bluePos < 480;
            boolean upSafe = bluePos > -4750;
            if (gamepad2.dpad_up && upSafe){
                hw.blueLift.setPower (-1);
                hw.blackLift.setPower (-1);
            } else if (gamepad2.dpad_down && downSafe) {
                hw.blueLift.setPower (1);
                hw.blackLift.setPower (1);
            } else {
                hw.blueLift.setPower (-0.08);
                hw.blackLift.setPower (-0.08);
                hw.blueLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                hw.blackLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            if (gamepad2.dpad_left) {
                hw.blueExtend.setPosition (0.9);
                hw.blackExtend.setPosition (0.1);
            } else if (gamepad2.dpad_right){
                hw.blueExtend.setPosition (0.1);
                hw.blackExtend.setPosition (0.9);
            }
            if (gamepad2.b) {
                hw.blueGrip.setPosition (1);
                hw.blackGrip.setPosition (0);
            } else if (gamepad2.a) {
                hw.blueGrip.setPosition (0);
                hw.blackGrip.setPosition (1);
            }
            if (gamepad1.right_bumper) {
                powerScale = 0.25;
            } else if (gamepad1.left_bumper) {
                powerScale = 0.5;
            } else {
                powerScale = 1.0;
            }

            hw.booleanIncrementer = 0;

        }
    }

    //private void drive(double y, double x, double rx, GoBildaPinpointDriver imu,
    private void drive(double y, double x, double rx)
    {

        if (gamepad1.options) {
            //imu.resetPosAndIMU();
            hw.imu.resetYaw();
        }


        //double botHeading = imu.getHeading();
        double botHeading = hw.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        // Show the position of the motor on telemetry
        telemetry.addData("bot heading)", botHeading);
        int bluePos = hw.blueLift.getCurrentPosition();
        telemetry.addData("blueLift: ", bluePos);
//        telemetry.addData("x:", imu.getPosX());
//        telemetry.addData("y:", imu.getPosY());
        telemetry.update();
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
        rotX = rotX * 1.1;
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        hw.frontLeft.setPower(frontLeftPower* powerScale);
        hw.backLeft.setPower(backLeftPower* powerScale);
        hw.frontRight.setPower(frontRightPower* powerScale);
        hw.backRight.setPower(backRightPower* powerScale);
    }
}

