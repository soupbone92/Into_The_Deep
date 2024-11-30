package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
@Autonomous
public final class FiguringOutAutonomousRR extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

            waitForStart();

            TrajectoryActionBuilder actionBuilder = drive.actionBuilder(beginPose);

            Actions.runBlocking(actionBuilder
            //        Build autonomous code under here:
                                    .splineTo(new Vector2d(60,0),Math.toRadians(0))
                                    .splineTo( new Vector2d(112, 68), Math.toRadians(90))
//                                    .splineTo( new Vector2d(75, 54), Math.toRadians(-175))
//                                    .splineTo(new Vector2d(0,0),Math.toRadians(0))
                                    //.splineTo(new Vector2d(40,0),Math.toRadians(0))


                    //.lineToX(85)
                            //.turnTo(Math.toRadians(84))

                            .build());
            //        Play Field is: 139/93in
            //        Robot Position, Frontright is:
        }
    }
}
