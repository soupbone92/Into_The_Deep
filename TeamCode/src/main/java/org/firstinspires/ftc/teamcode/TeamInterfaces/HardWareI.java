package org.firstinspires.ftc.teamcode.TeamInterfaces;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public interface HardWareI {
    void updateImuPos();
    Pose2D getImuPose();
    double getImuHeading(AngleUnit unit);
    void setFrontLeftPower(double power);
    void setFrontRightPower(double power);
    void setBackLeftPower(double power);
    void setBackRightPower(double power);
    double getFrontLeftPower();
    double getFrontRightPower();
    double getBackLeftPower();
    double getBackRightPower();

}
