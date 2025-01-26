package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class Vector2 {
    public double x;
    public double y;

    Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double dot(@NonNull Vector2 lh) {
        return x * lh.x + y * lh.y;
    }

    public void add(@NonNull Vector2 lh) {
        this.x += lh.x;
        this.y += lh.y;
    }

    public void scale(double gain) {
        this.x*=gain;
        this.y*=gain;
    }

    public void subtractInPlace(@NonNull Pose2D currentPose, @NonNull Vector2 targetLocation) {
        this.x = currentPose.getX(DistanceUnit.INCH) -  targetLocation.x;
        this.y = currentPose.getY(DistanceUnit.INCH) -  targetLocation.y;
    }

    public static double deltaMag(@NonNull Pose2D currentPose, @NonNull Vector2 targetLocation) {
        double dx = targetLocation.x - currentPose.getX(DistanceUnit.INCH);
        double dy = targetLocation.y - currentPose.getY(DistanceUnit.INCH);
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void normalize()
    {
        // normalize to unit vector.
        double mag = magnitude();
        this.x /= mag;
        this.y /= mag;
    }

}
