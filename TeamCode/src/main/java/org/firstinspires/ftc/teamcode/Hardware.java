package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;


import java.util.ArrayList;

// State Drive Control

public class Hardware {
    // Are these numbers the lower and upper limits of the motors?
    int lowerLimit = 30;
    int upperLimit = 1500;
     public DcMotor frontRight;
     public DcMotor frontLeft;
     public DcMotor backLeft;
     public DcMotor backRight;
     public DcMotor blueLift;
     public DcMotor blackLift;
     public Servo blackExtend;
     public Servo blueExtend;
     public Servo blackGrip;
     public Servo blueGrip;

     public GoBildaPinpointDriver imu;
    //public IMU imu;
    public double frontLeftPower;
    public double frontRightPower;
    public double backLeftPower;
    public double backRightPower;
    public double blueHookPower;
    public double blackHookPower;
    public double denominator;
    public double rotX;
    public double rotY;

    public double botHeading;
    public double y;
    public double x;
    public double rx;
    public double power;

    public double slow;

    ArrayList<Boolean> booleanArray = new ArrayList<>();
    public int booleanIncrementer = 0;
    public boolean x2Pressed;
    public boolean a2Pressed;
    public boolean y2Pressed;

    //static Score score = Score.collapsed;
    //static DropoffServos dropoffServos = DropoffServos.Closed;
    //Intake intake = Intake.In;

    public Hardware(HardwareMap hardwareMap) throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        blueLift = hardwareMap.dcMotor.get("blueLift");
        blackLift = hardwareMap.dcMotor.get("blackLift");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        blueLift.setDirection(DcMotorSimple.Direction.REVERSE);
        blueLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blackLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        blackExtend = hardwareMap.servo.get("blackExtend");
        blueExtend = hardwareMap.servo.get("blueExtend");
        blackGrip = hardwareMap.servo.get("blackGrip");
        blueGrip = hardwareMap.servo.get("blueGrip");

        imu = hardwareMap.get(GoBildaPinpointDriver.class, "odo");
        imu.resetPosAndIMU();
       // imu = hardwareMap.get(IMU.class, "imu");
//        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.UP,
//                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        //imu.initialize(parameters);

        // Adjust the orientation parameters to match your robot
//        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.UP,
//                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
//        imu.initialize(parameters);


    }
}