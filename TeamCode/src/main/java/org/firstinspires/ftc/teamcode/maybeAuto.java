package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

@TeleOp
public class maybeAuto extends LinearOpMode {
    Hardware hw;
    PinpointLocalizer pl;
    double mmToInch = 1.0/25.4;
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
        InitializeHardware();

        ElapsedTime run = new ElapsedTime();
        // x and y's are switched
        hw.imu.update();
        double xloc = hw.imu.getPosX() * mmToInch;
        double yloc = Math.abs(hw.imu.getPosY() * mmToInch);
        double xvol = 1;
        double yvol = 1;
        double head = hw.imu.getHeading();
        double InetH = hw.imu.getHeading();
        double initx = hw.imu.getPosX() * mmToInch;
        double inity = Math.abs(hw.imu.getPosY() * mmToInch);

        waitForStart();

        // Resets the timer upon starting
        run.reset();

        // While loop that moves to the left
        //PLEASE FOR THE LOVE OF GOD RONALD KEEP IT AT 0.5 OR LOWER!
        // run.time should equal seconds

        double power = 0.5;
        // average distance it went after break at power 0.2: 0.8953
        // average distance it went after break at power 0.5: 3.3533
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
        double hdelta = head - InetH;
        double delta = yloc-inity;

        while (!isStopRequested())
        {
            if (!gamepad1.a)
            {
                    frontLeft.setPower(0);
                    frontRight.setPower(0);
                    backLeft.setPower(0);
                    backRight.setPower(0);
                    hw.imu.update();
                    xloc = hw.imu.getPosX() / 25.4;
                    yloc = hw.imu.getPosY() / 25.4;
                    xvol = hw.imu.getVelX() / 25.4;
                    yvol = hw.imu.getVelY() / 25.4;
                    head = hw.imu.getHeading();
                    delta = yloc - inity;
                    hdelta = head - InetH;
                    double Pscale = Math.cos(hdelta);

                    telemetry.addData("bot heading)", head);
                    telemetry.addData("Position X", xloc);
                    telemetry.addData("Position Y", yloc);
                    telemetry.addData("Initial y", inity);
                    telemetry.addData("Pscale", Pscale);
                    //telemetry.addData("init y", inity);
                    telemetry.addData("Delta y", delta);
                    telemetry.addData("Delta Heading", hdelta);
                    //            telemetry.addData("Velocity X", xvol);
                    //            telemetry.addData("Velocity Y", yvol);
                    //            telemetry.addData("Velocity Head", hvol);
                    telemetry.update();
            }
            else
            {
                while (delta < 36 && !isStopRequested())
                {

                    hw.imu.update();
                    xloc = hw.imu.getPosX() * mmToInch;
                    yloc = hw.imu.getPosY() * mmToInch;
                    xvol = hw.imu.getVelX() * mmToInch;
                    yvol = hw.imu.getVelY() * mmToInch;
                    head = hw.imu.getHeading();
                    delta = yloc - inity;
                    hdelta = head - InetH;
                    double Pscale = Math.cos(hdelta);
                    power = RampUpPower(delta, 36);

                    telemetry.addData("bot heading)", head);
                    telemetry.addData("Position X", xloc);
                    telemetry.addData("Position Y", yloc);
                    telemetry.addData("Initial y", inity);
                    telemetry.addData("Pscale", Pscale);
                    telemetry.addData("power", power);
                    //telemetry.addData("init y", inity);
                    telemetry.addData("Delta y", delta);
                    telemetry.addData("Delta Heading", hdelta);
                    //            telemetry.addData("Velocity X", xvol);
                    //            telemetry.addData("Velocity Y", yvol);
                    //            telemetry.addData("Velocity Head", hvol);
                    double correction = 0;
                    telemetry.update();
                    if (Math.abs(hdelta) > .02) {
                        correction = 0.1;

                    }


                    if (hdelta > 0) {
                        frontLeft.setPower(power + correction);
                        frontRight.setPower(power - correction);
                        backLeft.setPower(power + correction);
                        backRight.setPower(power - correction);
                    } else if (hdelta < 0) {
                        frontLeft.setPower(power - correction);
                        frontRight.setPower(power + correction);
                        backLeft.setPower(power - correction);
                        backRight.setPower(power + correction);
                    } else {
                        frontLeft.setPower(power);
                        frontRight.setPower(power);
                        backLeft.setPower(power);
                        backRight.setPower(power);
                    }
                }
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
        }
    }

    private void InitializeHardware() throws InterruptedException {
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
        hw = new Hardware(hardwareMap);
        pl = new PinpointLocalizer(hardwareMap, 1);
        hw.imu.resetPosAndIMU();
        sleep(500);
        hw.imu.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.REVERSED);
        pl.odo.setEncoderResolution(8192.0/110);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private double RampUpPower(double distance, double endDistance) {
        double m = 0.5/2;
        double y = 0.01;
        double half = endDistance/2.0;
        if (distance < half) {
            y = m*distance;

            y = (y < 0.1) ? 0.1 : y;
        }
        else {
            double Rdd = endDistance*0.1;
            m = -0.5/(endDistance-Rdd);
            y = m*distance;
            y = (y > 0) ? y : 0;
            if (distance < endDistance) {
                y = Math.max(y, 0.1);
            }
        }

        return (y < 0.5) ? y : 0.5;
    }
}
