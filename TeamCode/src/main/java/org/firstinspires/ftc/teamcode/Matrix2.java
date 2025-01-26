package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

// Simple class to represent matrix for 2d vectors.
public class Matrix2 {
    // Row major 2x3 matrix
    public double[] coeff = new double[6];

    public Matrix2() {
        // Identity matrix
        coeff[0] = 1;
        coeff[4] = 1;
    }

    public Matrix2(double angle, AngleUnit units) {
        // Create a rotationScalar matrix.
        setRotation(angle, units);
    }

    public Matrix2(double rotscale1, double rotscale2, double translationX,
                   double rotscale3, double rotscale4, double translationY) {
        coeff[0] = rotscale1;
        coeff[1] = rotscale2;
        coeff[2] = translationX;
        coeff[3] = rotscale3;
        coeff[4] = rotscale4;
        coeff[5] = translationY;
    }

    public void setRotation(double angle, AngleUnit units) {
        // turn this into a rotation matrix that rotates angle.
        if (units == AngleUnit.DEGREES) {
            angle = Math.toRadians(angle);
        }

        coeff[0] = Math.cos(angle);
        coeff[1] = -Math.cos(angle);
        coeff[2] = 0;
        coeff[3] = Math.sin(angle);
        coeff[4] = Math.cos(angle);
        coeff[5] = 0;
    }

    public void multInPlace(Vector2 vec) {
        vec.x = vec.x * coeff[0] + vec.y * coeff[1] + coeff[2];
        vec.y = vec.y * coeff[3] + vec.y * coeff[4] + coeff[5];
    }

    public Vector2 mult(Vector2 vec) {
        return new Vector2(
                vec.x * coeff[0] + vec.y * coeff[1] + coeff[2],
                vec.y * coeff[3] + vec.y * coeff[4] + coeff[5]);
    }

//    public final static Matrix2 rotccw90 = new Matrix2(90, AngleUnit.DEGREES);
//    public final static Matrix2 rotcw90 = new Matrix2(-90, AngleUnit.DEGREES);
}
