package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;
import org.firstinspires.ftc.teamcode.Utils.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Relic Servo Range Tester", group =  "Linear Opmode")
public class RelicTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Robot robot = new Robot();
        robot.init(hardwareMap);

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            double rotatorServoPos = Utils.map(gamepad1.right_stick_y, -1, 1, 0, 1);
            double clawServoPos = Utils.map(gamepad2.right_stick_y, -1, 1, 0, 1);
            double rotator2ServoPos = Utils.map(gamepad2.left_stick_y, -1, 1, 0, 1);

            robot.getRelicRotatorServo().setPosition(rotatorServoPos);
            robot.getRelicRotator2Servo().setPosition(rotator2ServoPos);
            robot.getRelicClawServo().setPosition(clawServoPos);

            telemetry.addData("Rotator Servo: ", rotatorServoPos);
            telemetry.addData("Rotator 2 Servo: ", rotator2ServoPos);
            telemetry.addData("Claw Servo: ", clawServoPos);
            telemetry.update();
        }
    }
}