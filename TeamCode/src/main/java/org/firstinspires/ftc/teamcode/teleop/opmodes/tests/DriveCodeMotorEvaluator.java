package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "DriveCodeMotorEvaluator 1", group = "Linear Opmode")


public class DriveCodeMotorEvaluator extends LinearOpMode {

    /* Declare OpMode members. */

    private DcMotor motorLeftFront = null;
    private DcMotor motorRightFront = null;
    private DcMotor motorLeftBack = null;
    private DcMotor motorRightBack = null;

    private double deadzone = 0.1; //deadzone for joysticks


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        motorLeftFront = hardwareMap.dcMotor.get("Motor Drive FL");
        motorLeftBack = hardwareMap.dcMotor.get("Motor Drive FR");
        motorRightFront = hardwareMap.dcMotor.get("Motor Drive RL");
        motorRightBack = hardwareMap.dcMotor.get("Motor Drive RR");

        //keep the directions as follows or else bad stuff happens:
        //left front: FORWARD
        //left back: FORWARD
        //right front: REVERSE
        //right back: REVERSE
        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotor.Direction.FORWARD);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);


        motorLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //set zero power behavior to BRAKE
        motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //don't run motors if stick is within the deadzone
            if (Math.abs(gamepad1.left_stick_x) > deadzone
                    || Math.abs(gamepad1.left_stick_y) > deadzone
                    || Math.abs(gamepad1.right_stick_x) > deadzone) {

                double r = Math.hypot(-gamepad1.left_stick_x, gamepad1.left_stick_y);
                double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
                double rightX = -gamepad1.right_stick_x;

                final double v1 = r * Math.cos(robotAngle) + rightX;
                final double v2 = r * Math.sin(robotAngle) - rightX;
                final double v3 = r * Math.sin(robotAngle) + rightX;
                final double v4 = r * Math.cos(robotAngle) - rightX;

                motorLeftFront.setPower(v1);
                motorRightFront.setPower(v2);
                motorLeftBack.setPower(v3);
                motorRightBack.setPower(v4);

            } else if (Math.abs(gamepad1.left_stick_x) < deadzone
                    && Math.abs(gamepad1.left_stick_y) < deadzone
                    && Math.abs(gamepad1.right_stick_y) < deadzone) {
                motorLeftFront.setPower(0);
                motorRightFront.setPower(0);
                motorLeftBack.setPower(0);
                motorRightBack.setPower(0);
            }


            telemetry.addData("Left Front: ", motorLeftFront.getCurrentPosition());
            telemetry.addData("Left Back: ", motorLeftBack.getCurrentPosition());
            telemetry.addData("Right Front: ", motorRightFront.getCurrentPosition());
            telemetry.addData("Right Back: ", motorRightBack.getCurrentPosition());

            if (gamepad1.a) {
                motorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                motorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            telemetry.update();

        }


    }


}
