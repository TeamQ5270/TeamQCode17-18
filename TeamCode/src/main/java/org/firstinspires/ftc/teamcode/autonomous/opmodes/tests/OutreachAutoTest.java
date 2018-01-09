
package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;

@Disabled
@Autonomous(name="Autonomous Testing Outreach")
public class OutreachAutoTest extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    //Vuforia manager

    //Initialize Misc

    @Override
    public void runOpMode() {

        //Define the motors on the robot
        DcMotor leftMotor;
        DcMotor rightMotor;
        ModernRoboticsI2cGyro gyro;


        //Set the motors to be actual classes
        leftMotor = hardwareMap.get(DcMotor.class, "left motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right motor");
        gyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");

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

        //Move forwards
        MultiMotor.moveToPositionAndyMark40(motors, 36, 1f, 4);
        while(opModeIsActive()&&MultiMotor.busyMotors(motors)) {}
        MultiMotor.setPower(motors, 0);

        telemetry.addData("at target #1, turning", 0);
        telemetry.update();

        //Turn 90 degrees
        while (!MultiMotor.turnBetter(leftMotors, rightMotors, 90, gyro.getHeading(), 0.005f, 1)&&opModeIsActive()) {}
        //Reverse
        MultiMotor.moveToPositionAndyMark40(motors, 36, -1f, 4);
        while(opModeIsActive()&&MultiMotor.busyMotors(motors)) {}
        MultiMotor.setPower(motors, 0);

        //End OpMode
        stop();
    }
}
