package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test3;

import com.disnodeteam.dogecv.detectors.JewelDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test3.Utils;

/**
 * Created by Matthew_Modi on 12/1/2017.
 */
@Disabled
@Autonomous(name = "Glyph Recognition Test 3")
public class RecognitionTest extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();

    CV cv = new CV();

    private double servoHalfDistance = 0.5f;        //The distance for the jewel servo to be straight out
    private double servoFullDistance = 1f;          //pivoted towards the jewel sensor
    private double servoNoDistance = 0f;            //away from the jewel sensorh


    Robot robot = new Robot();

    Servo jewelServo;     //Jewel servo

    @Override
    public void runOpMode(){

        robot.init(hardwareMap);
        jewelServo = hardwareMap.get(Servo.class, "Servo Jewel");
        cv.init(hardwareMap.appContext, 99);
        while(!Utils.getInitComplete()){
            this.log("CV Status: ", "Waiting for init.");
            this.updateLog();
        }
        cv.enable();
        this.log("Status: ", "Running");
        this.updateLog();

        jewelServo.setPosition(servoHalfDistance);

        int i = 0;
        while(opModeIsActive()){
            i++;
            if(i>10) {
                jewelServo.setPosition(Utils.getJewelOrder() == JewelDetector.JewelOrder.RED_BLUE ? servoFullDistance : servoNoDistance);
            }
            this.log("Jewel Order: ", Utils.getJewelOrder().toString());
            this.updateLog();
            sleep(100);
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