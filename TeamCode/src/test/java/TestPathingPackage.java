import org.firstinspires.ftc.teamcode.Pathing.PathController;
import org.junit.Test;

public class TestPathingPackage {

    @Test
    public void TestDrive()
    {
        FakeHardware hw = new FakeHardware();
        hw.resetImu();
        // wait for start to be pushed.

        PathController pathing = new PathController(hw, null, 0.3);

        // Move forward 24 inches.
        pathing.setTargetHeadingDeg(0);
        pathing.setTargetLocation(24,0);
        pathing.run();
    }
}
