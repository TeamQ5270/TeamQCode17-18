
package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.mainauto.unittests;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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

@Autonomous(name="SensorUnitTest")
@Disabled
public class SensorUnitTest extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    private final double maxTimeVuforia = 5;

    @Override
    public void runOpMode() {

        LynxI2cColorRangeSensor jewelColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Jewel");      //Color sensor onboard jewel arm
        LynxI2cColorRangeSensor boardColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Ground");     //sensor to read the board

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Core Initialized");
        telemetry.update();

        //get color of the side the robot is on
        int colorR = boardColor.red();
        int colorB = boardColor.blue();
        boolean sideColor = colorR>colorB;  //true if red


        //TODO this should either be run in a seperate thread or given more time to handle or done before the game starts
        //Read the vuforia vumark(tm)
        VuforiaManager vuforiaManager = new VuforiaManager(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        RelicRecoveryVuMark targetImage = RelicRecoveryVuMark.UNKNOWN;
        while (getRuntime()<maxTimeVuforia) {                                           //while the timeout has not occued
            if (!(vuforiaManager.getvisibleTarget() == RelicRecoveryVuMark.UNKNOWN)){   //If the camera has detected anything
                telemetry.addData("Vuforia Target: ", targetImage.toString());          //Report and quit loop
                telemetry.update();
                break;
            }
            targetImage = vuforiaManager.getvisibleTarget();
        }
        telemetry.addData("Vuforia Target: ", targetImage.toString());

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        /* ----- GAME STARTED ----- */

        while (opModeIsActive()) {
            telemetry.addData("Vuforia: ", targetImage.toString());
            telemetry.addData("Red side: ", sideColor);
            telemetry.addData("Jewel same color: ", !(jewelColor.red()>jewelColor.blue()^sideColor)); //xnor, so true when the colors are the same
            telemetry.update();
        }

        //End OpMode
        stop();
    }
}
