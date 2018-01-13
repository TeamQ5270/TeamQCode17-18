
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
import org.firstinspires.ftc.teamcode.autonomous.utilities.PathBasedMovement;
import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Autonomous(name="JerryRiggedAutonomous")
public class JerryRiggedAuto extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    //TODO verify and correct these constants
    private final double maxTimeVuforia = 5;        //max time (in seconds) to look for a target
    private double straightPower = 0.5f;           //power when moving
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

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        /* ----- GAME STARTED ----- */

        //get the jewel and knock it off
        float boardMoveDistance = 11.5f;
        //move the servo out
        jewelServo.setPosition(servoHalfDistance);
        sleep(500);
        //move to the jewel
        MultiMotor.moveToPositionAndyMark40(robot.getLeftDriveMotors(),boardMoveDistance,(float)straightPower,4);
        MultiMotor.moveToPositionAndyMark40(robot.getRightDriveMotors(),boardMoveDistance,(float)straightPower,4);
        while (MultiMotor.busyMotors(robot.getDriveMotors())) {}
        MultiMotor.setPower(robot.getDriveMotors(), 0);
        sleep(500);
        //knock off the jewel
        //get the color of the jewel and swing servo
        jewelServo.setPosition((jewelColor.red()>jewelColor.blue()^sideColor) /* Servo is facing the same jewel as the side */
                ? servoFullDistance:servoNoDistance);
        sleep(1500);
        //wait for the servo
        MultiMotor.moveToPositionAndyMark40(robot.getLeftDriveMotors(),-boardMoveDistance,(float)straightPower,4);
        MultiMotor.moveToPositionAndyMark40(robot.getRightDriveMotors(),-boardMoveDistance,(float)straightPower,4);
        while (MultiMotor.busyMotors(robot.getDriveMotors())) {}
        MultiMotor.setPower(robot.getDriveMotors(), 0);
        sleep(500);

        //End OpMode
        stop();
    }
}
