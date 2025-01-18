package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotHardware.Hardware;
import org.firstinspires.ftc.teamcode.TelemetryHelper;

@TeleOp
public class HighTel extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        hw = new Hardware(hardwareMap);

        initializeExtenderGripPositions();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            controlLift();
            controlExtender();
            controlGrip();
            controlResetImu();
            double powerScale = controlScalePower();
            driveFieldCentric(
                    -gamepad1.left_stick_y, gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    powerScale, hw);
            updateTelemetry();
        }
    }

    private void initializeExtenderGripPositions() {
        hw.blackGrip.setPosition(0.5);
        hw.blueGrip.setPosition(0.5);
        hw.blackExtend.setPosition(0.1);
        hw.blueExtend.setPosition(0.9);
    }

    private void controlLift() {
        int bluePos = hw.blueLift.getCurrentPosition();
        boolean downSafe = bluePos < 480;
        boolean upSafe = bluePos > -4750;
        if (gamepad2.dpad_up && upSafe) {
            hw.blueLift.setPower(-1);
            hw.blackLift.setPower(-1);
        } else if (gamepad2.dpad_down && downSafe) {
            hw.blueLift.setPower(1);
            hw.blackLift.setPower(1);
        } else {
            hw.blueLift.setPower(-0.08);
            hw.blackLift.setPower(-0.08);
        }
    }

    private void updateTelemetry() {
        hw.imuPos.update();
        double botHeading = hw.imuPos.getHeading();
        double xo = hw.imuPos.getPosX();
        double yo = hw.imuPos.getPosY();
        double xt = startEncX - hw.imuPos.getPosX();
        double yt = startEncY - hw.imuPos.getPosY();

        // Show the position of the motor on telemetry
        TelemetryHelper.UpdateTelemetry(telemetry,
                "bot heading", botHeading,
                "Position X", xo,
                "Position Y", yo,
                "X", xt,
                "Y", yt);
    }

    private void controlResetImu() {
        if (gamepad1.options) {
            hw.imuPos.reset();
        }
    }

    private void driveFieldCentric(double leftStickY, double leftStickX, double rightStickX,
                                   double powerScale, Hardware hw) {
        // Takes joystick input and sets motor power levels to drive
        // robot field centric.
        hw.imuPos.update();
        double botHeading = hw.imuPos.getHeading();

        FieldCentricPowerLevels fcPower = getFieldCentricPowerLevels(
                leftStickY, leftStickX,
                rightStickX, botHeading);

        hw.frontLeft.setPower(fcPower.frontLeftPower * powerScale);
        hw.backLeft.setPower(fcPower.backLeftPower * powerScale);
        hw.frontRight.setPower(fcPower.frontRightPower * powerScale);
        hw.backRight.setPower(fcPower.backRightPower * powerScale);
    }

    @NonNull
    private static FieldCentricPowerLevels getFieldCentricPowerLevels(
            double leftStickY, double leftStickX,
            double rightStickX, double botHeading) {

        // leftStickY forward/backward [-1.0, 1.0]
        // leftStickX strafe [-1.0, 1.0]
        // rightStickX rotate [-1.0, 1.0].

        // Rotate joystick input vectors for field centric control.

        // Apply rotation matrix inverse of botHeading (-botHeading).
        // | cos(-h) -sin(-h) | | lsx  lsy |
        // | sin(-h)  cos(-h) | | lsx  lsy |
        // h = botHeading
        // lsx = left stick x value
        // lsy = left stick y value.
        double rotatedX = leftStickX * Math.cos(-botHeading) - leftStickY * Math.sin(-botHeading);
        double rotatedY = leftStickX * Math.sin(-botHeading) + leftStickY * Math.cos(-botHeading);

        // Don't know why the rotated value of left stick x is scale by 1.1.
        // It's not in online example code.
        rotatedX = rotatedX * 1.1;

        // Normalize output power [-1.0-1.0]
        double vectorSum = Math.abs(rotatedY) + Math.abs(rotatedX) + Math.abs(rightStickX);
        double normalize = 1.0 / Math.max(vectorSum, 1.0);

        double frontLeftPower = (rotatedY + rotatedX + rightStickX) * normalize;
        double backLeftPower = (rotatedY - rotatedX + rightStickX) * normalize;
        double frontRightPower = (rotatedY - rotatedX - rightStickX) * normalize;
        double backRightPower = (rotatedY + rotatedX - rightStickX) * normalize;

        // Return normalized field centric power levels.
        return new FieldCentricPowerLevels(
                frontLeftPower, backLeftPower,
                frontRightPower, backRightPower);
    }

    // Class to hold field centric power level output from getFieldCentricPowerLevels
    private static class FieldCentricPowerLevels {
        public final double frontLeftPower;
        public final double backLeftPower;
        public final double frontRightPower;
        public final double backRightPower;

        public FieldCentricPowerLevels(double frontLeftPower, double backLeftPower,
                                       double frontRightPower, double backRightPower) {
            this.frontLeftPower = frontLeftPower;
            this.backLeftPower = backLeftPower;
            this.frontRightPower = frontRightPower;
            this.backRightPower = backRightPower;
        }
    }

    private double controlScalePower() {
        double powerScale;
        if (gamepad1.right_bumper) {
            powerScale = 0.25;
        } else if (gamepad1.left_bumper) {
            powerScale = 0.5;
        } else {
            powerScale = 1.0;
        }
        return powerScale;
    }

    private void controlGrip() {
        if (gamepad2.b) {
            hw.blueGrip.setPosition(1);
            hw.blackGrip.setPosition(0);
        } else if (gamepad2.a) {
            hw.blueGrip.setPosition(0);
            hw.blackGrip.setPosition(1);
        }
    }

    private void controlExtender() {
        if (gamepad2.dpad_left) {
            hw.blueExtend.setPosition(0.6);
            hw.blackExtend.setPosition(0.4);
        } else if (gamepad2.dpad_right) {
            hw.blueExtend.setPosition(0.4);
            hw.blackExtend.setPosition(0.6);
        }
    }

    Hardware hw;
    double startEncX = 0;
    double startEncY = 0;

}

