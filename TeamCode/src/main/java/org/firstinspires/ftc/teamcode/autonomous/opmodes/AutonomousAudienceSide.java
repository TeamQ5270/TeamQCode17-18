
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name="Autonomous Audience Side")
public class AutonomousAudienceSide extends LinearOpMode {
    @Override
    public void runOpMode() {
        AutonomousMain.runCenterOpMode(this,false); //run false, as that is the side for the audience
    }
}
