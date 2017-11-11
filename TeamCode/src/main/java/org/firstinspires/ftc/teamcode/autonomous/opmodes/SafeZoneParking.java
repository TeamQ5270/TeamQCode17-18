
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiServo;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Autonomous(name="Safe Zone Parking")
public class SafeZoneParking extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    //Vuforia manager

    //Initialize Misc

    @Override
    public void runOpMode() {
        /*
        //Create hardware devices (Using names from configuration)
        DcMotor frontLeftMotor = hardwareMap.get(DcMotor.class, "Motor Drive FL");
        DcMotor frontRightMotor = hardwareMap.get(DcMotor.class, "Motor Drive FR");
        DcMotor rearLeftMotor = hardwareMap.get(DcMotor.class, "Motor Drive BL");
        DcMotor rearRightMotor = hardwareMap.get(DcMotor.class, "Motor Drive BR");
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rearLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rearRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotor[] motors = new DcMotor[] {frontLeftMotor, frontRightMotor, rearLeftMotor, rearRightMotor};
        */

        //Define the motors on the robot
        DcMotor leftMotor;
        DcMotor rightMotor;
        //Set the motors to be actual classes
        leftMotor = hardwareMap.get(DcMotor.class, "left motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right motor");
        //Set the directions of the motors
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        DcMotor[] motors = new DcMotor[] {leftMotor, rightMotor};


        //Let user know that robot has been initialized
        telemetry.addData("Status", "Core Initialized");
        telemetry.update();

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();


        int motorSize = 40;
        //int motorSize = 20;
        MultiMotor.moveToPosition(motors, motorSize == 40 ? /*3208*/89 : 1604, 0.5f);

        //Run until stopped
        //TODO this wont stop if the user presses the stop button - make sure to check and see if the robot has to stop
        while(opModeIsActive()) {
        }
        //End OpMode
        stop();
    }
}
