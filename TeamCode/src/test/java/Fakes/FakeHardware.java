package Fakes;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Interfaces.HardwareI;
import org.firstinspires.ftc.teamcode.Math.Vector2;

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
            return Math.toDegrees(Math.atan2(velocity.y, velocity.x));
        else
            return Math.atan2(velocity.y, velocity.x);
    }

    @Override
    public void setFrontLeftPower(double power) {
        flpower = power;
    }

    @Override
    public void setFrontRightPower(double power) {
        frpower = power;
    }

    @Override
    public void setBackLeftPower(double power) {
        blpower = power;
    }

    @Override
    public void setBackRightPower(double power) {
        brpower = power;
    }

    @Override
    public double getFrontLeftPower() {
        return flpower;
    }

    @Override
    public double getFrontRightPower() {
        return frpower;
    }

    @Override
    public double getBackLeftPower() {
        return blpower;
    }

    @Override
    public double getBackRightPower() {
        return frpower;
    }

    @Override
    public void updateState(long timeMs) {
        // Update for simulation during testing.

        long deltaTime = timeMs - lastTime;

        // Update speed scaler for each wheel based on set power.
        double speedfl = maxSpeedInchSec * flpower;
        double speedfr = maxSpeedInchSec * frpower;
        double speedbl = maxSpeedInchSec * blpower;
        double speedbr = maxSpeedInchSec * brpower;

        // Update the velocity vector.  Sum of the wheel direction unit vectors
        // times speed.
        velocity = Vector2.add(
                Vector2.mult(flWheelDv, speedfl),
                Vector2.mult(frWheelDv, speedfr),
                Vector2.mult(blWheelDv, speedbl),
                Vector2.mult(brWheelDv, speedbr));

        // Update the location based on the speed and delta time.
        location = Vector2.add(location, Vector2.mult(velocity, deltaTime/1000.0));
    }

    long lastTime;

    // direction and speed of simulated robot.  Inches/second.
    Vector2 velocity;

    // Current location.
    Vector2 location;

    double flpower;
    double frpower;
    double blpower;
    double brpower;

    // Direction unit vectors for Mecanum wheels rotation forward.
    final Vector2 flWheelDv = new Vector2(0.7071, -0.7071);
    final Vector2 frWheelDv = new Vector2(0.7071, 0.7071);
    final Vector2 blWheelDv = new Vector2(0.7071, -0.7071);
    final Vector2 brWheelDv = new Vector2(0.7071, 0.7071);

    double maxSpeedInchSec = 5;
}
