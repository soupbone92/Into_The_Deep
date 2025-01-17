package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.PathingMethods;
import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp
public class SickAutonomous extends LinearOpMode {
    Hardware hw;

    double endDistance = 12;
    double setPower = 0.2;
    double power = 0.5;


    @Override
    public void runOpMode() throws InterruptedException {
        hw = new Hardware(hardwareMap);
        hw.pinpoint.resetPosAndIMU();

        waitForStart();
        PathingMethods.driveStraightY(hw, this, 30, power);
        PathingMethods.Rotate(hw, this, 90, power);

    }
}
