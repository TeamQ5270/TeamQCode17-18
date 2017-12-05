package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Matthew_Modi on 12/1/2017.
 */

@Autonomous(name = "Glyph Recognition")
public class RecognitionTest extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();

    Utils utils = new Utils();

    @Override
    public void runOpMode(){

        utils.init(hardwareMap.appContext, 99);
        utils.enable();

        waitForStart();
        runtime.reset();

        while(opModeIsActive()){

        }
        utils.disable();
    }
}