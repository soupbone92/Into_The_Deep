package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotHardware.Hardware;

public class PathingMethods {

    public abstract static class WhichAxis {
        // abstract class to use same code for driveStraight
        // for Y (forward/back) or X (strafe left/right) direction.

        // Need access to the hardware to get heading and pos.
        // and to set motor power depending direction being controlled.
        Hardware hw;

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
            return hw.imuPos.getHeading();
        }

        public void update() {
            hw.imuPos.update();
        }
    }

    public static class XAxisDirection extends WhichAxis {

        public XAxisDirection(Hardware hw) {
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

    public static class YAxisDirection extends WhichAxis {

        public YAxisDirection(Hardware hw_in) {
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
            double power)
    {
        // Drive linear/straight distanceInches in WhichAxis.
        // WhichAxis abstracts moving either Y (forward/backward) or
        // X (strafe left/right).

        // opMode is sending telemetry and checking for stop.

        power = Math.min(0.5, power);  // limiting power to 0.5 for now.

        double loc = Math.abs(direction.getPosition());
        double heading = direction.getHeading();
        double initialLoc = Math.abs(direction.getPosition());
        double initialHeading = direction.getHeading();
        double hdelta = heading - initialHeading;
        double sign = 1;

        // Mirror the problem into positive so the same while loop can be used.
        if (distanceInches < 0) {
            sign = -1;
            power *= sign;
            distanceInches *= sign;
            initialLoc *= sign;
        }

        double target = initialLoc + distanceInches;
        double targetdelta = target - loc;

        while (targetdelta > 0 && !opMode.isStopRequested()) {
            direction.update();
            loc = direction.getPosition() * sign;
            heading = direction.getHeading();
            targetdelta = target - loc;
            hdelta = heading - initialHeading;
            double yDelta = loc - initialLoc;

            power = PathingMethods.powerRamp(targetdelta, yDelta, power);
            power *= sign;

            TelemetryHelper.UpdateTelemetry(opMode.telemetry,
                "bot heading", heading,
                "Position Y", loc,
                "Initial y", initialLoc,
                "power", power,
                "Delta y", targetdelta,
                "Delta Heading", hdelta);

            double correction = 0;
            if (Math.abs(hdelta) > .02) {
               if(hdelta > 0)
                   correction = 0.1;
               else if (hdelta < 0)
                   correction = -0.1;
               else
                   correction = 0;
            }

            direction.setPower(
                    power,
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
        double Ru = 2.0;
        double m = nominalPower / 2.0;
        double rampedPower = nominalPower;

        // Both us Ru distance but that may change
        // so both if/else if exist.
        if (traveled < Ru) {
            // Segment 'u'
            rampedPower = m * traveled;
            rampedPower = Math.max(rampedPower, 0.1);
        } else if (delta < Ru) {
            // Segment 'd'
            rampedPower = m * traveled;
            rampedPower = Math.max(rampedPower, 0.1);
        }

        // Power-down if at target.
        if(delta < 0)
            rampedPower = 0.0;

        return rampedPower;
    }

    public static void Rotate(Hardware hw, LinearOpMode opmode, double targetDegrees, double power) {
        double sign = 1.0;
        if (targetDegrees < 0) {
            sign = -1.0;
            power *= sign;
        }
        double hdelta = hw.imuPos.getHeading() - targetDegrees;
        while (hdelta < 0 && !opmode.isStopRequested()) {
            hw.frontLeft.setPower(-power);
            hw.frontRight.setPower(power);
            hw.backLeft.setPower(-power);
            hw.backRight.setPower(power);

            hw.imuPos.update();
            double newHeading = hw.imuPos.getHeading();
            hdelta = newHeading - targetDegrees;
        }
    }
}





