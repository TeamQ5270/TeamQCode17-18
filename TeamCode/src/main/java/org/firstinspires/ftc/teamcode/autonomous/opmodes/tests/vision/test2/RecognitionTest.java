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
            this.log("CV Status: ", "Waiting");
        }
        cv.enable();

        waitForStart();
        runtime.reset();

        while(opModeIsActive()){
            this.log("HSVC: ", Utils.toString(Utils.getHsvc()) + "\nHSVR: " + Utils.toString(Utils.getHsvr()) + "\n");
            this.log("HSVL: ", Utils.toString(Utils.getHsvLower()) + "\nHSVU: " + Utils.toString(Utils.getHsvUpper()));

            if(gamepad1.left_bumper){
                Utils.setHsvc(new double[]{Utils.getHsvc()[0] - (gamepad1.right_stick_y * 20), Utils.getHsvc()[1] + (gamepad1.left_stick_x * 20), Utils.getHsvc()[2] - (gamepad1.left_stick_y * 20)});
            }else if(gamepad1.right_bumper){
                Utils.setHsvr(new double[]{Utils.getHsvr()[0] - (gamepad1.right_stick_y * 10), Utils.getHsvr()[1] + (gamepad1.left_stick_x * 10), Utils.getHsvr()[2] - (gamepad1.left_stick_y * 10)});
            }
            sleep(100);
        }
        cv.disable();
    }

    public void log(String tag, String message){
        telemetry.addData(tag, message);
        telemetry.update();
    }
}