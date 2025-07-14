import org.firstinspires.ftc.teamcode.utils.TimeSource;

public class FakeTimeSource implements TimeSource {
    static long time = 0;
    @Override
    public long currentTimeMillis() {
        return (time += 10);
    }
}
