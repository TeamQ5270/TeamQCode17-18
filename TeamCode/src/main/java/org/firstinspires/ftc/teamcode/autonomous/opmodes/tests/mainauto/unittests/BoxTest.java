
package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.mainauto.unittests;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.utilities.PathBasedMovement;
import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Autonomous(name="Cryptobox Unit Test")
public class BoxTest extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    //TODO verify and correct these constants
    private final double maxTimeVuforia = 5;        //max time (in seconds) to look for a target
    private double straightPower = 0.75f;           //power when moving
    private double turnPower = 0.25f;               //when turning
    private double servoHalfDistance = 0.5f;        //The distance for the jewel servo to be straight out
    private double servoFullDistance = 1f;          //pivoted towards the jewel sensor
    private double servoNoDistance = 0f;            //away from the jewel sensor

    @Override
    public void runOpMode() {
        //initialize robot
        Robot robot = new Robot();
        robot.init(hardwareMap);

        LynxI2cColorRangeSensor jewelColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Jewel");      //Color sensor onboard jewel arm
        LynxI2cColorRangeSensor boardColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Ground");     //sensor to read the board

        GyroSensor gyro = hardwareMap.get(GyroSensor.class, "Sensor Gyro");     //Gyro to use when turning

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
        //TODO make this code a bit more readable
        VuforiaManager vuforiaManager = new VuforiaManager(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        RelicRecoveryVuMark targetImage = RelicRecoveryVuMark.UNKNOWN;
        while (!isStarted()) {                                                          //while the timeout has not occued
            targetImage = vuforiaManager.getvisibleTarget();
            if (!(vuforiaManager.getvisibleTarget() == RelicRecoveryVuMark.UNKNOWN)) {   //If the camera has detected anything
                telemetry.addData("Vuforia Target: ", targetImage.toString());          //Report and quit loop
                telemetry.update();
                break;
            }
        }
        telemetry.addData("Vuforia Target: ", targetImage.toString());

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        /* ----- GAME STARTED ----- */


        //Read the vuforia vumark(tm)
        //TODO make this code a bit more readable
        while (getRuntime()<maxTimeVuforia&&targetImage==RelicRecoveryVuMark.UNKNOWN) {                                           //while the timeout has not occued
            targetImage = vuforiaManager.getvisibleTarget();
            if (!(vuforiaManager.getvisibleTarget() == RelicRecoveryVuMark.UNKNOWN)){   //If the camera has detected anything
                telemetry.addData("Vuforia Target: ", targetImage.toString());          //Report and quit loop
                telemetry.update();
                break;
            }
        }
        telemetry.addData("Vuforia Target: ", targetImage.toString());


        //calculate and move to the position to get the glyph in the box
        //TODO verify the movement distanced for each step of the glyph movement
        double cryptoboxMoveDistance = 0.0;
        switch (targetImage) {
            case UNKNOWN:
                cryptoboxMoveDistance=23.0;
                break;
            case RIGHT:
                cryptoboxMoveDistance=4.0;
                break;
            case CENTER:
                cryptoboxMoveDistance=11.0;
                break;
            case LEFT:
                cryptoboxMoveDistance=19.0;
                break;
        }
        MultiMotor.moveToPositionAndyMark40(robot.getLeftDriveMotors(),-(float)cryptoboxMoveDistance,(float)straightPower,4);
        MultiMotor.moveToPositionAndyMark40(robot.getRightDriveMotors(),-(float)cryptoboxMoveDistance,(float)straightPower,4);
        while (MultiMotor.busyMotors(robot.getDriveMotors())) {}
        //End OpMode
        stop();
    }
}
