package org.firstinspires.ftc.teamcode.Math;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double norm() {
        // Euclidean norm of vector represents
        // length/magnitude of vector.
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

    public void subtractInPlace(@NonNull Vector2 targetLocation, @NonNull Pose2D currentPose) {
        this.x = targetLocation.x - currentPose.getX(DistanceUnit.INCH);
        this.y = targetLocation.y - currentPose.getY(DistanceUnit.INCH);
    }

    public static double deltaNorm(@NonNull Pose2D currentPose, @NonNull Vector2 targetLocation) {
        double dx = targetLocation.x - currentPose.getX(DistanceUnit.INCH);
        double dy = targetLocation.y - currentPose.getY(DistanceUnit.INCH);
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void normalize()
    {
        // normalize to unit vector.
        // A normalized vector has a norm/length of 1.
        double mag = norm();
        if(mag == 0)
            return;
        x /= mag;
        y /= mag;
    }

    public void setZero() {
        x = 0;
        y = 0;
    }
}
