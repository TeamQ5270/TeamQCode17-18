package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.Utils.Utils;

@TeleOp(name = "crtest", group =  "Linear Opmode")
@Disabled
public class CRServoTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        CRServo cr = null;

        cr = hardwareMap.crservo.get("servo cr");
        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            cr.setPower(gamepad2.right_stick_y);

            telemetry.addData("Joystick2: ", gamepad2.right_stick_y);
            telemetry.update();
        }
    }
}