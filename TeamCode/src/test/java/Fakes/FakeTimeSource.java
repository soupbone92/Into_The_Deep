package Fakes;

import org.firstinspires.ftc.teamcode.Interfaces.TimeSourceI;

public class FakeTimeSource implements TimeSourceI {
    static long time = 0;
    @Override
    public long currentTimeMillis() {
        return (time += 10);
    }
}
