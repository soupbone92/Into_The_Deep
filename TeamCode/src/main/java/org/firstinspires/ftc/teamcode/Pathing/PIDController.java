package org.firstinspires.ftc.teamcode.Pathing;

import android.util.Log;

import org.firstinspires.ftc.teamcode.utils.TimeSource;

public class PIDController {
    private double kP, kI, kD;
    private double target;
    private double integralSum;
    private double lastError;
    private long lastTime;
    private TimeSource timeSource;

    public PIDController(double kP, double kI, double kD, TimeSource ts) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.integralSum = 0;
        this.lastError = 0;
        timeSource = ts;
        this.lastTime = timeSource.currentTimeMillis();

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

    public double calculate(double currentPoint)
    {

        double milliToSec = 1.0/1000.0;
        long currentTime = timeSource.currentTimeMillis();
        long deltaTime = (currentTime - lastTime);

        double error = target - currentPoint;
        integralSum += error * deltaTime;


        double output;
        if(deltaTime == 0) {
            // Avoid divide by zero it deltaTime is zero.
            double P = kP * error;
            double I = kI * integralSum;
            output = P + I;
        }
        else {
            double derivative = (error - lastError) / (deltaTime * milliToSec);
            double P = kP * error;
            double I = kI * integralSum;
            double D = kD * derivative;
            output = P + I + D;
        }

        lastError = error;
        lastTime = currentTime;

        Log.d("Calculate", String.format("Target: %f", this.target));
        Log.d("Calculate", String.format("CurrentPoint: %f", currentPoint));
        Log.d("Calculate", String.format("Output: %f", output));

        return output;
    }

    public void reset() {
        integralSum = 0;
        lastError = 0;
        lastTime = System.currentTimeMillis();
    }

    public void setCoeff(double kp, double ki, double kd) {
        kP = kp;
        kI = ki;
        kD = kd;
    }
}
