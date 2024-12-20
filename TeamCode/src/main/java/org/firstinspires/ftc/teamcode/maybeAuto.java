package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

@Autonomous
public class maybeAuto extends LinearOpMode {
    Hardware hw;
    PinpointLocalizer pl;
    //Hardware and variables
    DcMotor backLeft;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor blackHook;
    DcMotor blueHook;
    DcMotor blueLift;
    public DcMotor blackLift;
    public Servo blackExtend;
    public Servo blueExtend;
    public Servo blackGrip;
    public Servo blueGrip;
    DcMotor inTake;
    Servo blueBar;
    Servo blackBar;
    Servo outtakeFlip;
    Servo blueFlap;
    Servo blackFlap;
    Servo blackIntake;
    Servo blueIntake;
    Servo Liftservo;
    ElapsedTime run = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        //All movement happen
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        blueLift = hardwareMap.dcMotor.get("blueLift");
        blackLift = hardwareMap.dcMotor.get("blackLift");
        blackExtend = hardwareMap.servo.get("blackExtend");
        blueExtend = hardwareMap.servo.get("blueExtend");
        blackGrip = hardwareMap.servo.get("blackGrip");
        blueGrip = hardwareMap.servo.get("blueGrip");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ElapsedTime run = new ElapsedTime();
        double xloc = 1;
        double yloc = 1;
        double xvol = 1;
        double yvol = 1;
        double head = 1;
        double hvol = 1;


        waitForStart();

        // Resets the timer upon starting
        run.reset();

        // While loop that moves to the left
        //PLEASE FOR THE LOVE OF GOD RONALD KEEP IT AT 0.5!
        // run.time should equal seconds
        while (xloc < 36 && !isStopRequested()) {
            frontLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backLeft.setPower(0.5);
            backRight.setPower(0.5);
            hw.imu.update();
            xloc = hw.imu.getPosX() / 25.4;
            yloc = hw.imu.getPosY()/25.4;
            xvol = hw.imu.getVelX()/25.4;
            yvol = hw.imu.getVelY()/25.4;
            head = hw.imu.getHeading();
            hvol = hw.imu.getHeadingVelocity();
            telemetry.addData("bot heading)", head);
            telemetry.addData("Position X", xloc);
            telemetry.addData("Position Y", yloc);
            telemetry.addData("Velocity X", xvol);
            telemetry.addData("Velocity Y", yvol);
            telemetry.addData("Velocity Head", hvol);


        }

    }

}
