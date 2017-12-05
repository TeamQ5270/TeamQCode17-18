package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;

@TeleOp(name = "Outreach Pushbot Drive", group = "Linear Opmode")


public class OutreachPushbotDrive extends LinearOpMode {

    /* Declare OpMode members. */

    @Override
    public void runOpMode() {
        float grabSpeed = 0.05f;

        //Define the motors on the robot
        DcMotor leftMotor;
        DcMotor rightMotor;
        DcMotor rGrab;
        DcMotor lGrab;
        //Set the motors to be actual classes
        leftMotor = hardwareMap.get(DcMotor.class, "left motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right motor");
        rGrab = hardwareMap.get(DcMotor.class, "right grabber");
        lGrab = hardwareMap.get(DcMotor.class, "left grabber");

        //Set the directions of the motors
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        rGrab.setDirection(DcMotor.Direction.FORWARD);
        lGrab.setDirection(DcMotor.Direction.REVERSE);

        //Set the braking of the motors
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rGrab.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lGrab.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DcMotor[] grabbers = new DcMotor[] {rGrab, lGrab};

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            leftMotor.setPower(gamepad1.left_stick_y);
            rightMotor.setPower(gamepad1.right_stick_y);
            if (gamepad2.left_trigger>0.05f) {
                MultiMotor.setPower(grabbers, grabSpeed);
            }
            else if (gamepad2.right_trigger>0.05f) {
                MultiMotor.setPower(grabbers, -grabSpeed);
            }
            else {
                MultiMotor.setPower(grabbers, 0);
            }
        }
    }
}