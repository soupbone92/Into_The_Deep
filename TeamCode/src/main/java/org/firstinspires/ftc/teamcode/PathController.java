package org.firstinspires.ftc.teamcode;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.RobotHardware.Hardware;

public class PathController {

    public PathController(Hardware hw, LinearOpMode opMode, double nominalPower) {
        this.hardWare = hw;
        this.opMode = opMode;
        this.nominalPower = nominalPower;
    }

    public void setNominalPower(double power)
    {
        this.nominalPower = power;
    }

    @NonNull
    private Vector2 computeMovementVector(Vector2 targetLocation) {
        lastPose = hardWare.imuPos.getPose();
        return new Vector2(0, 0);
    }

    public void setTargetHeadingDeg(double newTargetHeading) {
        // [180, -180)
        // Positive is clockwise
        targetHeadingDeg = newTargetHeading;
    }

    public void setTargetLocation(int x, int y) {
        // Drive to relative x,y (inches) position and maintain/rotate to targetHeadingDeg.
        this.targetLocation.x = x;
        this.targetLocation.y = y;
    }

    public boolean atTarget()
    {
        double closeEnoughInches = 0.25;
        Pose2D currentPose = hardWare.imuPos.getPose();
        return Vector2.deltaMag(currentPose, targetLocation) < closeEnoughInches;
    }

    public void stop()
    {
        // Turn off motors.
        // Call drive again to resume.
        hardWare.frontLeft.setPower(0.0);
        hardWare.frontRight.setPower(0.0);
        hardWare.backLeft.setPower(0.0);
        hardWare.backRight.setPower(0.0);
    }

    public void drive() {
        // Takes current heading and position
        // and adjusts motor power to drive towards target x,y and targetHeadingDeg.

        // This gets the current x,y and normalized [180,-180) heading
        lastPose = hardWare.imuPos.getPose();

        // Compute normalize vector pointing to target location.
        motionVector.subtractInPlace(lastPose, targetLocation);
        motionVector.normalize();
        deltaTargetPid.setTargetPoint(.25); // Get to delta of .25 inches.
        double deltaTargetPidValue = deltaTargetPid.calculate(Vector2.deltaMag(lastPose, targetLocation));
        motionVector.scale(nominalPower*deltaTargetPidValue);

        // Rotate the vector based on robot heading to
        // get the power vector needed to drive that direction.
        robotToFieldRotation.setRotation(lastPose.getHeading(AngleUnit.RADIANS), AngleUnit.RADIANS);
        robotRelativePowerVector = robotToFieldRotation.mult(motionVector);

        // Calculate rotation correction using PID.
        headingPid.setTargetPoint(targetHeadingDeg);
        rotationScalar = headingPid.calculate(lastPose.getHeading(AngleUnit.DEGREES));

        // Clamp magnitude of rotationScalar [-0.3, 0.3]
        rotationScalar = clampRange(rotationScalar, -0.3, 0.3);

        // Normalize output power [-1.0-1.0]
        double vectorSum = Math.abs(robotRelativePowerVector.y) + Math.abs(robotRelativePowerVector.x) + Math.abs(rotationScalar);
        double normalize = 1.0 / Math.max(vectorSum, 1.0);

        // Set normalized field centric power levels.
        frontLeftPower = (robotRelativePowerVector.y + robotRelativePowerVector.x + rotationScalar) * normalize;
        backLeftPower = (robotRelativePowerVector.y - robotRelativePowerVector.x + rotationScalar) * normalize;
        frontRightPower = (robotRelativePowerVector.y - robotRelativePowerVector.x - rotationScalar) * normalize;
        backRightPower = (robotRelativePowerVector.y + robotRelativePowerVector.x - rotationScalar) * normalize;

        // set power to the real motors.
        setMotorPower();
    }

    private void setMotorPower()
    {
        hardWare.frontLeft.setPower(powerRampControlFl.getValue(frontLeftPower));
        hardWare.frontRight.setPower(powerRampControlFr.getValue(frontRightPower));
        hardWare.backLeft.setPower(powerRampControlBl.getValue(backLeftPower));
        hardWare.backRight.setPower(powerRampControlBr.getValue(backRightPower));
    }

    private double clampRange(double value, double min, double max) {
        // clamp value into range [min, max]
        double clamped = value;
        if(value < min)
            clamped = min;
        else if(value > max)
            clamped = max;
        return clamped;
    }

    public void updateTelemetry()
    {
        TelemetryHelper.UpdateTelemetry(opMode.telemetry,
                "currentLoc x:", lastPose.getX(DistanceUnit.INCH),
                "currentLoc y:", lastPose.getY(DistanceUnit.INCH),
                "currentHeading:", lastPose.getHeading(AngleUnit.DEGREES),
                "delta Target:", Vector2.deltaMag(lastPose, targetLocation),
                "fl power", hardWare.frontLeft.getPower(),
                "fr power", hardWare.frontRight.getPower(),
                "bl power", hardWare.backLeft.getPower(),
                "br power", hardWare.backRight.getPower());
    }

    private final LinearOpMode opMode;
    private final Hardware hardWare;

    double targetHeadingDeg;
    Vector2 targetLocation;
    double rotationScalar;
    Vector2 motionVector;
    double nominalPower;

    Pose2D lastPose;

    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;
    Matrix2 robotToFieldRotation = new Matrix2();
    Vector2 robotRelativePowerVector = new Vector2(0,0);
    PIDController headingPid = new PIDController(0.1,0,0);
    PIDController deltaTargetPid = new PIDController(.5,0, 0);
    PowerRampControler powerRampControlFl = new PowerRampControler(.1);
    PowerRampControler powerRampControlFr = new PowerRampControler(.1);
    PowerRampControler powerRampControlBl = new PowerRampControler(.1);
    PowerRampControler powerRampControlBr = new PowerRampControler(.1);
}





