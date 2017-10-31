package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;

@TeleOp(name = "Multithreaded Servo Test OpMode", group = "Linear Opmode")


public class ThreadedServoDriveCodeTest extends LinearOpMode {

    /* Declare OpMode members. */

    private DcMotor motorLeftFront = null;
    private DcMotor motorRightFront = null;
    private DcMotor motorLeftBack = null;
    private DcMotor motorRightBack = null;
    private DcMotor motorLift = null;

    private Servo leftServo = null;
    private Servo rightServo = null;

    private double deadzone = 0.1; //deadzone for joysticks

    private static final double servoMaxPosition = 0.7;
    private static final double servoMinPosition = 0.15;
    private static final double servoIncrement = 0.008; //adjust this to adjust the speed of the claw
    private double position = (servoMinPosition); //start either open or closed - need to find out which

    //private boolean clawCycle = false; //flag for alternation of which servo arm to move



    private boolean previouslyRaised = false;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        motorLeftFront = hardwareMap.dcMotor.get("Motor Drive FL");
        motorLeftBack = hardwareMap.dcMotor.get("Motor Drive RL");
        motorRightFront = hardwareMap.dcMotor.get("Motor Drive FR");
        motorRightBack = hardwareMap.dcMotor.get("Motor Drive RR");
        motorLift = hardwareMap.dcMotor.get("Motor Glyph");

        leftServo = hardwareMap.servo.get("Servo Glyph L");
        rightServo = hardwareMap.servo.get("Servo Glyph R");

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

        //reset lift


        // DigitalChannel liftResetSense;
        // liftResetSense= hardwareMap.get(DigitalChannel.class, "sensor_digital"); //declare touch sensor

        // while (liftResetSense.getState() && !isStarted()) {
        //   motorLift.setPower(1.0);
        //}


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
                    && motorLift.getCurrentPosition() <= -225) {

                motorLift.setPower(gamepad2.right_stick_y);

            } else {

                if (gamepad2.right_stick_y > 0
                        && motorLift.getCurrentPosition() <= -2000) {
                    motorLift.setPower(gamepad2.right_stick_y);
                } else if (gamepad2.right_stick_y < 0
                        && motorLift.getCurrentPosition() >= -225) {


                    motorLift.setPower(gamepad2.right_stick_y);


                } else {
                    motorLift.setPower(0);

                }
            }

            if (gamepad2.left_bumper) { //open claw

                //open claw arms simultaneously - the servos have to be cycled, apparently
                //using clawCycle to switch between servos

                ThreadedServoMovement moveLeftServo = new ThreadedServoMovement(leftServo, position);
                ThreadedServoMovement moveRightServo = new ThreadedServoMovement(rightServo, servoMaxPosition - position);

                if (position >= servoMinPosition) {

                    position -= servoIncrement;
                }
            } else if (gamepad2.right_bumper) { //close claw

                ThreadedServoMovement moveLeftServo = new ThreadedServoMovement(leftServo, position);
                ThreadedServoMovement moveRightServo = new ThreadedServoMovement(rightServo, servoMaxPosition - position);


                if (position <= servoMaxPosition) {

                    position += servoIncrement;

                }
            }
            telemetry.addData("Lift encoder value: ", motorLift.getCurrentPosition());
            telemetry.update();
        }
    }
}