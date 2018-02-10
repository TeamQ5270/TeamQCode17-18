package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Utils.InitTypes;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.AutoConstants;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;

@TeleOp(name = "Drive Code new Robot (use at states)", group = "Linear Opmode")

public class TeleopMain extends LinearOpMode {

    //make Robot object
    private Robot robot = new Robot(InitTypes.NEWBOT);

    //more un-magicked numbers
    private final double motorZeroPower = 0.0;
    private final double joystickZero = 0.0;
    private final int weirdFourInMecanumCalcs = 4;

    private final float liftSpeed = 0.5f; //5% lift speed

    @Override
    public void runOpMode() {
        //initialize robot
        robot.init(hardwareMap);

        //start telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //wait until driver presses PLAY
        waitForStart();

        //raise the jewel arm
        hardwareMap.get(Servo.class, "Servo Jewel").setPosition(AutoConstants.jewelRetracted);

        //run until OpMode is stopped
        while (opModeIsActive()) {
            controlRobot();
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

        if (gamepad2.left_stick_y<-0.1) {
            robot.getMotorLift().setPower(liftSpeed);
        }
        else if (gamepad2.left_stick_y>0.1) {
            robot.getMotorLift().setPower(-0.3);
        }
        else {
            robot.getMotorLift().setPower(0);
        }

        //extend the glyph thing if rtriggered is preseed
        if (gamepad2.a) {
            robot.getServoLiftPuller().setPosition(Robot.servoPullPulled);
        }
        else {
            robot.getServoLiftPuller().setPosition(Robot.servoPullRetracted);
        }

        if (gamepad2.right_stick_y>0.1) {
            robot.getServoPush().setPosition(1);
        }
        else if (gamepad2.right_stick_y<-0.1) {
            robot.getServoPush().setPosition(0);
        }
        else {
            robot.getServoPush().setPosition(0.5f);
        }

        //move the intake wheels at the speed of the trigger
        robot.getMotorIntakeLeft().setPower(gamepad2.right_trigger>gamepad2.left_trigger?gamepad2.right_trigger:-gamepad2.left_trigger/2);
        robot.getMotorIntakeRight().setPower(gamepad2.right_trigger>gamepad2.left_trigger?gamepad2.right_trigger:-gamepad2.left_trigger/2);
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
        robot.getDriveMotors()[0].setPower(v1);
        robot.getDriveMotors()[1].setPower(v2);
        robot.getDriveMotors()[2].setPower(v3);
        robot.getDriveMotors()[3].setPower(v4);

        //if sticks are within deadzone, set all drive motor powers to
    }

    private void mecanumDriveStop() {
        MultiMotor.setPower(robot.getDriveMotors(),0);
    }
}
