package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Implementations.LinearOpModeImpl;
import org.firstinspires.ftc.teamcode.Implementations.SystemTimeSource;
import org.firstinspires.ftc.teamcode.Pathing.PathController;
import org.firstinspires.ftc.teamcode.RobotHardware.Hardware;
import org.firstinspires.ftc.teamcode.TelemetryHelper;

@TeleOp
public class ExampleNewPathing extends LinearOpMode {

    // This runs as soon as init it pushed.
    @Override
    public void runOpMode() throws InterruptedException {
        hw = new Hardware(hardwareMap);
        hw.imuPos.reset();
        // wait for start to be pushed.
        waitForStart();

        LinearOpModeImpl ourOp = new LinearOpModeImpl(this);

        PathController pathing = new PathController(hw, ourOp, 0.3, new SystemTimeSource());

        pause();
        // Move forward 24 inches.
        pathing.setTargetHeadingDeg(0);
        pathing.setTargetLocation(24,0);
        pathing.run();

//        pause();
//        pathing.setTargetHeadingDeg(45);
//        pathing.setTargetLocation(24,24);
//        pathing.run();
//
//        pause();
//        pathing.setTargetHeadingDeg(-90);
//        pathing.setTargetLocation(36,24);
//        pathing.run();
    }

    private void pause() {
        TelemetryHelper.UpdateTelemetry(this.telemetry, "Push B to continue...");
        while(!gamepad1.b)
            sleep(250);
    }

    Hardware hw;
    double power = 0.5;
}
