package org.firstinspires.ftc.teamcode;

// Used to limit rate of change in power to motors.
public class PowerRampControler {
    public double maxChange;
    public double lastValue;
    public double lastTimeSetMs;
    public double minTimeSec = 20;

    PowerRampControler(double maxChange)
    {
        this.maxChange = maxChange;
    }

    public double getValue(double newValue)
    {
        double currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - lastTimeSetMs);

        if(deltaTime < minTimeSec) {
            // Wait at least minTimeSec seconds before changing again.
            // Increasing from 0 to 1 power will take 200 milisecs (0.2 seconds)
            // if minTimeSec = 20 and maxChange = 0.1
            return lastValue;
        }
        double delta = newValue - lastValue;
        double sign = Math.signum(delta);

        if(Math.abs(delta) > maxChange)
        {
            delta = maxChange*sign;
        }

        lastValue = lastValue + delta;
        return lastValue;
    }
}
