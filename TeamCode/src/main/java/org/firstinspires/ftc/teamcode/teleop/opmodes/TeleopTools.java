package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by John_Kesler on 3/21/2018.
 */

public class TeleopTools {
    public static void mecanumDrive(Gamepad gamepad, DcMotor[] motors) {
        //weird trig
        double r = Math.hypot(-gamepad.left_stick_x * 2, gamepad.left_stick_y);
        double robotAngle = Math.atan2(gamepad.left_stick_y, -gamepad.left_stick_x * 2)
                - Math.PI / 4;
        double rightX = -gamepad.right_stick_x;
        double v1 = r * Math.cos(robotAngle) + rightX;
        double v2 = r * Math.sin(robotAngle) - rightX;
        double v3 = r * Math.sin(robotAngle) + rightX;
        double v4 = r * Math.cos(robotAngle) - rightX;

        //set calculated powers to motors
        motors[0].setPower(v1);
        motors[1].setPower(v2);
        motors[2].setPower(v3);
        motors[3].setPower(v4);

    }
}
