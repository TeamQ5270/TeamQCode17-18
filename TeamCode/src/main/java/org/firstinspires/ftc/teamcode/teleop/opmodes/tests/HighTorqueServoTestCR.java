package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
/**
 * Created by Sheridan_Page on 2/13/2018.
 */

@TeleOp(name = "High Torque Servo Test - Normal", group =  "Linear Opmode")
public class HighTorqueServoTestNormal extends LinearOpMode{

    @Override
    public void runOpMode() {
        Servo s = null;

        s = hardwareMap.servo.get("Servo Relic Rotator");

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double position = 0.0;

            if (gamepad1.y) {
                position += 0.05;
            } else if (gamepad1.a) {
                position -= 0.05;
            }

            s.setPosition(position);

            telemetry.addData("Servo Position: ", position);
            telemetry.update();
        }
    }
}