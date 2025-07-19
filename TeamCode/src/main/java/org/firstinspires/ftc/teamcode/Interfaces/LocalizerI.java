package org.firstinspires.ftc.teamcode.Interfaces;

import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;

public interface LocalizerI {
    Twist2dDual<Time> update();
}
