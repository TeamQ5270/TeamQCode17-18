package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Outreach Pushbot Drive", group = "Linear Opmode")


public class OutreachPushbotDrive extends LinearOpMode {

    /* Declare OpMode members. */

    @Override
    public void runOpMode() {
        //Define the motors on the robot
        DcMotor leftMotor;
        DcMotor rightMotor;

        //Set the motors to be actual classes
        leftMotor = hardwareMap.get(DcMotor.class, "left motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right motor");

        //Set the directions of the motors
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        //Set the braking of the motors
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //Calculate the power of the motors using sketchy math
            float[] movementVector = new float[] {gamepad1.left_stick_x,gamepad1.left_stick_y};
            float magnitude = (float)Math.hypot(movementVector[0],movementVector[1]);
            leftMotor.setPower(movementVector[0]<0 ? magnitude+movementVector[0] : magnitude);
            rightMotor.setPower(movementVector[1]>0 ? magnitude-movementVector[1] : magnitude);
        }
    }


}
