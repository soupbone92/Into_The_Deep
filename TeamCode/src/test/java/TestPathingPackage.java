import static org.junit.Assert.assertTrue;

import org.firstinspires.ftc.teamcode.FakeOpMode;
import org.firstinspires.ftc.teamcode.Interfaces.OpModeI;
import org.firstinspires.ftc.teamcode.Pathing.PathController;
import org.junit.Test;

import Fakes.FakeHardware;
import Fakes.FakeLog;
import Fakes.FakeTimeSource;

public class TestPathingPackage {

    @Test
    public void TestDrive()
    {
        FakeHardware hw = new FakeHardware();
        hw.resetImu();
        OpModeI opMode = new FakeOpMode();
        PathController pathing = new PathController(hw, opMode, 0.9, new FakeTimeSource(), new FakeLog());
        // Move forward 24 inches.
        pathing.setTargetHeadingDeg(0);
        pathing.setTargetLocation(0,24);
        boolean result = pathing.run(10);
        assertTrue("pathing.run", result);
    }
}
