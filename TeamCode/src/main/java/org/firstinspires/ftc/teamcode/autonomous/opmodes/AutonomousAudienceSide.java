
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Autonomous(name="Autonomous Audience Side")
public class AutonomousAudienceSide extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    private final float maxTimeVuforia = 5;        //max time (in seconds) to look for a target
    private final float straightPower = 1f;           //power when moving
    private final float turnPower = 0.5f;               //when turning
    private final double servoHalfDistance = 0.5f;        //The distance for the jewel servo to be straight out
    private final double servoFullDistance = 1f;          //pivoted towards the jewel sensor
    private final double servoNoDistance = 0f;            //away from the jewel sensor

    @Override
    public void runOpMode() {
        //initialize robot
        Robot robot = new Robot();
        robot.init(hardwareMap);


        LynxI2cColorRangeSensor jewelColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Jewel");      //Color sensor onboard jewel arm
        LynxI2cColorRangeSensor boardColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Ground");     //sensor to read the board

        GyroSensor gyro = hardwareMap.get(GyroSensor.class, "Sensor Gyro");     //Gyro to use when turning

        gyro.calibrate();

        Servo jewelServo = hardwareMap.get(Servo.class, "Servo Jewel");     //Jewel servo

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Core Initialized");
        telemetry.update();

        //get color of the side the robot is on
        int colorR = boardColor.red();
        int colorB = boardColor.blue();
        boolean sideColor = colorR>colorB;  //true if red

        //get side of the field that the robot is on
        boolean sideField = false;  //true if on doublebox side

        //Read the vuforia vumark(tm)
        VuforiaManager vuforiaManager = new VuforiaManager(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        RelicRecoveryVuMark targetImage = RelicRecoveryVuMark.UNKNOWN;
        while (!opModeIsActive()) {                                           //while the timeout has not occued
            if (!(vuforiaManager.getvisibleTarget() == RelicRecoveryVuMark.UNKNOWN)){   //If the camera has detected anything
                telemetry.addData("Vuforia Target: ", targetImage.toString());          //Report and quit loop
                telemetry.update();
                break;
            }
            targetImage = vuforiaManager.getvisibleTarget();
        }
        telemetry.addData("Vuforia Target: ", targetImage.toString());
        telemetry.update();

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        /* ----- GAME STARTED ----- */
        if (targetImage==RelicRecoveryVuMark.UNKNOWN) {
            //rotate to the left and get the other image
            MultiMotor.bestTurn(robot,30,1,this);
            while (runtime.seconds()<=maxTimeVuforia) {                                           //while the timeout has not occued
                if (vuforiaManager.getvisibleTarget() != RelicRecoveryVuMark.UNKNOWN){   //If the camera has detected anything
                    telemetry.addData("Vuforia Target: ", targetImage.toString());          //Report and quit loop
                    telemetry.update();
                    break;
                }
                targetImage = vuforiaManager.getvisibleTarget();
            }
            telemetry.addData("Vuforia Target: ", targetImage.toString());
            telemetry.update();
            MultiMotor.bestTurn(robot,-30,1,this);
        }

        float chargeDistance = -33;
        switch(targetImage) {
            case LEFT:
                chargeDistance+=6;
                break;
            case RIGHT:
                chargeDistance-=6;
                break;
        }

        float boardMoveDistance = 8f;
        //move the servo out
        jewelServo.setPosition(servoHalfDistance);

        //move to the jewel
        MultiMotor.bestMove(robot,boardMoveDistance,straightPower/2,this);

        //knock off the jewel
        //get the color of the jewel and swing servo
        jewelServo.setPosition((jewelColor.red()>jewelColor.blue()^sideColor) /* Servo is facing the same jewel as the side */
                ? servoFullDistance:servoNoDistance);
        sleep(500);

        //wait for the servo
        MultiMotor.bestMove(robot,-boardMoveDistance-2,straightPower,this);

        //grab the cube
        robot.getLeftServo().setPosition(robot.getGlyphServoMaxPosition());
        robot.getRightServo().setPosition(robot.getGlyphServoMaxPosition());
        MultiMotor.bestMove(robot,-4,straightPower,this);

        //turn 90 degrees
        sleep(100);
        MultiMotor.bestTurn(robot,sideColor?-90:90,turnPower,this);
        //go straight for a bit
        MultiMotor.bestMove(robot,chargeDistance,straightPower,3,this);
        //turn 90 degrees
        sleep(100);
        MultiMotor.bestTurn(robot,sideColor?-90:90,turnPower,this);

        sleep(100);
        robot.getLeftServo().setPosition(robot.getGlyphServoMinPosition());
        robot.getRightServo().setPosition(1-robot.getGlyphServoMinPosition());

        //go straight for a bit
        sleep(100);
        MultiMotor.bestMove(robot,-100f,straightPower,2,this);

        sleep(100);
        MultiMotor.bestMove(robot,10,straightPower,this);

        sleep(100);
        MultiMotor.bestTurn(robot,180,turnPower,this);

        //End OpMode
        stop();
    }
}
