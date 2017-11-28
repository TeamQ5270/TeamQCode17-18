package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Matthew_Modi on 11/19/2017.
 */

@Autonomous(name = "Vision Autonomous")
public class VisionManager extends LinearOpMode {

    //Game Timer
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){

        waitForStart();
        runtime.reset();

    }
}
