# Road Runner Quickstart

Check out the [docs](https://rr.brott.dev/docs/v1-0/tuning/).

## For pinpoint
### update PinpointDrive.Params
Update xOffset yOffset in RoadRunner/PinpointDrive:45  
    These measurements a from the center of rotation of the robot, not from the Pinpoint computer.

# FtcDashBoard
https://acmerobotics.github.io/ftc-dashboard/gettingstarted
http://192.168.43.1:8080/dash

# Debug
adb connect 192.168.43.1

# Pull JH remote and create local branch from jh remote with RoadRunner integration.
git fetch jh
git checkout -b addRoadRunner jh/addRoadRunner



