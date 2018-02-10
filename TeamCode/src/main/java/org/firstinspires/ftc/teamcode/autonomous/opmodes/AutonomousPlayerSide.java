
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name="Autonomous NOT AUDIENCE Side")
public class AutonomousPlayerSide extends LinearOpMode {
    @Override
    public void runOpMode() {
        AutonomousMain.runCenterOpMode(this,true); //run true, as that is the side for the non-audience
    }
}
