package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Utils.InitTypes;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.Utils.Utils;
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

    private final float liftSpeed = 0.3f;

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

        if (gamepad1.dpad_up) {
            robot.getMotorLift().setPower(liftSpeed);
        }
        else if (gamepad1.dpad_down) {
            robot.getMotorLift().setPower(-0.3);
        }
        else {
            robot.getMotorLift().setPower(0);
        }

        //extend the glyph thing if rtriggered is preseed
        if (gamepad1.b) {
            robot.getServoLiftPuller().setPosition(Robot.servoPullPulled);
        }
        else {
            robot.getServoLiftPuller().setPosition(Robot.servoPullRetracted);
        }

        //move the intake wheels at the speed of the trigger
        robot.getMotorIntakeLeft().setPower(gamepad1.right_trigger>gamepad1.left_trigger?gamepad1.right_trigger:-gamepad1.left_trigger);
        robot.getMotorIntakeRight().setPower(gamepad1.right_trigger>gamepad1.left_trigger?gamepad1.right_trigger:-gamepad1.left_trigger);

        robot.getMotorRelicArm().setPower(1);

        if (gamepad1.a) {
            hardwareMap.get(Servo.class, "Servo Jewel").setPosition(AutoConstants.jewelExtended);
        }
    }

    private void mecanumDrive() {

        //weird trig
        double r = Math.hypot(-gamepad1.left_stick_x * 2, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x * 2)
                - Math.PI / weirdFourInMecanumCalcs;
        double rightX = -gamepad1.right_stick_x;
        double v1 = r * Math.cos(robotAngle) + rightX;
        double v2 = r * Math.sin(robotAngle) - rightX;
        double v3 = r * Math.sin(robotAngle) + rightX;
        double v4 = r * Math.cos(robotAngle) - rightX;

        //if the right trigger is held, invert controls
//        if (gamepad1.right_trigger>0.1) {
//            v1*=-1;
//            v2*=-1;
//            v3*=-1;
//            v4*=-1;
//        }

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
