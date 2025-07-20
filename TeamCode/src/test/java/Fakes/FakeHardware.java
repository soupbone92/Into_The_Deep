package Fakes;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Interfaces.HardwareI;
import org.firstinspires.ftc.teamcode.Math.Matrix2;
import org.firstinspires.ftc.teamcode.Math.Vector2;

// Simple robot drive train simulation for unit testing.
// I'm sure a better simulation of Mecanum drive is available somewhere.
public class FakeHardware implements HardwareI {
    @Override
    public void updateImuPos() {

    }

    @Override
    public void resetImu() {

    }

    @Override
    public void resetImuHeading() {

    }

    @Override
    public Pose2D getImuPose() {
        return new Pose2D(DistanceUnit.INCH, location.x, location.y, AngleUnit.DEGREES, getImuHeading(AngleUnit.DEGREES));
    }

    @Override
    public double getImuHeading(AngleUnit unit) {
        if(unit == AngleUnit.DEGREES)
            return Math.toDegrees(Math.atan2(velocityInchesSec.y, velocityInchesSec.x));
        else
            return Math.atan2(velocityInchesSec.y, velocityInchesSec.x);
    }

    @Override
    public void setFrontLeftPower(double power) {
        frontLeftPower = power;
    }

    @Override
    public void setFrontRightPower(double power) {
        frontRightPower = power;
    }

    @Override
    public void setBackLeftPower(double power) {
        backLeftPower = power;
    }

    @Override
    public void setBackRightPower(double power) {
        backRightPower = power;
    }

    @Override
    public double getFrontLeftPower() {
        return frontLeftPower;
    }

    @Override
    public double getFrontRightPower() {
        return frontRightPower;
    }

    @Override
    public double getBackLeftPower() {
        return backLeftPower;
    }

    @Override
    public double getBackRightPower() {
        return frontRightPower;
    }

    @Override
    public void updateState(long timeMs) {
        // Update for simulation during testing.

        long deltaTimeMs = timeMs - lastTime;
        double deltaTimeSec = (double) deltaTimeMs/1000.0;
        lastTime = timeMs;

        // Update speed scaler for each wheel based on set power.
        double speedfl = maxSpeedInchSec * frontLeftPower;
        double speedfr = maxSpeedInchSec * frontRightPower;
        double speedbl = maxSpeedInchSec * backLeftPower;
        double speedbr = maxSpeedInchSec * backRightPower;

        // Update the velocity vector.  Sum of the wheel direction unit vectors
        // times speed.

        // Compute arc along wheel track circle the robot will
        // rotate given the difference in speed between right and left sides.
        Vector2 leftVelocity = Vector2.add(Vector2.mult(flWheelDv, speedfl), Vector2.mult(blWheelDv, speedfl));
        Vector2 rightVelocity = Vector2.add(Vector2.mult(frWheelDv, speedfl), Vector2.mult(brWheelDv, speedfl));
        double tangentalVelocity = leftVelocity.norm() - rightVelocity.norm();
        double arclenTraveled = tangentalVelocity * deltaTimeSec;
        // convert the length to radians
        double rad = 2 * Math.PI * (arclenTraveled / wheelTrackRadiusInches);
        double angleCurrentVelocityRad = velocityInchesSec.angle(AngleUnit.RADIANS);
        rotation.setRotation(angleCurrentVelocityRad + rad, AngleUnit.RADIANS);

        velocityInchesSec = Vector2.add(leftVelocity, rightVelocity);
        // Rotate the velocity
        rotation.mult(velocityInchesSec);

        // Update the location based on the speed and delta time.
        location = Vector2.add(location, Vector2.mult(velocityInchesSec, deltaTimeSec));
    }

    long lastTime;

    // direction and speed of simulated robot.  Inches/second.
    Vector2 velocityInchesSec = new Vector2(0, 0);

    // Current location.
    Vector2 location = new Vector2(0,0);

    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;

    // Direction unit vectors for Mecanum wheels rotation forward.
    final Vector2 flWheelDv = new Vector2(0.7071, 0.7071);
    final Vector2 frWheelDv = new Vector2(-0.7071, 0.7071);
    final Vector2 blWheelDv = new Vector2(-0.7071, 0.7071);
    final Vector2 brWheelDv = new Vector2(0.7071, 0.7071);

    double maxSpeedInchSec = 5;

    // Distance from center of robot to wheels.
    // This is also the arc length per radian used
    // to simulate rotation of the robot given different wheel speeds.
    double wheelTrackRadiusInches = 8;

    Matrix2 rotation = new Matrix2();
}
