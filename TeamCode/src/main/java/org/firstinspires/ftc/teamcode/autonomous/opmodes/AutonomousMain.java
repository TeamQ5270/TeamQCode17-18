
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.utilities.PathBasedMovement;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Disabled
@Autonomous(name="Main Autonomous")
public class AutonomousMain extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    //TODO verify and correct these constants
    private final double maxTimeVuforia = 5;
    private double straightPower = 0.75f;
    private double turnPower = 0.25f;
    private double servoHalfDistance = 0.5f;
    private double servoFullDistance = 1f;
    private double servoNoDistance = 0f;

    //Vuforia manager

    //Initialize Misc

    @Override
    public void runOpMode() {

        Robot robot = new Robot();
        robot.init(hardwareMap);

        LynxI2cColorRangeSensor jewelColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Jewel");
        LynxI2cColorRangeSensor boardColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Ground");

        GyroSensor gyro = hardwareMap.get(GyroSensor.class, "Sensor Gyro");

        Servo jewelServo = hardwareMap.get(Servo.class, "Servo Jewel");

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Core Initialized");
        telemetry.update();

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

        //TODO make this less fragile
        VuforiaManager vuforiaManager = new VuforiaManager(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        RelicRecoveryVuMark targetImage = RelicRecoveryVuMark.UNKNOWN;
        while (getRuntime()<maxTimeVuforia) {
            if (!(vuforiaManager.getvisibleTarget() == RelicRecoveryVuMark.UNKNOWN)){
                telemetry.addData("Vuforia Target: ", targetImage.toString());
                telemetry.update();
                break;
            }
            targetImage = vuforiaManager.getvisibleTarget();
        }
        telemetry.addData("Vuforia Target: ", targetImage.toString());

        //get the jewel and knock it off
        //move the servo out
        jewelServo.setPosition(servoHalfDistance);
        //move to the jewel
        //TODO get the correct move distance
        double jewelMoveDistance = 16.5;
        MultiMotor.moveToPositionAndyMark40(robot.getLeftDriveMotors(),(float)jewelMoveDistance,(float)straightPower,4);
        MultiMotor.moveToPositionAndyMark40(robot.getRightDriveMotors(),(float)jewelMoveDistance,-(float)straightPower,4);
        //knock off the jewel
        //get the color of the jewel and swing servo
        jewelServo.setPosition(jewelColor.red()>jewelColor.blue()&&sideColor /* Servo is facing the same jewel as the side */
                ? servoFullDistance:servoNoDistance);
        //wait for the servo
        jewelServo.setPosition(servoNoDistance);

        //go to cryptobox starting position using the movetoposition algorithm thing
        //TODO get the correct movement paths for the robot
        String moveA = "";
        String moveB = "";
        String move = sideField?moveB:moveA;
        PathBasedMovement.followPath(move, sideColor, robot.getLeftDriveMotors(), robot.getRightDriveMotors(), gyro);

        //calculate and move to the position to get the glyph in the box
        //TODO veryify the movement distanced for each step of the glyph movement
        double cryptoboxMoveDistance = 0.0;
        switch (targetImage) {
            case UNKNOWN:
                cryptoboxMoveDistance=23.0;
                break;
            case CENTER:
                cryptoboxMoveDistance=4.0;
                break;
            case LEFT:
                cryptoboxMoveDistance=10.0;
                break;
            case RIGHT:
                cryptoboxMoveDistance=19.0;
                break;
        }
        double cryptoboxMoveDistanceOut=23.0-cryptoboxMoveDistance;
        MultiMotor.moveToPositionAndyMark40(robot.getLeftDriveMotors(),(float)cryptoboxMoveDistance,(float)straightPower,4);
        MultiMotor.moveToPositionAndyMark40(robot.getRightDriveMotors(),(float)cryptoboxMoveDistance,-(float)straightPower,4);

        //put the glyph in the box
        //strafe to the side
        //TODO get the correct value for this
        double strafeDistanceOutOfCryptoBox = 6;
        MultiMotor.moveToPositionAndyMark40(robot.getDiagonalRight(),(float)strafeDistanceOutOfCryptoBox,(float)straightPower,4);
        MultiMotor.moveToPositionAndyMark40(robot.getDiagonalLeft(),(float)strafeDistanceOutOfCryptoBox,-(float)straightPower,4);

        //TODO get turning into the cryptobox and deploying the glyph

        //End OpMode
        stop();
    }
}
