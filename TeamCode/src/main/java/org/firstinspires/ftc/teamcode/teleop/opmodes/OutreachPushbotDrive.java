package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;

@TeleOp(name = "Outreach Pushbot Drive", group = "Linear Opmode")
@Disabled

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

        DcMotor liftThing = hardwareMap.get(DcMotor.class, "liftThing");

        //Set the directions of the motors
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        liftThing.setDirection(DcMotor.Direction.FORWARD);

        //Set the braking of the motors
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftThing.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            leftMotor.setPower(gamepad1.left_stick_y);
            rightMotor.setPower(gamepad1.right_stick_y);
            liftThing.setPower(gamepad2.right_stick_y/2);
        }
    }
}