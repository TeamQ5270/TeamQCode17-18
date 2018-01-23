package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;

@TeleOp(name = "Refactored TeleOp Drive Code IN PROGRESS", group = "Linear Opmode")

public class NewTeleopInProgress extends LinearOpMode {

    //make Robot object
    private Robot robot = new Robot();

    //these variables get rid of magic numbers - you're welcome, Matthew
    //indices of motor values returned by robot.getDriveMotors()
    //left front -- right front -- left back -- right back
    private final int mLeftFrontIdx = 0;
    private final int mRightFrontIdx = 1;
    private final int mLeftBackIdx = 2;
    private final int mRightBackIdx = 3;

    //more un-magicked numbers
    private final double motorZeroPower = 0.0;
    private final double joystickZero = 0.0;
    private final int weirdFourInMecanumCalcs = 4;

    private boolean encoderLimEnabled = true;

    @Override
    public void runOpMode() {

        //initialize robot
        robot.init(hardwareMap);

        //start telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //wait until driver presses PLAY
        waitForStart();

        zeroGlyphClaw();

        //run until OpMode is stopped
        while (opModeIsActive()) {
            controlRobot();

            telemetry.addData("Lift position: ", robot.getMotorLift().getCurrentPosition());
            telemetry.update();

            telemetry.addData("Lift servo position: ", robot.getClawPosition());
        }
    }

    private void controlRobot() {
        if (Math.abs(gamepad1.left_stick_x) > robot.getDeadzone()
                || Math.abs(gamepad1.left_stick_y) > robot.getDeadzone()
                || Math.abs(gamepad1.right_stick_x) > robot.getDeadzone()) {

            mecanumDrive();

        } else if (Math.abs(gamepad1.left_stick_x) < robot.getDeadzone()
                && Math.abs(gamepad1.left_stick_y) < robot.getDeadzone()
                && Math.abs(gamepad1.right_stick_y) < robot.getDeadzone()) {

            mecanumDriveStop();
        }

        if (Math.abs(gamepad2.right_stick_y) > robot.getDeadzone()) {

            glyphLift();

        } else if (Math.abs(gamepad2.right_stick_y) <= robot.getDeadzone()) {
            robot.getMotorLift().setPower(motorZeroPower);
        } else {
            robot.getMotorLift().setPower(motorZeroPower);
        }

        if (Math.abs(gamepad2.right_stick_y) <= robot.getDeadzone()) {
            robot.getMotorLift().setPower(motorZeroPower);
        }

        if (gamepad2.left_bumper) {
            openGlyphClaw();
        } else if (gamepad2.right_bumper) {
            closeGlyphClaw();
        }

        if (Math.abs(gamepad2.right_trigger) > robot.getDeadzone()
                || Math.abs(gamepad2.left_trigger) > robot.getDeadzone()) {
            glyphClawTriggers();
        }

        if (Math.abs(gamepad2.left_stick_y) > robot.getDeadzone()) {
            relicArm();
        } else if (Math.abs(gamepad2.left_stick_y) <= robot.getDeadzone()) {
            robot.getMotorRelicArm().setPower(motorZeroPower);
        } else {
            robot.getMotorRelicArm().setPower(motorZeroPower);
        }

        if (Math.abs(gamepad2.left_stick_y) <= robot.getDeadzone()) {
            robot.getMotorRelicArm().setPower(motorZeroPower);
        }


        if (gamepad2.a || gamepad2.y) {
            rotateClaw();
        }



        if (gamepad2.x) {
            //relic claw servo
        }

        if (gamepad2.b) {
            robot.getRelicClawServo().setPosition(1);        }

        /*if (gamepad2.dpad_up) {
            encoderLimEnabled = false;
            robot.getMotorLift().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getMotorLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            encoderLimEnabled = true;
        }*/


    }

    private void mecanumDrive() {

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

    }

    private void mecanumDriveStop() {
        robot.getDriveMotors()[mLeftFrontIdx].setPower(motorZeroPower);
        robot.getDriveMotors()[mRightFrontIdx].setPower(motorZeroPower);
        robot.getDriveMotors()[mLeftBackIdx].setPower(motorZeroPower);
        robot.getDriveMotors()[mRightBackIdx].setPower(motorZeroPower);
    }

    private void glyphLift() {


        if (Math.abs(gamepad2.right_stick_y) > robot.getDeadzone()
                && robot.getMotorLift().getCurrentPosition() >= robot.getLiftTop()
                && robot.getMotorLift().getCurrentPosition() <= robot.getLiftBottom()
                && encoderLimEnabled) {

            robot.getMotorLift().setPower(gamepad2.right_stick_y);

        } else {
            //Allow lift to return to the safe zone if it is at max or min
            if (gamepad2.right_stick_y > joystickZero
                    && robot.getMotorLift().getCurrentPosition() <= robot.getLiftTop()) {
                robot.getMotorLift().setPower(gamepad2.right_stick_y);

            } else if (gamepad2.right_stick_y < joystickZero
                    && robot.getMotorLift().getCurrentPosition() >= robot.getLiftBottom()) {
                robot.getMotorLift().setPower(gamepad2.right_stick_y);
            } else {
                robot.getMotorLift().setPower(motorZeroPower);
            }
        }
    }


    private void openGlyphClaw() {


        //open claw arms simultaneously using ThreadedServoMovement class

        ThreadedServoMovement moveLeftServo = new ThreadedServoMovement
                (robot.getLeftServo(), robot.getClawPosition());
        ThreadedServoMovement moveRightServo = new ThreadedServoMovement
                (robot.getRightServo(), 1.0 - robot.getClawPosition());

        //start servo objects
        moveLeftServo.start();
        moveRightServo.start();

        //limit servo to allowed positions, set by servoMinPosition
        if (robot.getClawPosition() >= robot.getGlyphServoMinPosition()) {
            robot.setClawPosition(robot.getClawPosition() - robot.getServoIncrement());
        }
    }

    private void closeGlyphClaw() {
        //close claw arms simultaneously using ThreadedServoMovement class
        ThreadedServoMovement moveLeftServo = new ThreadedServoMovement
                (robot.getLeftServo(), robot.getClawPosition());
        ThreadedServoMovement moveRightServo = new ThreadedServoMovement
                (robot.getRightServo(), 1.0 - robot.getClawPosition());

        //start servo objects
        moveLeftServo.start();
        moveRightServo.start();

        //limit servo to allowed positions, set by servoMaxPosition
        if (robot.getClawPosition() <= robot.getGlyphServoMaxPosition()) {

            robot.setClawPosition(robot.getClawPosition() + robot.getServoIncrement());
        }
    }

    private void zeroGlyphClaw() {
        robot.getLeftServo().setPosition(0); //sorry for magic numbers
        robot.getRightServo().setPosition(1);
    }

    private void relicArm() {

        if (robot.getMotorRelicArm().getCurrentPosition() >= robot.getRelicLimitExtended()
                && robot.getMotorRelicArm().getCurrentPosition() <= robot.getRelicLimitRetracted()) {
            robot.getMotorRelicArm().setPower(gamepad2.left_stick_y);
        } else {
            //Allow lift to return to the safe zone if it is at max or min
            if (gamepad2.left_stick_y > joystickZero
                    && robot.getMotorRelicArm().getCurrentPosition() <= robot.getRelicLimitExtended()) {
                robot.getMotorRelicArm().setPower(gamepad2.left_stick_y);

            } else if (gamepad2.left_stick_y < joystickZero
                    && robot.getMotorRelicArm().getCurrentPosition() >= robot.getRelicLimitRetracted()) {

                robot.getMotorRelicArm().setPower(gamepad2.left_stick_y);

            } else {
                robot.getMotorRelicArm().setPower(motorZeroPower);
            }
        }
    }

    private void rotateClaw() {

        /*robot.getRelicRotatorServo().setPosition(robot.getRelicRotatorServoPosition());
        if (robot.getRelicRotatorServoPosition() >= robot.getRelicRotatorServoMinPosition()
                && gamepad2.y) {
            robot.setRelicRotatorServoPosition(robot.getRelicRotatorServoPosition() + robot.getServoIncrement());
        } else if (robot.getRelicRotatorServoPosition() <= robot.getRelicRotatorServoMaxPosition()
                && gamepad2.a) {
            robot.setRelicRotatorServoPosition(robot.getRelicRotatorServoPosition() - robot.getServoIncrement());
        }*/

        if (gamepad2.a) {
            robot.getRelicRotatorServo().setPosition(1);

        } else if (gamepad2.y) {
            robot.getRelicRotatorServo().setPosition(0);
        }

    }

/*    private void rotateClawCR() {
        if (gamepad2.a) {
            robot.getRelicRotatorCR().setPower(-0.5);
        } else if (gamepad2.y) {
            robot.getRelicRotatorCR().setPower(0.5);
        } else {
            robot.getRelicRotatorCR().setPower(0);
        }
    }*/

    private void openRelicClaw() {

        /*robot.getRelicClawServo().setPosition(robot.getRelicClawServoPosition());

        if (robot.getRelicClawServoPosition() >= robot.getRelicClawServoMinPosition()) {

            robot.setRelicClawServoPosition(robot.getRelicClawServoPosition() + robot.getServoIncrement());
        }*/

        robot.getRelicClawServo().setPosition(0.4);

    }

    private void closeRelicClaw() {

        /*if (robot.getRelicClawServoPosition() <= robot.getRelicClawServoMaxPosition()) {

            robot.setRelicClawServoPosition(robot.getRelicClawServoPosition() - robot.getServoIncrement());
        }

        robot.getRelicClawServo().setPosition(robot.getRelicClawServoPosition());*/

        robot.getRelicClawServo().setPosition(0.1);

    }

    private void glyphClawTriggers() {
        robot.getLeftServo().setPosition(gamepad2.left_trigger);
        robot.getRightServo().setPosition(1.0 - gamepad2.right_trigger);
    }
}
