/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Mecanum Drive Example", group = "Linear Opmode")
// @Autonomous(...) is the other common choice

public class TeleOp extends LinearOpMode {

    /* Declare OpMode members. */

    private DcMotor motorLeftFront = null;
    private DcMotor motorRightFront = null;
    private DcMotor motorLeftBack = null;
    private DcMotor motorRightBack = null;
    private DcMotor motorLift = null;

    private int deadzone = 10; //deadzone for joysticks

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        motorLeftFront = hardwareMap.dcMotor.get("leftFront");
        motorLeftBack = hardwareMap.dcMotor.get("leftBack");
        motorRightFront = hardwareMap.dcMotor.get("rightFront");
        motorRightBack = hardwareMap.dcMotor.get("rightBack");
        motorLift = hardwareMap.dcMotor.get("lift");


        // eg: Set the drive motor directions:
        // "Reverse" the motor that runs backwards when connected directly to the battery

        //keep the directions as follows or else bad stuff happens:
        //left front: FORWARD
        //left back: FORWARD
        //right front: REVERSE
        //right back: REVERSE

        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotor.Direction.FORWARD);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);

        motorLift.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //left stick translates
            //right stick rotates
            //this code is from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example.
            //Some modifications made by Sheridan Page

            //gamepad left stick x is inverted to keep the direction of left-right translation correct


            //don't run motors if stick is within the deadzone
            if (Math.abs(gamepad1.left_stick_x) > deadzone
                    || Math.abs(gamepad1.left_stick_y) > deadzone
                    || Math.abs(gamepad1.left_stick_y) > deadzone) {


                double r = Math.hypot(-gamepad1.left_stick_x, gamepad1.left_stick_y);
                double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
                double rightX = -gamepad1.right_stick_x;
                final double v1 = r * Math.cos(robotAngle) + rightX;
                final double v2 = r * Math.sin(robotAngle) - rightX;
                final double v3 = r * Math.sin(robotAngle) + rightX;
                final double v4 = r * Math.cos(robotAngle) - rightX;

                motorLeftFront.setPower(v1);
                motorRightFront.setPower(v2);
                motorLeftBack.setPower(v3);
                motorRightBack.setPower(v4);
            }

            //move lift up and down

            if (Math.abs(gamepad2.right_stick_y) > deadzone
                    && motorLift.getCurrentPosition() < 2000) { //not sure what encoder position to use yet
                motorLift.setPower(gamepad2.right_stick_y);
            }


        }
    }
}
