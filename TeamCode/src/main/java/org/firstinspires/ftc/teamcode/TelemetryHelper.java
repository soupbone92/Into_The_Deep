package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetryHelper {

    public static void UpdateTelemetry(Telemetry telemetry, Object... args)
    {
        if(args.length % 2 != 0) {
            // Args must be in pairs.
            throw new RuntimeException("TelemetryHelper invalid args");
        }

        for(int i = 0; i < args.length; i+=2) {
            Object valArg = args[i+1];

            if(valArg.getClass() == Double.class) {
                telemetry.addData((String) args[i], (double) args[i + 1]);
            }
            else if(valArg.getClass() == Integer.class) {
                telemetry.addData((String) args[i], (int) args[i + 1]);
            }
            else if(valArg.getClass() == String.class) {
                telemetry.addData((String) args[i], (String) args[i + 1]);
            }
            else{
                throw new RuntimeException("Invalid type to TelemetryHelper");
            }
        }

        telemetry.update();
    }
}
