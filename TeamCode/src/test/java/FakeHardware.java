import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Interfaces.HardWareI;

public class FakeHardware implements HardWareI {
    @Override
    public void updateImuPos() {

    }

    @Override
    public void resetImu() {

    }

    @Override
    public void resetImuHeading() {

    }

    @Override
    public Pose2D getImuPose() {
        return null;
    }

    @Override
    public double getImuHeading(AngleUnit unit) {
        return 0;
    }

    @Override
    public void setFrontLeftPower(double power) {

    }

    @Override
    public void setFrontRightPower(double power) {

    }

    @Override
    public void setBackLeftPower(double power) {

    }

    @Override
    public void setBackRightPower(double power) {

    }

    @Override
    public double getFrontLeftPower() {
        return 0;
    }

    @Override
    public double getFrontRightPower() {
        return 0;
    }

    @Override
    public double getBackLeftPower() {
        return 0;
    }

    @Override
    public double getBackRightPower() {
        return 0;
    }
}
