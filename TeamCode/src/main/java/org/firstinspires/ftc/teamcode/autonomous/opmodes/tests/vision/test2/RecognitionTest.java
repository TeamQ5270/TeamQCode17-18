package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Matthew_Modi on 12/1/2017.
 */

@Autonomous(name = "Glyph Recognition Test 2")
public class RecognitionTest extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();

    CV cv = new CV();

    @Override
    public void runOpMode(){

        cv.init(hardwareMap.appContext, 99);
        while(!Utils.getInitComplete()){

        }
        cv.enable();

        waitForStart();
        runtime.reset();

        while(opModeIsActive()){
            telemetry.addData("Mat: ", Utils.getCurrentMat().get(0, 0)[2]);
            telemetry.update();
        }
        cv.disable();
    }


}