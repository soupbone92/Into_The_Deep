package org.firstinspires.ftc.teamcode.Pathing.TuningOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Pathing.PathController;
import org.firstinspires.ftc.teamcode.RobotHardware.Hardware;
import org.firstinspires.ftc.teamcode.TelemetryHelper;

@TeleOp
public class Rotation extends LinearOpMode {
    double kp, ki, kd;

    @Override
    public void runOpMode() throws InterruptedException {

        Hardware hw = new Hardware(hardwareMap);
        hw.imuPos.reset();
        kp = 0.02; // Initial guess.  If 10 degrees away set power to .2?
        ki = 0.0;
        kd = 0.0;

        waitForStart();

        double targetDeg = 90.0;
        PathController pc = new PathController(hw, this, 0.5);
        pc.setTargetHeadingDeg(targetDeg);
        while (!this.isStopRequested()) {
            hw.imuPos.update();
            pc.drive();
            adjustValues();
            pc.setRotPidCoeff(kp, ki, kd);
            double heading = hw.imuPos.getHeading(AngleUnit.DEGREES);

            // 1. Start with kp.
            // 2. adjust ki for steady state error.
            // 3. adjust kd to reduce overshoot.
            pc.updateTelemetry();
//            TelemetryHelper.UpdateTelemetry(telemetry,
//                    "target", targetDeg,
//                    "heading", heading,
//                    "kp - circle", kp,
//                    "ki - square ", ki,
//                    "kd - cross", kd);

            if (gamepad1.left_bumper) {
                targetDeg -= -1;
                pc.setTargetHeadingDeg(targetDeg);
            }
        }
    }

    private void adjustValues() {
        if (gamepad1.circle) {
            kp = gamepadSet(kp);
        } else if (gamepad1.square) {
            ki = gamepadSet(ki);
        } else if (gamepad1.cross) {
            kd = gamepadSet(kd);
        }
    }

    private double gamepadSet(double value) {

        if (gamepad1.dpad_up)
            value += 0.1;
        else if (gamepad1.dpad_down)
            value -= 0.1;
        return value;
    }
}
