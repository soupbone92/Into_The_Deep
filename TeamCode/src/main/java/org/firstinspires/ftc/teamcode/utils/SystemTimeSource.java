package org.firstinspires.ftc.teamcode.utils;

public class SystemTimeSource implements TimeSource {
    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
