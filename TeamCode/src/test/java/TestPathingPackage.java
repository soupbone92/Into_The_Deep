import org.firstinspires.ftc.teamcode.FakeOpMode;
import org.firstinspires.ftc.teamcode.Interfaces.OpModeI;
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
        OpModeI opmode = new FakeOpMode();
        PathController pathing = new PathController(hw, opmode, 0.3, new FakeTimeSource());
        // Move forward 24 inches.
        pathing.setTargetHeadingDeg(0);
        pathing.setTargetLocation(24,0);
        pathing.run();
    }
}
