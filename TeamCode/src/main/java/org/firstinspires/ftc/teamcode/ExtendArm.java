package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ExtendArm implements Action {

    private DcMotorEx motor;

    public ExtendArm(HardwareMap hwmap)
    {
        motor = hwmap.get(DcMotorEx.class, "armMotor");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        return false;
    }

    @Override
    public void preview(@NonNull Canvas fieldOverlay) {
        Action.super.preview(fieldOverlay);
    }
}
