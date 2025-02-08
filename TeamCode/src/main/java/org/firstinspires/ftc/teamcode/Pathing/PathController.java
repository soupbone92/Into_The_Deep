package org.firstinspires.ftc.teamcode.Pathing;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Math.Matrix2;
import org.firstinspires.ftc.teamcode.Math.Vector2;
import org.firstinspires.ftc.teamcode.RobotHardware.Hardware;
import org.firstinspires.ftc.teamcode.TelemetryHelper;

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

    public boolean compareHeading(double rh, double lh)
    {
        // compare heading to 1/4 degree.
        return Math.abs(lh - rh) < .25;
    }

    public boolean notAtTarget()
    {
        // Test is not at target heading and loc.
        double closeEnoughInches = 0.25;
        Pose2D currentPose = hardWare.imuPos.getPose();
        boolean atLoc = Vector2.deltaNorm(currentPose, targetLocation) < closeEnoughInches;
        boolean atHeading = compareHeading(currentPose.getHeading(AngleUnit.DEGREES), targetHeadingDeg);
        return !(atLoc && atHeading);
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
        hardWare.imuPos.update();
        lastPose = hardWare.imuPos.getPose();

        // Compute normalize vector pointing to target location.
        motionVector.subtractInPlace(targetLocation,lastPose);
        motionVector.normalize();
        double deltaTargetPidValue = deltaTargetPid.calculate(Vector2.deltaNorm(lastPose, targetLocation));
        deltaTargetPidValue = clampRange(deltaTargetPidValue, -0.9, 0.9);
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
        vectorSum = Math.abs(robotRelativePowerVector.y) + Math.abs(robotRelativePowerVector.x) + Math.abs(rotationScalar);
        normalize = 1.0 / Math.max(vectorSum, 1.0);

        // TESTING set motion vector to zero to only test rotation
        // Comment out to test motion/direction
        robotRelativePowerVector.setZero();
        // End testing

        // Set normalized field centric power levels.
        // Clamp to valid range [-1,1] for motors.
        frontLeftPower = motorClamp((robotRelativePowerVector.y + robotRelativePowerVector.x + rotationScalar) * normalize);
        backLeftPower = motorClamp((robotRelativePowerVector.y - robotRelativePowerVector.x + rotationScalar) * normalize);
        frontRightPower = motorClamp((robotRelativePowerVector.y - robotRelativePowerVector.x - rotationScalar) * normalize);
        backRightPower = motorClamp((robotRelativePowerVector.y + robotRelativePowerVector.x - rotationScalar) * normalize);

        // set power to the real motors.
        setMotorPower();
    }

    private double motorClamp(double v) {
        // Clamp value to range valid for motors.
        return clampRange(v, -1.0, 1.0);
    }

    double vectorSum;
    double normalize;

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
                "getheading:", hardWare.imuPos.getHeading(AngleUnit.DEGREES),
                "delta Target:", Vector2.deltaNorm(lastPose, targetLocation),
                "rotscaler:", rotationScalar,
                "vecsum:", vectorSum,
                "normalize:", normalize,
                "fl power", hardWare.frontLeft.getPower(),
                "fr power", hardWare.frontRight.getPower(),
                "bl power", hardWare.backLeft.getPower(),
                "br power", hardWare.backRight.getPower());
    }

    private final LinearOpMode opMode;
    private final Hardware hardWare;

    double targetHeadingDeg;
    Vector2 targetLocation = new Vector2(0,0);
    double rotationScalar;
    Vector2 motionVector = new Vector2(0,0);
    double nominalPower;

    Pose2D lastPose = new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);

    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;
    Matrix2 robotToFieldRotation = new Matrix2();
    Vector2 robotRelativePowerVector = new Vector2(0,0);
    PIDController headingPid = new PIDController(0.3,0,0);
    PIDController deltaTargetPid = new PIDController(.5,0, 0);
    PowerRampController powerRampControlFl = new PowerRampController(.1);
    PowerRampController powerRampControlFr = new PowerRampController(.1);
    PowerRampController powerRampControlBl = new PowerRampController(.1);
    PowerRampController powerRampControlBr = new PowerRampController(.1);

    public void run() {
        // Runs until stop or at target.
        while(notAtTarget() && !opMode.isStopRequested()) {
            drive();
            updateTelemetry();
        }
    }

    public void setRotPidCoeff(double kp, double ki, double kd) {
        this.headingPid.setCoeff(kp,ki,kd);
    }
}





