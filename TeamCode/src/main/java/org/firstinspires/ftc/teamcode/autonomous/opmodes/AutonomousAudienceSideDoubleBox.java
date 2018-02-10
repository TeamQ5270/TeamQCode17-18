
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name="Autonomous Doublebox Side (This is the one that does NOT go for center box)")
public class AutonomousAudienceSideDoubleBox extends LinearOpMode {
    @Override
    public void runOpMode() {
        AutonomousMain.runOutsideOpMode(this,true);
    }
}
