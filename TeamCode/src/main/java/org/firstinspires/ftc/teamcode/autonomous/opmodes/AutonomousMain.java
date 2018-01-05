
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import android.util.MutableLong;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiServo;
import org.firstinspires.ftc.teamcode.autonomous.utilities.PathBasedMovement;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

import static com.sun.tools.javac.util.LayoutCharacters.LF;

@Disabled
@Autonomous(name="Main Autonomous")
public class AutonomousMain extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    //Maximum amount of time to run the vuforia seeker (seconds)
    private final double maxTimeVuforia = 5;

    //Vuforia manager

    //Initialize Misc

    @Override
    public void runOpMode() {
        double straightPower = 0.75f;
        double turnPower = 0.25f;

        Robot robot = new Robot();
        robot.init(hardwareMap);

        LynxI2cColorRangeSensor jewelColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Jewel");
        LynxI2cColorRangeSensor boardColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Ground");

        GyroSensor gyro = hardwareMap.get(GyroSensor.class, "Sensor Gyro");

        Servo jewelServo = hardwareMap.get(Servo.class, "Servo Jewel");

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Core Initialized");
        telemetry.update();

        VuforiaManager vuforiaManager = new VuforiaManager(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        RelicRecoveryVuMark targetImage = RelicRecoveryVuMark.UNKNOWN;
        while (true&&getRuntime()<maxTimeVuforia) {
            if (!(vuforiaManager.getvisibleTarget() == RelicRecoveryVuMark.UNKNOWN)&&!isStarted()){
                telemetry.addData("Vuforia Target: ", targetImage.toString());
                telemetry.update();
                break;
            }
            targetImage = vuforiaManager.getvisibleTarget();
        }
        telemetry.addData("Vuforia Target: ", targetImage.toString());

        boolean sideColor = false; //true if red
        //get color of the side the robot is on
        int colorR = boardColor.red();
        int colorB = boardColor.blue();
        sideColor = colorR>colorB;

        //get side of the field that the robot is on
        boolean sideField = false; //true if on doublebox side

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        //go to cryptobox starting position using the movetoposition algorithm thing
        String moveA = "";
        String moveB = "";
        String move = sideField?moveB:moveA;
        PathBasedMovement.followPath(move, sideColor, robot.getLeftDriveMotors(), robot.getRightDriveMotors(), gyro);

        //calculate and move to the position to get the glyph in the box
        double cryptoboxMoveDistance = 0.0d;
        switch (targetImage) {
            case UNKNOWN:
                cryptoboxMoveDistance=0.0f;
                break;
            case CENTER:
                cryptoboxMoveDistance=0.0f;
                break;
            case LEFT:
                cryptoboxMoveDistance=0.0f;
                break;
            case RIGHT:
                cryptoboxMoveDistance=0.0f;
                break;
        }
        MultiMotor.moveToPositionAndyMark40(robot.getLeftDriveMotors(),(float)cryptoboxMoveDistance,(float)straightPower,4);
        MultiMotor.moveToPositionAndyMark40(robot.getRightDriveMotors(),(float)cryptoboxMoveDistance,(float)straightPower,4);

        //End OpMode
        stop();
    }
}
