package org.firstinspires.ftc.teamcode.Implementations;

import org.firstinspires.ftc.teamcode.Interfaces.TimeSourceI;

public class SystemTimeSource implements TimeSourceI {
    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
