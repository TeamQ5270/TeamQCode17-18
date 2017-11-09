package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;

@TeleOp(name = "TeleOp Drive Code", group = "Linear Opmode")

public class TeleOpMain extends LinearOpMode {
    /* Declare OpMode members. */

    //TODO add necessary motors and servos for relic arm

    //declare motors
    private DcMotor motorLeftFront = null;
    private DcMotor motorRightFront = null;
    private DcMotor motorLeftBack = null;
    private DcMotor motorRightBack = null;
    private DcMotor motorLift = null;

    //declare servos
    private Servo leftServo = null;
    private Servo rightServo = null;

    //declare other variables
    private double deadzone = 0.1; //deadzone for joysticks

    //declare glyph claw variables
    //adjust these to adjust how far the claw opens and closes
    private static final double servoMaxPosition = 0.7;
    private static final double servoMinPosition = 0.15;
    private double position = (servoMinPosition); //start open, with servos at minimum position

    //declare general servo variables
    private static final double servoIncrement = 0.008; //adjust this to adjust the speed of all servos


    @Override
    public void runOpMode() {

        //start telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //assign appropriate motors from config to the motors
        motorLeftFront = hardwareMap.dcMotor.get("Motor Drive FL");
        motorLeftBack = hardwareMap.dcMotor.get("Motor Drive RL");
        motorRightFront = hardwareMap.dcMotor.get("Motor Drive FR");
        motorRightBack = hardwareMap.dcMotor.get("Motor Drive RR");
        motorLift = hardwareMap.dcMotor.get("Motor Glyph");

        //assign appropriate servos from config to the servos
        leftServo = hardwareMap.servo.get("Servo Glyph L");
        rightServo = hardwareMap.servo.get("Servo Glyph R");

        //assign motor directions
        //keep the directions as follows or else bad stuff happens:
        //left front: FORWARD
        //left back: FORWARD
        //right front: REVERSE
        //right back: REVERSE
        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotor.Direction.FORWARD);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);
        motorLift.setDirection(DcMotor.Direction.FORWARD);

        //set zero power behavior to BRAKE
        motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //reset lift encoder to zero, then enable lift encoder for lift limiter
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //TODO: add ability to automatically reset lift to zero during init

        //this is prototype lift reset code:
        // DigitalChannel liftResetSense;
        // liftResetSense= hardwareMap.get(DigitalChannel.class, "sensor_digital"); //declare touch sensor

        // while (liftResetSense.getState() && !isStarted()) {
        //   motorLift.setPower(1.0);
        //}

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //right stick translates
            //left stick rotates
            //mecanum code is from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example.
            //Some modifications made by Sheridan Page

            //Mecanum code starts here

            //don't run motors if stick is within the deadzone
            if (Math.abs(gamepad1.right_stick_x) > deadzone
                    || Math.abs(gamepad1.right_stick_y) > deadzone
                    || Math.abs(gamepad1.left_stick_x) > deadzone) {

                //gamepad left stick x is inverted to keep the direction of left-right translation correct
                //weird trig
                double r = Math.hypot(-gamepad1.right_stick_x, gamepad1.right_stick_y);
                double robotAngle = Math.atan2(gamepad1.right_stick_y, -gamepad1.right_stick_x) - Math.PI / 4;
                double rightX = -gamepad1.left_stick_x;
                final double v1 = r * Math.cos(robotAngle) + rightX;
                final double v2 = r * Math.sin(robotAngle) - rightX;
                final double v3 = r * Math.sin(robotAngle) + rightX;
                final double v4 = r * Math.cos(robotAngle) - rightX;

                //set calculated powers to motors
                motorLeftFront.setPower(v1);
                motorRightFront.setPower(v2);
                motorLeftBack.setPower(v3);
                motorRightBack.setPower(v4);

                //if sticks are within deadzone, set all drive motor powers to 0
            } else if (Math.abs(gamepad1.left_stick_x) < deadzone
                    && Math.abs(gamepad1.left_stick_y) < deadzone
                    && Math.abs(gamepad1.right_stick_y) < deadzone) {
                motorLeftFront.setPower(0);
                motorRightFront.setPower(0);
                motorLeftBack.setPower(0);
                motorRightBack.setPower(0);
            }

            //move lift up and down if stick is not in deadzone
            //also checks to make sure encoder values are within the safe range
            //IF WE CHANGE THE STRING OR MODIFY THE LIFT, MAKE SURE VALUES ARE RE-EVALUATED
            if (Math.abs(gamepad2.right_stick_y) > deadzone
                    && motorLift.getCurrentPosition() >= -2100
                    && motorLift.getCurrentPosition() <= -225) {

                motorLift.setPower(gamepad2.right_stick_y);

            } else {
                //Allow lift to return to the safe zone if it is at max or min
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

                //open claw arms simultaneously using ThreadedServoMovement class

                ThreadedServoMovement moveLeftServo = new ThreadedServoMovement(leftServo, position);
                ThreadedServoMovement moveRightServo = new ThreadedServoMovement(rightServo, servoMaxPosition - position);

                //start servo objects
                moveLeftServo.start();
                moveRightServo.start();

                //limit servo to allowed positions, set by servoMinPosition
                if (position >= servoMinPosition) {

                    position -= servoIncrement;
                }
            } else if (gamepad2.right_bumper) { //close claw

                //close claw arms simultaneously using ThreadedServoMovement class
                ThreadedServoMovement moveLeftServo = new ThreadedServoMovement(leftServo, position);
                ThreadedServoMovement moveRightServo = new ThreadedServoMovement(rightServo, servoMaxPosition - position);

                //start servo objects
                moveLeftServo.start();
                moveRightServo.start();

                //limit servo to allowed positions, set by servoMaxPosition
                if (position <= servoMaxPosition) {

                    position += servoIncrement;
                }
            }
            //TODO add code for relic arm

            //set telemetry TODO add telemetry for current servo positions
            telemetry.addData("Lift encoder value: ", motorLift.getCurrentPosition());
            telemetry.update();
        }
    }
}