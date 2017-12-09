
package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;

@Disabled
@Autonomous(name="Gyro Sample Test")
public class GyroSamplingRate extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    //Vuforia manager

    //Initialize Misc

    @Override
    public void runOpMode() {

        //Define the motors on the robot
        DcMotor leftMotor;
        DcMotor rightMotor;
        GyroSensor gyro;

        //Set the motors to be actual classes
        leftMotor = hardwareMap.get(DcMotor.class, "left motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right motor");
        gyro = hardwareMap.get(GyroSensor.class, "gyro");

        //Set the directions of the motors
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        //Set the braking of the motors
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DcMotor[] motors = new DcMotor[] {leftMotor, rightMotor};
        DcMotor[] leftMotors = new DcMotor[] {leftMotor};
        DcMotor[] rightMotors = new DcMotor[] {rightMotor};

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Core Initialized");
        telemetry.update();

        MultiMotor.setOpMode(motors, DcMotor.RunMode.RUN_USING_ENCODER);

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();
        gyro.calibrate();
        while (opModeIsActive()) {
            telemetry.addData("data", gyro.getHeading());
            telemetry.update();
        }
        //End OpMode
        stop();
    }
}
