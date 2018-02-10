package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;
import org.firstinspires.ftc.teamcode.Utils.*;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Relic Servo Range Tester", group =  "Linear Opmode")
public class RelicTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Servo claw = null;
        Servo rotator = null;

        claw = hardwareMap.servo.get("Servo Relic Claw");
        rotator = hardwareMap.servo.get("Servo Relic Rotator");

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {


            double clawServoPos = Utils.map(gamepad2.right_stick_y, -1, 1, 0, 1);
            double rotatorPos = Utils.map(gamepad2.left_stick_y, -1, 1, 0, 1);

            claw.setPosition(clawServoPos);
            rotator.setPosition(rotatorPos);


            telemetry.addData("Claw Servo: ", clawServoPos);
            telemetry.addData("Rotator: ", rotatorPos);
            telemetry.update();
        }
    }
}