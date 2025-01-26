import static org.junit.Assert.assertEquals;
import org.firstinspires.ftc.teamcode.Pathing.PIDController;
import org.junit.Test;

public class TestTeamCode {

    @Test
    public void testPid()
    {
        PIDController testPid = new PIDController(1,0,0);

        double output;
        testPid.setTargetPoint(10);

        output = testPid.calculate(1);
        assertEquals(9.0, output, .01);
        output = testPid.calculate(15);
        assertEquals(-5.0, output, .01);
    }

}
