
package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.mainauto.unittests;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.utilities.PathBasedMovement;
import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Autonomous(name="ServoUnitTest")
@Disabled
public class ServoUnitTest extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    private double servoHalfDistance = 0.5f;        //The distance for the jewel servo to be straight out
    private double servoFullDistance = 1f;          //pivoted towards the jewel sensor
    private double servoNoDistance = 0f;            //away from the jewel sensor

    @Override
    public void runOpMode() {
        Servo jewelServo = hardwareMap.get(Servo.class, "Servo Jewel");     //Jewel servo
        LynxI2cColorRangeSensor jewelColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Jewel");      //Color sensor onboard jewel arm

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Core Initialized");
        telemetry.update();

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        /* ----- GAME STARTED ----- */

        while (opModeIsActive()) {
            telemetry.addData("NoDistance", servoNoDistance);
            telemetry.update();
            jewelServo.setPosition(servoNoDistance);
            sleep(1000);

            telemetry.addData("HalfDistance", servoHalfDistance);
            telemetry.update();
            jewelServo.setPosition(servoHalfDistance);
            sleep(1000);

            telemetry.addData("FullDistance", servoFullDistance);
            telemetry.update();
            jewelServo.setPosition(servoFullDistance);
            sleep(1000);

            telemetry.addData("ServoDirection (ASSUMING RED SIDE OF FIELD)", !(jewelColor.red()>jewelColor.blue()^true)); //assume robot is on red side
            telemetry.update();
            jewelServo.setPosition(!(jewelColor.red()>jewelColor.blue()^true) /* Servo is facing the same jewel as the side */
                    ? servoFullDistance:servoNoDistance);
            sleep(3000);
        }

        //End OpMode
        stop();
    }
}
