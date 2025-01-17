package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// Please remember to alocate ex:
// hw = new Hardware(hardwareMap);

public class PathingMethods {
    Hardware hw;

    public static double correction(double headingDelta) {
        double correction = 0.0;
        if (Math.abs(headingDelta) > 0.02) {
            correction = 0.1;
        }
        return correction;
    }

    public static void driveStraightY(Hardware hw, LinearOpMode op, double distanceInches, double power)
    {
        power = Math.min(0.5, power);
        double yloc = Math.abs(hw.pinpoint.getPosY() * hw.mmToInch);
        double head = hw.pinpoint.getHeading();
        double inity = Math.abs(hw.pinpoint.getPosY() * hw.mmToInch);
        double initHeader = hw.pinpoint.getHeading();
        double hdelta = head - initHeader;
        double correction = 0;
        double targetdelta = distanceInches - yloc;

        double sign = 1;

        if (distanceInches < 0) {
            sign = -1;
            power *= sign;
            distanceInches *= sign;
            inity *= sign;
        }
        double target = inity + distanceInches;
        targetdelta = target - yloc;
        while (targetdelta > 0 && !op.isStopRequested())
        {
            hw.pinpoint.update();
            yloc = hw.pinpoint.getPosY() * hw.mmToInch * sign;
            head = hw.pinpoint.getHeading();
            targetdelta = target - yloc;
            hdelta = head - initHeader;
            double yDelta = yloc - inity;
            power = PathingMethods.powerRamp(targetdelta, yDelta, power);
            power *= sign;
            op.telemetry.addData("bot heading)", head);
            op.telemetry.addData("Position Y", yloc);
            op.telemetry.addData("Initial y", inity);
            op.telemetry.addData("power", power);
            op.telemetry.addData("Delta y", targetdelta);
            op.telemetry.addData("Delta Heading", hdelta);

            correction = 0;
            op.telemetry.update();
            if (Math.abs(hdelta) > .02) {
                correction = 0.1;
            }

            if (hdelta > 0) {
                hw.frontLeft.setPower(power + correction);
                hw.frontRight.setPower(power - correction);
                hw.backLeft.setPower(power + correction);
                hw.backRight.setPower(power - correction);
            } else if (hdelta < 0) {
                hw.frontLeft.setPower(power - correction);
                hw.frontRight.setPower(power + correction);
                hw.backLeft.setPower(power - correction);
                hw.backRight.setPower(power + correction);
            } else {
                hw.frontLeft.setPower(power);
                hw.frontRight.setPower(power);
                hw.backLeft.setPower(power);
                hw.backRight.setPower(power);
            }
        }
    }
    public static void driveStraightX(Hardware hw, LinearOpMode op, double distanceInches, double power)
    {
        power = Math.min(0.5, power);
        double xloc = Math.abs(hw.pinpoint.getPosX() * hw.mmToInch);
        double head = hw.pinpoint.getHeading();
        double initx = Math.abs(hw.pinpoint.getPosY() * hw.mmToInch);
        double initHeader = hw.pinpoint.getHeading();
        double hdelta = head - initHeader;
        double correction = 0;
        double targetdelta = distanceInches - xloc;

        double sign = 1;

        if (distanceInches < 0) {
            sign = -1;
            power *= sign;
            distanceInches *= sign;
            initx *= sign;
        }
        double target = initx + distanceInches;
        targetdelta = target - xloc;
        while (targetdelta > 0 && !op.isStopRequested())
        {
            hw.pinpoint.update();
            xloc = hw.pinpoint.getPosY() * hw.mmToInch * sign;
            head = hw.pinpoint.getHeading();
            targetdelta = target - xloc;
            hdelta = head - initHeader;
            double xDelta = xloc - initx;
            power = PathingMethods.powerRamp(targetdelta, xDelta, power);
            power *= sign;
            op.telemetry.addData("bot heading)", head);
            op.telemetry.addData("Position Y", xloc);
            op.telemetry.addData("Initial y", initx);
            op.telemetry.addData("power", power);
            op.telemetry.addData("Delta y", targetdelta);
            op.telemetry.addData("Delta Heading", hdelta);

            correction = 0;
            op.telemetry.update();
            if (Math.abs(hdelta) > .02) {
                correction = 0.1;
            }

            if (hdelta > 0) {
                hw.frontLeft.setPower(-power + correction);
                hw.frontRight.setPower(power - correction);
                hw.backLeft.setPower(power + correction);
                hw.backRight.setPower(-power - correction);
            } else if (hdelta < 0) {
                hw.frontLeft.setPower(-power - correction);
                hw.frontRight.setPower(power + correction);
                hw.backLeft.setPower(power - correction);
                hw.backRight.setPower(-power + correction);
            } else {
                hw.frontLeft.setPower(-power);
                hw.frontRight.setPower(power);
                hw.backLeft.setPower(power);
                hw.backRight.setPower(-power);
            }
        }
    }


    public static double powerRamp(double delta, double traveled, double nominalPower) {
        nominalPower = Math.min(nominalPower, 0.5);
        double Ru = 2.0;
        double m = nominalPower / 2.0;
        double y = nominalPower;
        if (traveled < Ru) {
            y = m * traveled;
            y = Math.max(y, 0.1);
        }
        else if (delta < Ru) {
            y = m * traveled;
            y = Math.max(y, 0.1);


        }
        return Math.max(y, 0.1);


    }


    public static double Rotate(Hardware hw, LinearOpMode lo, double targetDegrees, double power) {
        double sign = 1.0;
        if (targetDegrees < 0) {
            sign = -1.0;
            power *= sign;
        }
        double target = Math.toRadians(targetDegrees);
        double hdelta = hw.pinpoint.getHeading() - target;
        while (hdelta < 0 && !lo.isStopRequested()) {
            hw.frontLeft.setPower(-power);
            hw.frontRight.setPower(power);
            hw.backLeft.setPower(-power);
            hw.backRight.setPower(power);

            hw.pinpoint.update();
            double newHeading = hw.pinpoint.getHeading();
            hdelta = newHeading - target;

        }
        return hdelta;
    }



}





