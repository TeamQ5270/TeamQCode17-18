package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp Drive Code", group = "Linear Opmode")


public class driveCode extends LinearOpMode {

    /* Declare OpMode members. */

    private DcMotor motorLeftFront = null;
    private DcMotor motorRightFront = null;
    private DcMotor motorLeftBack = null;
    private DcMotor motorRightBack = null;
    private DcMotor motorLift = null;

    private Servo leftServo = null;
    private Servo rightServo = null;

    private double deadzone = 0.1; //deadzone for joysticks

    private static final double servoMaxPosition = 1.0;
    private static final double servoMinPosition = 0.0;
    private static final double servoIncrement = 0.02; //adjust this to adjust the speed of the claw
    private double position = (servoMinPosition); //start either open or closed - need to find out which

    private boolean clawCycle = false; //flag for alternation of which servo arm to move

    private boolean previouslyRaised = false;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        motorLeftFront = hardwareMap.dcMotor.get("FL Drive");
        motorLeftBack = hardwareMap.dcMotor.get("BL Drive");
        motorRightFront = hardwareMap.dcMotor.get("FR Drive");
        motorRightBack = hardwareMap.dcMotor.get("BR Drive");
        motorLift = hardwareMap.dcMotor.get("Riser Lift");

        leftServo = hardwareMap.servo.get("Servo Lift L");
        rightServo = hardwareMap.servo.get("Servo Lift R");

        //keep the directions as follows or else bad stuff happens:
        //left front: FORWARD
        //left back: FORWARD
        //right front: REVERSE
        //right back: REVERSE
        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotor.Direction.FORWARD);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);

        //set zero power behavior to BRAKE
        motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorLift.setDirection(DcMotor.Direction.FORWARD);
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //left stick translates
            //right stick rotates
            //this code is from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example.
            //Some modifications made by Sheridan Page

            //gamepad left stick x is inverted to keep the direction of left-right translation correct

            //don't run motors if stick is within the deadzone
            if (Math.abs(gamepad1.left_stick_x) > deadzone
                    || Math.abs(gamepad1.left_stick_y) > deadzone
                    || Math.abs(gamepad1.right_stick_x) > deadzone) {

                //weird trig
                double r = Math.hypot(-gamepad1.left_stick_x, gamepad1.left_stick_y);
                double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
                double rightX = -gamepad1.right_stick_x;
                final double v1 = r * Math.cos(robotAngle) + rightX;
                final double v2 = r * Math.sin(robotAngle) - rightX;
                final double v3 = r * Math.sin(robotAngle) + rightX;
                final double v4 = r * Math.cos(robotAngle) - rightX;

                //set calculated powers to motors
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


            //move lift up and down
            if (Math.abs(gamepad2.right_stick_y) > deadzone
                    && motorLift.getCurrentPosition() >= -2100
                    && motorLift.getCurrentPosition() <= -275) {

                motorLift.setPower(gamepad2.right_stick_y);

            } else {

                if (gamepad2.right_stick_y > 0
                        && motorLift.getCurrentPosition() <= -2000) {
                    motorLift.setPower(gamepad2.right_stick_y);
                } else if (gamepad2.right_stick_y < 0
                        && motorLift.getCurrentPosition() >= -275) {


                    motorLift.setPower(gamepad2.right_stick_y);


                } else {
                    motorLift.setPower(0);

                }


            }

            if (gamepad2.left_bumper) { //open claw

                //open claw arms simultaneously - the servos have to be cycled, apparently
                //using clawCycle to switch between servos
                if (clawCycle) {

                    leftServo.setPosition(position);
                    clawCycle = !clawCycle;

                } else if (!clawCycle) {

                    rightServo.setPosition(servoMaxPosition - position);
                    clawCycle = !clawCycle;

                }

                position -= servoIncrement;

            } else if (gamepad2.right_bumper) { //close claw


                //same as above but for closing
                if (clawCycle) {

                    leftServo.setPosition(position);
                    clawCycle = !clawCycle;

                } else if (!clawCycle) {

                    rightServo.setPosition(servoMaxPosition - position);
                    clawCycle = !clawCycle;

                }

                position += servoIncrement;

            }
            telemetry.addData("Lift encoder value: ", motorLift.getCurrentPosition());
            telemetry.update();

        }


    }


}
