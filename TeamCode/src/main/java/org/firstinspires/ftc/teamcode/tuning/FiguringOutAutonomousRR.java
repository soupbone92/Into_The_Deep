package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
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
                           // .splineTo(new Vector2d(30, -24), Math.toRadians(90))
                             //       .splineTo(new Vector2d(62, -49), 0)




                    //.lineToX(85)
                            //.turnTo(Math.toRadians(84))

                            .build());
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("x", 3.7);
            packet.put("status", "alive");
            packet.fieldOverlay()
                    .setFill("blue")
                    .fillRect(-20, -20, 40, 40);
            FtcDashboard dashboard = FtcDashboard.getInstance();
            dashboard.sendTelemetryPacket(packet);
            //        Play Field is: 139/93in
            //        Robot Position, Frontright is:
        }
    }
}
