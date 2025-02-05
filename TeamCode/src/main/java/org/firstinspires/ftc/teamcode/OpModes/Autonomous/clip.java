package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotHardware.Hardware;

@Autonomous
public class clip extends LinearOpMode {
    //Hardware and variables
    ElapsedTime run = new ElapsedTime();
    int speciminlevel = 500;

    @Override
    public void runOpMode() throws InterruptedException {
        //All movement happen
        Hardware hw = new Hardware(hardwareMap);

        waitForStart();

        // Resets the timer upon starting
        run.reset();

        hw.blueLift.setTargetPosition(-3250);
        hw.blackLift.setTargetPosition(-3250);

        hw.blackLift.setPower(.3);
        hw.blueLift.setPower(.3);
        hw.blackLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hw.blueLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        // Wait until there
        while (hw.blackLift.getCurrentPosition() > -3250 && !isStopRequested()) {
            sleep(1);
        }

        hw.blueLift.setTargetPosition(0);
        hw.blackLift.setTargetPosition(0);

        hw.blackLift.setPower(.3);
        hw.blueLift.setPower(.3);
        hw.blackLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hw.blueLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (hw.blackLift.getCurrentPosition() < 0 && !isStopRequested()) {
            sleep(1);
        }

        hw.blackLift.setPower(0);
        hw.blueLift.setPower(0);
    }
}