package org.firstinspires.ftc.teamcode.TeamInterfaces;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface TelemetryI {
    Telemetry.Item addData(String caption, Object value);
}
