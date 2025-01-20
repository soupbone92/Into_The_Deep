package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.RobotHardware.Hardware;
import org.firstinspires.ftc.teamcode.Units.AngularUnit;

public class PathingMethods {

    public abstract static class WhichAxis {
        // abstract class to use same code for driveStraight
        // for X (forward/back) or Y (strafe left/right) direction.

        // Need access to the hardware to get heading and pos.
        // and to set motor power depending direction being controlled.
        final Hardware hw;

        WhichAxis(Hardware hw_in) {
            this.hw = hw_in;
        }

        // Get the current 1d position
        abstract double getPosition();

        // Set the power to move in direction
        abstract void setPower(
                double power,
                double correction);

        double getHeading() {
            return hw.imuPos.getHeading(AngularUnit.Degree);
        }

        public void update() {
            hw.imuPos.update();
        }
    }

    // Forward is X axis in Roadrunner land.
    public static class ForwardAxisDirection extends WhichAxis {

        public ForwardAxisDirection(Hardware hw) {
            super(hw);
        }

        @Override
        public double getPosition() {
            // Return pos in inches.
            return hw.imuPos.getPosX();
        }

        void setPower(
                double power,
                double correction)
        {
            hw.frontLeft.setPower(-(power + correction));
            hw.frontRight.setPower(power - correction);
            hw.backLeft.setPower(power + correction);
            hw.backRight.setPower(-(power - correction));
        }

    }

    // Strafe is Y axis in Roadrunner land.
    public static class StrafeAxisDirection extends WhichAxis {

        public StrafeAxisDirection(Hardware hw_in) {
            super(hw_in);
        }

        @Override
        public double getPosition() {
            // Return pos in inches.
            return hw.imuPos.getPosX();
        }

        void setPower(
                double power,
                double correction)
        {
            hw.frontLeft.setPower(power + correction);
            hw.frontRight.setPower(power - correction);
            hw.backLeft.setPower(power + correction);
            hw.backRight.setPower(power - correction);
        }
    }

    public static void driveStraight(
            WhichAxis direction,
            LinearOpMode opMode,
            double distanceInches,
            double nominalPower)
    {
        // Drive linear/straight distanceInches in WhichAxis.
        // WhichAxis abstracts moving either Y (forward/backward) or
        // X (strafe left/right).

        // opMode is sending telemetry and checking for stop.

        double loc = direction.getPosition();
        double initialLoc = loc;
        double initialHeading = direction.getHeading();
        double mirror = 1;  // factor to mirror if needed.

        // Mirror the problem into positive so the same while loop can be used.
        if (distanceInches < 0) {
            mirror = -1;
            distanceInches *= mirror;
            initialLoc *= mirror;
        }

        double target = initialLoc + distanceInches;
        double targetDelta = target - loc;

        while (targetDelta > 0 && !opMode.isStopRequested()) {
            direction.update();
            loc = direction.getPosition() * mirror;
            double heading = direction.getHeading();
            targetDelta = target - loc;
            double hDelta = heading - initialHeading;
            double yDelta = loc - initialLoc;

            double rampedPower = PathingMethods.powerRamp(targetDelta, yDelta, nominalPower);

            // mirror power to handle going backward.
            rampedPower *= mirror;

            TelemetryHelper.UpdateTelemetry(opMode.telemetry,
                "bot heading", heading,
                "Position Y", loc,
                "Initial y", initialLoc,
                "power", rampedPower,
                "Delta y", targetDelta,
                "Delta Heading", hDelta);

            double correction = 0;
            if (Math.abs(hDelta) > .02) {
               if(hDelta > 0)
                   correction = 0.1;
               else if (hDelta < 0)
                   correction = -0.1;
               else
                   correction = 0;
            }

            // mirror the correction.
            correction *= mirror;

            direction.setPower(
                    rampedPower,
                    correction);
        }
    }

    public static double powerRamp(double delta, double traveled, double nominalPower) {

        // computer power based on distance traveled and distance to target.
        // |u||------Steady-----|d|
        //   /-------------------\
        //  /                     \
        // /                       \
        // Ramp up during 'u' segment.
        // Ramp down during 'd' segment.
        // Hold steady between
        // Ramp up/down using y-mx+b line
        // b = 0.
        // Up and down are mirrored/symmetric so use the same
        // equation can be used with b = 0.

        nominalPower = Math.min(nominalPower, 0.5);
        double Ru = 2.0; //  distance travel to scale to nominal power.
        double Rd = 2.0; // distance left to target to begin scale down to zero/stop.
        double m = nominalPower / 2.0;
        double rampedPower = nominalPower;

        // Both us Ru distance but that may change
        // so both if/else if exist.
        if (traveled < Ru) {
            // Segment 'u'
            rampedPower = m * traveled;
            rampedPower = Math.max(rampedPower, 0.1);
        } else if (delta < Rd) {
            // Segment 'd'
            rampedPower = m * traveled;
            rampedPower = Math.max(rampedPower, 0.1);
        }

        // Power-down if at target.
        if(delta < 0)
            rampedPower = 0.0;

        return rampedPower;
    }

    public static void Rotate(Hardware hw, LinearOpMode opmode, double rotateDegrees, double power) {

        // rotateDegrees = how far to rotate from current.
        //  positive value is counter-clockwise rotation.
        // Cannot rotate more that 180 degrees.

        // If rotateDegrees is negative
        // mirror everything across zero to simplify logic.
        double mirror = 1.0;
        if (rotateDegrees < 0) {
            mirror = -1.0;
            power *= mirror;
            rotateDegrees *= -1;
        }

        hw.imuPos.update();
        double startDegrees = hw.imuPos.getHeading(AngularUnit.Degree);
        double targetDegrees = startDegrees + rotateDegrees;

        double hdelta = targetDegrees - startDegrees;

        while (hdelta > 0 && !opmode.isStopRequested()) {
            hw.frontLeft.setPower(-power);
            hw.frontRight.setPower(power);
            hw.backLeft.setPower(-power);
            hw.backRight.setPower(power);

            hw.imuPos.update();
            double currentHeading = hw.imuPos.getHeading(AngularUnit.Degree) * mirror;
            hdelta = targetDegrees - currentHeading;
        }
    }
}





