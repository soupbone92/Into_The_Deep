package org.firstinspires.ftc.teamcode.Pathing;

public class PIDController {
    private double kP, kI, kD;
    private double target;
    private double integralSum;
    private double lastError;
    private double lastTime;
    private double lastOutput = 0;

    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.integralSum = 0;
        this.lastError = 0;
        this.lastTime = System.currentTimeMillis();
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
        double currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - lastTime);

        double error = target - currentPoint;
        integralSum += error * deltaTime;

        double output;
        if(deltaTime == 0) {
            // Avoid divide by zero it deltaTime is zero.
            output = kP * error + kI * integralSum;
        }
        else {
            double derivative = (error - lastError) / (deltaTime * milliToSec);
            output = kP * error + kI * integralSum + kD * derivative;
        }

        lastError = error;
        lastTime = currentTime;
        lastOutput = output;

        return output;
    }

    public void reset() {
        integralSum = 0;
        lastError = 0;
        lastTime = System.currentTimeMillis();
    }
}
