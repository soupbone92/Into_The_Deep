package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.PathController;
import org.firstinspires.ftc.teamcode.RobotHardware.Hardware;

@Autonomous
public class RonAuto extends LinearOpMode {

    // This runs as soon as init it pushed.
    @Override
    public void runOpMode() throws InterruptedException {
        hw = new Hardware(hardwareMap);
        hw.imuPos.reset();

        // wait for start to be pushed.
        waitForStart();

        PathController pathing = new PathController(hw, this, 0.5);
    }

    private void pause() {
            sleep(250);
    }

    Hardware hw;
    double power = 1;
}
