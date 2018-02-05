package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Utils.Robot;

@TeleOp(name = "K Value Evaluator",group = "Linear Opmode")
public class KEvaluator extends LinearOpMode {

    private Robot robot = new Robot();
    private final int mLeftFrontIdx = 0;
    private final int mRightFrontIdx = 1;
    private final int mLeftBackIdx = 2;
    private final int mRightBackIdx = 3;

    private double K = 0;
    public void runOpMode() {

        robot.init(hardwareMap);
        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.y && K <= 1.0) {
                K += 0.05;
                sleep(750);
            } else if (gamepad1.a && K >= 0.0) {
                K -= 0.05;
                sleep(750);
            }

            if (K > 1) {
                K = 1;
            }
            if (K < 0) {
                K = 1;
            }

            mecanumDrive();

            telemetry.addData("K Value: ", K);
            telemetry.update();

        }


    }

    private void mecanumDrive() {
        double forward = gamepad1.left_stick_y;
        double right = gamepad1.left_stick_x;
        double clockwise = gamepad1.right_stick_x;

        clockwise = K * clockwise;

        double front_left = forward + clockwise + right;
        double front_right = forward - clockwise - right;
        double rear_left = forward + clockwise - right;
        double rear_right = forward - clockwise + right;

        double max = Math.abs(front_left);
        if (Math.abs(front_right)>max) {
            max = Math.abs(front_right);
        }
        if (Math.abs(rear_left)>max) {
            max = Math.abs(rear_left);
        }
        if (Math.abs(rear_right)>max) {
            max = Math.abs(rear_right);
        }

        if (max > 1){
            front_left/=max;
            front_right/=max;
            rear_left/=max;
            rear_right/=max;
        }
        robot.getDriveMotors()[mLeftFrontIdx].setPower(front_left);
        robot.getDriveMotors()[mRightFrontIdx].setPower(front_right);
        robot.getDriveMotors()[mLeftBackIdx].setPower(rear_left);
        robot.getDriveMotors()[mRightBackIdx].setPower(rear_right);
    }
}
