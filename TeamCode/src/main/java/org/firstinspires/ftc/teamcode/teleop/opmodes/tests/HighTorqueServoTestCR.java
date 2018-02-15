package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

/**
 * Created by Sheridan_Page on 2/13/2018.
 */

@TeleOp(name = "High Torque Servo Test - CR", group =  "Linear Opmode")
public class HighTorqueServoTestCR extends LinearOpMode{

    @Override
    public void runOpMode() {
        CRServo s = null;

        s = hardwareMap.crservo.get("Servo Relic Rotator");

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

        double power = 0.0;

        waitForStart();

        while (opModeIsActive()) {


            if (gamepad1.y) {
                power += 0.05;
            } else if (gamepad1.a) {
                power -= 0.05;
            }

            s.setPower(power);

            telemetry.addData("Servo Power: ", power);
            telemetry.update();
        }
    }
}