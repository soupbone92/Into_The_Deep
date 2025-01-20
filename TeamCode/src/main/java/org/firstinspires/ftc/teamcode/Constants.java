package org.firstinspires.ftc.teamcode;

public class Constants {
    static public final double mmToInch = 1.0/25.4;

    // encoderResolution is based on circumference of wheels and ticks per
    // revolution of Rev encoders.
    static public final double wheelDiameterMm = 35.0;
    static public final double wheelCircumference = 2.0*Math.PI*(wheelDiameterMm/2.0);
    static public final double revTicksPerRotation = 8192.0;
    static public final double encoderResolution = revTicksPerRotation/wheelCircumference;
}
