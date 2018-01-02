package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;
import org.firstinspires.ftc.teamcode.teleop.opmodes.utilities.TeleOpRobot;

@TeleOp(name = "Refactored TeleOp Drive Code IN PROGRESS", group = "Linear Opmode")
@Disabled
public class NewTeleopInProgress extends LinearOpMode {

    //make TeleOpRobot object
    private TeleOpRobot robot = new TeleOpRobot();

    //these variables get rid of magic numbers - you're welcome, Matthew
    //indicies of motor values returned by robot.getDriveMotors()
    //left front -- right front -- left back -- right back
    private int mLeftFrontIdx = 0;
    private int mRightFrontIdx = 1;
    private int mLeftBackIdx = 2;
    private int mRightBackIdx = 3;

    //more un-magicked numbers
    private int motorZeroPower = 0;
    private int joystickZero = 0;
    private int weirdFourInMecanumCalcs = 4;

    @Override
    public void runOpMode() {

        //initialize robot
        robot.init(hardwareMap);

        //start telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            mecanumDrive();
            glyphLift();
            glyphClaw();
        }


    }

    public void mecanumDrive() {
        if (Math.abs(gamepad1.left_stick_x) > robot.getDeadzone()
                || Math.abs(gamepad1.left_stick_y) > robot.getDeadzone()
                || Math.abs(gamepad1.right_stick_x) > robot.getDeadzone()) {

            //weird trig
            double r = Math.hypot(-gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x)
                    - Math.PI / weirdFourInMecanumCalcs;
            double rightX = -gamepad1.right_stick_x;
            final double v1 = r * Math.cos(robotAngle) + rightX;
            final double v2 = r * Math.sin(robotAngle) - rightX;
            final double v3 = r * Math.sin(robotAngle) + rightX;
            final double v4 = r * Math.cos(robotAngle) - rightX;

            //set calculated powers to motors
            robot.getDriveMotors()[mLeftFrontIdx].setPower(v1);
            robot.getDriveMotors()[mRightFrontIdx].setPower(v2);
            robot.getDriveMotors()[mLeftBackIdx].setPower(v3);
            robot.getDriveMotors()[mRightBackIdx].setPower(v4);

            //if sticks are within deadzone, set all drive motor powers to 0

        } else if (Math.abs(gamepad1.left_stick_x) < robot.getDeadzone()
                && Math.abs(gamepad1.left_stick_y) < robot.getDeadzone()
                && Math.abs(gamepad1.right_stick_y) < robot.getDeadzone()) {
            robot.getDriveMotors()[mLeftFrontIdx].setPower(motorZeroPower);
            robot.getDriveMotors()[mRightFrontIdx].setPower(motorZeroPower);
            robot.getDriveMotors()[mLeftBackIdx].setPower(motorZeroPower);
            robot.getDriveMotors()[mRightBackIdx].setPower(motorZeroPower);
        }
    }

    public void glyphLift() {
        if (Math.abs(gamepad2.right_stick_y) > robot.getDeadzone()
                && robot.getMotorLift().getCurrentPosition() >= robot.getLiftTop()
                && robot.getMotorLift().getCurrentPosition() <= robot.getLiftBottom()) {

            robot.getMotorLift().setPower(gamepad2.right_stick_y);

        } else {
            //Allow lift to return to the safe zone if it is at max or min
            if (gamepad2.right_stick_y > 0
                    && robot.getMotorLift().getCurrentPosition() <= robot.getLiftTop()) {
                robot.getMotorLift().setPower(gamepad2.right_stick_y);

            } else if (gamepad2.right_stick_y < 0
                    && robot.getMotorLift().getCurrentPosition() >= robot.getLiftBottom()) {

                robot.getMotorLift().setPower(gamepad2.right_stick_y);

            } else {
                robot.getMotorLift().setPower(0);
            }
        }
    }

    public void glyphClaw() {
        if (gamepad2.left_bumper) { //open claw

            //open claw arms simultaneously using ThreadedServoMovement class

            ThreadedServoMovement moveLeftServo = new ThreadedServoMovement(robot.getLeftServo(), robot.getClawPosition());
            ThreadedServoMovement moveRightServo = new ThreadedServoMovement(robot.getRightServo(), robot.getServoMaxPosition() - position);

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
    }
}
