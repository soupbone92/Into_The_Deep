package Fakes;

import org.firstinspires.ftc.teamcode.Interfaces.TimeSourceI;

public class FakeTimeSource implements TimeSourceI {
    // Fake time source to just increments by 10ms every call.
    // Used for unit testing.
    long time;
    long lastTime;
    long total;
    boolean initialized = false;

    @Override
    public void update() {
        lastTime = time;
        time += 10;

        if(!initialized)
        {
            lastTime = time;
            initialized = true;
        }

        total += deltaTimeMs();
    }

    @Override
    public long currentTimeMillis() {
        return time;
    }

    @Override
    public long totalRunningTimeMs() {
        return total;
    }

    @Override
    public long deltaTimeMs() {
        return time - lastTime;
    }
}
