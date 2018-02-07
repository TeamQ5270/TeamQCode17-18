package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;

@TeleOp(name = "R&P Lift Tester", group = "Linear Opmode")

public class NewLiftTester extends LinearOpMode {

    //make Robot object
    //these variables get rid of magic numbers - you're welcome, Matthew
    //indices of motor values returned by robot.getDriveMotors()
    //left front -- right front -- left back -- right back
    private final int mLeftFrontIdx = 0;
    private final int mRightFrontIdx = 1;
    private final int mLeftBackIdx = 2;
    private final int mRightBackIdx = 3;

    //more un-magicked numbers
    private final double motorZeroPower = 0.0;
    private final double joystickZero = 0.0;
    private final int weirdFourInMecanumCalcs = 4;

    private final float servoGrabSpeed = 0.05f;
    private float servoGrabPosition = 0;

    private boolean encoderLimEnabled = true;

    private final float liftSpeed = 0.5f;

    @Override
    public void runOpMode() {

        DcMotor m = hardwareMap.get(DcMotor.class, "Lift Motor");
        m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //start telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //wait until driver presses PLAY
        waitForStart();

        //run until OpMode is stopped
        while (opModeIsActive()) {
            if (gamepad1.a) {
                m.setPower(liftSpeed);
            }
            else if (gamepad1.b) {
                m.setPower(-0.3);
            }
            else {
                m.setPower(0);
            }
        }
    }
}
