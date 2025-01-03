package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
@Autonomous
public final class StraightLineAutonomous extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

            waitForStart();

            TrajectoryActionBuilder actionBuilder = drive.actionBuilder(beginPose);

            Actions.runBlocking(actionBuilder
                                    .strafeTo(new Vector2d(0, -16))
                    //.splineTo(new Vector2d(30, -16), Math.toRadians(0))
                   // .turnTo(Math.toRadians(1))
                    //.splineTo(new Vector2d(96, 24), Math.toRadians(0))
                    .build());
        }
    }
}
