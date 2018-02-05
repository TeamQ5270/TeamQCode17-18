package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;

@TeleOp(name = "TeleOp Drive Code - Current Version", group = "Linear Opmode")

public class TeleopMain extends LinearOpMode {

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

    private final float servoGrabSpeed = 0.05f;
    private float servoGrabPosition = 0;

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

        //sets glyph claw to open
        zeroGlyphClaw();

        //run until OpMode is stopped
        while (opModeIsActive()) {
            controlRobot();

            telemetry.addData("Lift position: ", robot.getMotorGlyphLift().getCurrentPosition());
            telemetry.update();
            telemetry.addData("Lift servo position: ", robot.getClawPosition());
        }
    }

    private void controlRobot() {

        //mecanum driving
        if (Math.abs(gamepad1.left_stick_x) > robot.getDeadzone()
                || Math.abs(gamepad1.left_stick_y) > robot.getDeadzone()
                || Math.abs(gamepad1.right_stick_x) > robot.getDeadzone()) {

            mecanumDrive();

        } else if (Math.abs(gamepad1.left_stick_x) < robot.getDeadzone()
                && Math.abs(gamepad1.left_stick_y) < robot.getDeadzone()
                && Math.abs(gamepad1.right_stick_y) < robot.getDeadzone()) {

            mecanumDriveStop();
        } //end mecanum driving


        //glyph lift
        if (Math.abs(gamepad2.right_stick_y) > robot.getDeadzone()) {

            glyphLift();

        } else if (Math.abs(gamepad2.right_stick_y) <= robot.getDeadzone()) {
            robot.getMotorGlyphLift().setPower(motorZeroPower);
        } else {
            robot.getMotorGlyphLift().setPower(motorZeroPower);
        } //end glyph lift



        if (gamepad2.left_bumper) {
            openGlyphClaw();
        } else if (gamepad2.right_bumper) {
            closeGlyphClaw();
        }

        /*if (Math.abs(gamepad2.right_trigger) > robot.getDeadzone()
                || Math.abs(gamepad2.left_trigger) > robot.getDeadzone()) {
            glyphClawTriggers();
        }*/


        //relic arm
        if (Math.abs(gamepad2.left_stick_y) > robot.getDeadzone()) {
            relicArm();
        } else if (Math.abs(gamepad2.left_stick_y) <= robot.getDeadzone()) {
            robot.getMotorRelicArm().setPower(motorZeroPower);
        } else {
            robot.getMotorRelicArm().setPower(motorZeroPower);
        }//end relic arm


        //rotator
        if (gamepad2.left_trigger > robot.getDeadzone()
                || gamepad2.right_trigger > robot.getDeadzone()) {
            rotateClawCR();
        } else {
            robot.getRelicRotatorCR().setPower(0);
        } //end rotator



        //relic claw
        if (gamepad2.b) {
            servoGrabPosition=servoGrabPosition+servoGrabSpeed<=1?servoGrabPosition+servoGrabSpeed:1;
        } else if (gamepad2.a) {
            servoGrabPosition=servoGrabPosition+servoGrabSpeed>=0?servoGrabPosition-servoGrabSpeed:0;
        }//end relic claw

        robot.getRelicClawServo().setPosition(servoGrabPosition);



        //encoder limits enabled
        if (gamepad2.dpad_up) {
            encoderLimEnabled = false;
            robot.getMotorGlyphLift().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getMotorGlyphLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else if (gamepad2.dpad_down) {
            robot.getMotorRelicArm().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getMotorRelicArm().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else {
            encoderLimEnabled = true;
        }


    }

    private void mecanumDrive() {

        //weird trig
        double r = Math.hypot(-gamepad1.left_stick_x * 2, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x * 2)
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
                && robot.getMotorGlyphLift().getCurrentPosition() >= robot.getLiftTop()
                && robot.getMotorGlyphLift().getCurrentPosition() <= robot.getLiftBottom()
                && encoderLimEnabled) {

            robot.getMotorGlyphLift().setPower(gamepad2.right_stick_y);

        } else {
            //Allow lift to return to the safe zone if it is at max or min
            if (gamepad2.right_stick_y > joystickZero
                    && robot.getMotorGlyphLift().getCurrentPosition() <= robot.getLiftTop()) {
                robot.getMotorGlyphLift().setPower(gamepad2.right_stick_y);

            } else if (gamepad2.right_stick_y < joystickZero
                    && robot.getMotorGlyphLift().getCurrentPosition() >= robot.getLiftBottom()) {
                robot.getMotorGlyphLift().setPower(gamepad2.right_stick_y);
            } else if (!encoderLimEnabled
                    && Math.abs(gamepad2.right_stick_y) > joystickZero) {
                robot.getMotorGlyphLift().setPower(gamepad2.right_stick_y);
            } else {
                robot.getMotorGlyphLift().setPower(motorZeroPower);
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
            robot.getMotorRelicArm().setPower(-gamepad2.left_stick_y);
        } else {
            //Allow lift to return to the safe zone if it is at max or min
            if (-gamepad2.left_stick_y > joystickZero
                    && robot.getMotorRelicArm().getCurrentPosition() <= robot.getRelicLimitExtended()) {
                robot.getMotorRelicArm().setPower(-gamepad2.left_stick_y);

            } else if (-gamepad2.left_stick_y < joystickZero
                    && robot.getMotorRelicArm().getCurrentPosition() >= robot.getRelicLimitRetracted()) {

                robot.getMotorRelicArm().setPower(-gamepad2.left_stick_y);

            } else {
                robot.getMotorRelicArm().setPower(motorZeroPower);
            }
        }
    }


    @Deprecated
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

    private void rotateClawCR() {
        if (gamepad2.right_trigger > robot.getDeadzone()) {
            robot.getRelicRotatorCR().setPower(-gamepad2.right_trigger);
        } else if (gamepad2.left_trigger > robot.getDeadzone()) {
            robot.getRelicRotatorCR().setPower(gamepad2.left_trigger);
        }
    }

    private void openRelicClaw() {
        robot.getRelicClawServo().setPosition(1.0);

    }

    private void closeRelicClaw() {

        robot.getRelicClawServo().setPosition(0.1);

    }

    @Deprecated
    private void glyphClawTriggers() {
        robot.getLeftServo().setPosition(gamepad2.left_trigger);
        robot.getRightServo().setPosition(1.0 - gamepad2.right_trigger);
    }
}
