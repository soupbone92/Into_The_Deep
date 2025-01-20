## For pinpoint
### update PinpointDrive.Params
Update xOffset yOffset in RoadRunner/PinpointDrive:45  
These measurements a from the center of rotation of the robot, not from the Pinpoint computer.

# Road Runner Quickstart

Check out the [docs](https://rr.brott.dev/docs/v1-0/tuning/).

## Quick Steps
1. Don't do ForwardPushTest.  Pinpoint handles this when accurately setting encoder resolution.
2. LateralPushTest
    1. Update lateralInPerTick in RoadRunner/MecanumDrive:67
3. ForwardRampLogger
   1. Update kS, kV in  RoadRunner/MecanumDrive:71
4. LateralRampLogger
   1. Update lateralInPerTick in RoadRunner/MecanumDrive:67
5. AngularRampLogger
   1. Update trackWidthTicks, kS, and kV in RoadRunner/MecanumDrive:67,71
6. ManualFeedforwardTuner
   1. http://192.168.43.1:8080/dash
7. ManualFeedbackTuner

# FtcDashBoard
https://acmerobotics.github.io/ftc-dashboard/gettingstarted
http://192.168.43.1:8080/dash

# Debug
adb connect 192.168.43.1

# Pull JH remote and create local branch from jh remote with RoadRunner integration.
git fetch jh
git checkout -b addRoadRunner jh/addRoadRunner





