import org.firstinspires.ftc.teamcode.Pathing.PathController;
import org.junit.Test;

import Fakes.FakeHardware;
import Fakes.FakeTimeSource;

public class TestPathingPackage {

    @Test
    public void TestDrive()
    {
        FakeHardware hw = new FakeHardware();
        hw.resetImu();
        PathController pathing = new PathController(hw, null, 0.3, new FakeTimeSource());
        // Move forward 24 inches.
        pathing.setTargetHeadingDeg(0);
        pathing.setTargetLocation(24,0);
        pathing.run();
    }
}
