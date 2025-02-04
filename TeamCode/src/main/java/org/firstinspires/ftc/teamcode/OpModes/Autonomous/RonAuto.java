package org.firstinspires.ftc.teamcode.OpModes.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.PathingMethods;
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
        PathingMethods.WhichAxis strafe = new PathingMethods.StrafeDirection(hw);
        PathingMethods.WhichAxis forward = new PathingMethods.ForwardDirection(hw);

        ElapsedTime run = new ElapsedTime();
       //PathingMethods.driveStraight(strafe,this,35, power);
        while (run.milliseconds() < 750 && !isStopRequested()) {
            hw.frontLeft.setPower(1);
            hw.frontRight.setPower(-1);
            hw.backLeft.setPower(-1);
            hw.backRight.setPower(1);
        }
    }

    private void pause() {
            sleep(250);
    }

    Hardware hw;
    double power = 0.75;
}
