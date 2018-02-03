package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

import java.util.Timer;

import static java.lang.Thread.sleep;

/**
 * Created by John_Kesler on 2/1/2018.
 */

public class SubFunctions {
    public static float runJewel(Servo jewelServo, LynxI2cColorRangeSensor colorSensor, boolean sideColor, Robot robot, LinearOpMode opMode) throws java.lang.InterruptedException {
        jewelServo.setPosition(AutoConstants.jewelExtended);
        sleep(300);
        float moveDistance = AutoConstants.jewelMoveDistance*(colorSensor.red()<=colorSensor.blue()^sideColor?1:-1);
        MultiMotor.bestMove(robot,moveDistance,AutoConstants.straightPower/2,opMode);
        sleep(300);
        new ThreadedServoMovement(jewelServo,AutoConstants.jewelRetracted).run();
        return moveDistance;
    }
    public static RelicRecoveryVuMark getVumarkBefore(LinearOpMode opMode) {
        //Read the vuforia vumark(tm)
        VuforiaManager vuforiaManager = new VuforiaManager(opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName()));
        RelicRecoveryVuMark targetImage = RelicRecoveryVuMark.UNKNOWN;
        while (opMode.opModeIsActive()&& !opMode.isStarted()) {
            targetImage = vuforiaManager.getvisibleTarget();//while the timeout has not occued
            if (targetImage != RelicRecoveryVuMark.UNKNOWN){   //If the camera has detected anything
                opMode.telemetry.addData("Vuforia Target: ", targetImage.toString());          //Report and quit loop
                opMode.telemetry.update();
                break;
            }
            targetImage = vuforiaManager.getvisibleTarget();
        }
        opMode.telemetry.addData("Vuforia Target: ", targetImage.toString());
        opMode.telemetry.update();
        return targetImage;
    }
    public static RelicRecoveryVuMark getVumark(double seconds, LinearOpMode opMode) {
        //Read the vuforia vumark(tm)
        VuforiaManager vuforiaManager = new VuforiaManager(opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName()));
        RelicRecoveryVuMark targetImage = RelicRecoveryVuMark.UNKNOWN;
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (opMode.opModeIsActive()&&timer.seconds()<=seconds) {                                           //while the timeout has not occued
            targetImage = vuforiaManager.getvisibleTarget();
            if ((targetImage!= RelicRecoveryVuMark.UNKNOWN)){   //If the camera has detected anything
                opMode.telemetry.addData("Vuforia Target: ", targetImage.toString());          //Report and quit loop
                opMode.telemetry.update();
                break;
            }
        }
        opMode.telemetry.addData("Vuforia Target: ", targetImage.toString());
        opMode.telemetry.update();
        return targetImage;
    }
    public static float getMoveDistance(RelicRecoveryVuMark targetImage, boolean sideColor) {
        float chargeDistance = -33;
        switch(targetImage) {
            case LEFT:
                chargeDistance+=6*(sideColor?1:-1);
                break;
            case RIGHT:
                chargeDistance-=6*(sideColor?1:-1);
                break;
        }
        return chargeDistance;
    }
    public static float transferDegrees(float degrees, boolean side) {
        return side?-degrees:degrees;
    }
    public static void moveClawsIn(Robot robot) {
        //grab the cube
        robot.getLeftServo().setPosition(robot.getGlyphServoMaxPosition());
        robot.getRightServo().setPosition(robot.getGlyphServoMaxPosition());
    }
    public static void moveClawsOut(Robot robot) {
        robot.getLeftServo().setPosition(robot.getGlyphServoMinPosition());
        robot.getRightServo().setPosition(1-robot.getGlyphServoMinPosition());

    }
}
