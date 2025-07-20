package org.firstinspires.ftc.teamcode.Pathing;

import android.util.Log;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Interfaces.TimeSourceI;

public class PIDController {
    private double kP, kI, kD;
    private double target;
    private double integralSum;
    private double lastError;

    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.integralSum = 0;
        this.lastError = 0;
    }

    public void updateCoefficients(double kP, double kI, double kD)
    {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public void setTargetPoint(double target) {
        this.target = target;
    }

    public double calculate(double currentPoint, TimeSourceI timeSource)
    {
        long deltaTimeMs = timeSource.deltaTimeMs();
        double deltaTimeSec = Constants.millisecondsToSeconds((double)deltaTimeMs);

        double error = currentPoint-target;
        integralSum += error * deltaTimeSec;

        double output;
        if(deltaTimeSec == 0) {
            // Avoid divide by zero it deltaTime is zero.
            double P = kP * error;
            double I = kI * integralSum;
            output = P + I;
        }
        else {
            double derivative = (error - lastError) / deltaTimeSec;
            double P = kP * error;
            double I = kI * integralSum;
            double D = kD * derivative;
            output = P + I + D;
        }

        lastError = error;

        return output;
    }

    public void reset() {
        integralSum = 0;
        lastError = 0;
    }

    public void setCoeff(double kp, double ki, double kd) {
        kP = kp;
        kI = ki;
        kD = kd;
    }
}
