package org.firstinspires.ftc.teamcode.Implementations;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Interfaces.OpModeI;

public class LinearOpModeImpl implements OpModeI {
    LinearOpMode opMode;

    public LinearOpModeImpl(LinearOpMode opModeIn)
    {
        opMode = opModeIn;
    }
    @Override
    public boolean isStopRequested() {
        return opMode.isStopRequested();
    }

    @Override
    public Telemetry getTelemetry() {
        return opMode.telemetry;
    }

    @Override
    public void updateState(long time_ms) {

    }
}
