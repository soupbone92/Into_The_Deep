package org.firstinspires.ftc.teamcode.RoadRunner.tuning;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.reflection.ReflectionConfig;
import com.acmerobotics.roadrunner.MotorFeedforward;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.AngularRampLogger;
import com.acmerobotics.roadrunner.ftc.DeadWheelDirectionDebugger;
import com.acmerobotics.roadrunner.ftc.DriveType;
import com.acmerobotics.roadrunner.ftc.DriveView;
import com.acmerobotics.roadrunner.ftc.DriveViewFactory;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.ForwardPushTest;
import com.acmerobotics.roadrunner.ftc.ForwardRampLogger;
import com.acmerobotics.roadrunner.ftc.LateralPushTest;
import com.acmerobotics.roadrunner.ftc.LateralRampLogger;
import com.acmerobotics.roadrunner.ftc.ManualFeedforwardTuner;
import com.acmerobotics.roadrunner.ftc.MecanumMotorDirectionDebugger;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.TwoDeadWheelLocalizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TuningOpModes {
    //public static final Class<?> DRIVE_CLASS = SparkFunOTOSDrive.class;
    public static final Class<?> DRIVE_CLASS = MecanumDrive.class;

    public static final String GROUP = "quickstart";
    public static final boolean DISABLED = false;

    private TuningOpModes() {}

    private static OpModeMeta metaForClass(Class<? extends OpMode> cls) {
        return new OpModeMeta.Builder()
                .setName(cls.getSimpleName())
                .setGroup(GROUP)
                .setFlavor(OpModeMeta.Flavor.TELEOP)
                .build();
    }

    @OpModeRegistrar
    public static void register(OpModeManager manager) {
        if (DISABLED) return;

        DriveViewFactory dvf = getDriveViewFactory();

        registerOpModesWithManager(manager, dvf);

        FtcDashboard.getInstance().withConfigRoot(configRoot -> {
            for (Class<?> c : Arrays.asList(
                    AngularRampLogger.class,
                    ForwardRampLogger.class,
                    LateralRampLogger.class,
                    ManualFeedforwardTuner.class,
                    MecanumMotorDirectionDebugger.class,
                    ManualFeedbackTuner.class
            )) {
                configRoot.putVariable(c.getSimpleName(), ReflectionConfig.createVariableFromClass(c));
            }
        });
    }

    private static void registerOpModesWithManager(OpModeManager manager, DriveViewFactory dvf) {
        manager.register(metaForClass(AngularRampLogger.class), new AngularRampLogger(dvf));
        manager.register(metaForClass(ForwardPushTest.class), new ForwardPushTest(dvf));
        manager.register(metaForClass(ForwardRampLogger.class), new ForwardRampLogger(dvf));
        manager.register(metaForClass(LateralPushTest.class), new LateralPushTest(dvf));
        manager.register(metaForClass(LateralRampLogger.class), new LateralRampLogger(dvf));
        manager.register(metaForClass(ManualFeedforwardTuner.class), new ManualFeedforwardTuner(dvf));
        manager.register(metaForClass(MecanumMotorDirectionDebugger.class), new MecanumMotorDirectionDebugger(dvf));
        manager.register(metaForClass(DeadWheelDirectionDebugger.class), new DeadWheelDirectionDebugger(dvf));

        manager.register(metaForClass(ManualFeedbackTuner.class), ManualFeedbackTuner.class);
        manager.register(metaForClass(SplineTest.class), SplineTest.class);
        manager.register(metaForClass(LocalizationTest.class), LocalizationTest.class);
    }

    @NonNull
    private static DriveViewFactory getDriveViewFactory() {
        DriveViewFactory dvf;
        if (DRIVE_CLASS.equals(MecanumDrive.class)) {
            dvf = hardwareMap -> {
                MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

                List<Encoder> leftEncs = new ArrayList<>(), rightEncs = new ArrayList<>();
                List<Encoder> parEncs = new ArrayList<>(), perpEncs = new ArrayList<>();
                if (mecanumDrive.localizer instanceof MecanumDrive.DriveLocalizer) {
                    MecanumDrive.DriveLocalizer dl = (MecanumDrive.DriveLocalizer) mecanumDrive.localizer;
                    leftEncs.add(dl.leftFront);
                    leftEncs.add(dl.leftBack);
                    rightEncs.add(dl.rightFront);
                    rightEncs.add(dl.rightBack);
                } else if (mecanumDrive.localizer instanceof TwoDeadWheelLocalizer) {
                    TwoDeadWheelLocalizer dl = (TwoDeadWheelLocalizer) mecanumDrive.localizer;
                    parEncs.add(dl.par);
                    perpEncs.add(dl.perp);
                } else {
                    throw new RuntimeException("unknown localizer: " + mecanumDrive.localizer.getClass().getName());
                }

                return new DriveView(
                    DriveType.MECANUM,
                        MecanumDrive.PARAMS.inPerTick,
                        MecanumDrive.PARAMS.maxWheelVel,
                        MecanumDrive.PARAMS.minProfileAccel,
                        MecanumDrive.PARAMS.maxProfileAccel,
                        hardwareMap.getAll(LynxModule.class),
                        Arrays.asList(
                                mecanumDrive.leftFront,
                                mecanumDrive.leftBack
                        ),
                        Arrays.asList(
                                mecanumDrive.rightFront,
                                mecanumDrive.rightBack
                        ),
                        leftEncs,
                        rightEncs,
                        parEncs,
                        perpEncs,
                        mecanumDrive.lazyImu,
                        mecanumDrive.voltageSensor,
                        () -> new MotorFeedforward(MecanumDrive.PARAMS.kS,
                                MecanumDrive.PARAMS.kV / MecanumDrive.PARAMS.inPerTick,
                                MecanumDrive.PARAMS.kA / MecanumDrive.PARAMS.inPerTick)
                );
            };
        }
        else
        {
            throw new RuntimeException();
        }
        return dvf;
    }
}
