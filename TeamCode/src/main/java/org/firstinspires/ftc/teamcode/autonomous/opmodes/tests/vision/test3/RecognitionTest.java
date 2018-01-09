package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test3.Utils;

/**
 * Created by Matthew_Modi on 12/1/2017.
 */

@Autonomous(name = "Glyph Recognition Test 3")
public class RecognitionTest extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();

    CV cv = new CV();

    @Override
    public void runOpMode(){
        cv.init(hardwareMap.appContext, 99);
        while(!Utils.getInitComplete()){
            this.log("CV Status: ", "Waiting for init.");
            this.updateLog();
        }
        cv.enable();

        while(opModeIsActive()){
            this.log("Status: ", "Running");
            this.updateLog();
        }
        cv.disable();
        Utils.setInitComplete(false);
    }


    public void log(String tag, String message){
        telemetry.addData(tag, message);
    }

    public void updateLog(){
        telemetry.update();
    }
}