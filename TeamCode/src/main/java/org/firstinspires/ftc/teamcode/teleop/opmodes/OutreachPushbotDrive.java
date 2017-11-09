package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Outreach Pushbot Drive", group = "Linear Opmode")


public class OutreachPushbotDrive extends LinearOpMode {

    /* Declare OpMode members. */

    @Override
    public void runOpMode() {
        //Define the motors on the robot
        DcMotor leftMotor;
        DcMotor rightMotor;
        DcMotor lMotor;

        //Set the motors to be actual classes
        leftMotor = hardwareMap.get(DcMotor.class, "left motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right motor");
        lMotor = hardwareMap.get(DcMotor.class, "d");

        //Set the directions of the motors
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        //Set the braking of the motors
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            leftMotor.setPower(gamepad1.left_stick_y);
            rightMotor.setPower(gamepad1.right_stick_y);
            lMotor.setPower(gamepad2.left_stick_y);
        }
    }


}
