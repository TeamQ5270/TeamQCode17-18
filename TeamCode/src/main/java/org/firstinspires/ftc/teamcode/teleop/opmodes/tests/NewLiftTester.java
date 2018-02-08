package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

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

        Servo s = hardwareMap.get(Servo.class, "Servo Pull");

        DcMotor l = hardwareMap.get(DcMotor.class, "Motor Intake L");
        DcMotor r = hardwareMap.get(DcMotor.class, "Motor Intake R");
        r.setDirection(REVERSE);

        //start telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //wait until driver presses PLAY
        waitForStart();

        //run until OpMode is stopped
        while (opModeIsActive()) {
            if (gamepad2.dpad_up) {
                m.setPower(liftSpeed);
            }
            else if (gamepad2.dpad_down) {
                m.setPower(-0.3);
            }
            else {
                m.setPower(0);
            }

            //extend the glyph thing if rtriggered is preseed
            if (!gamepad2.right_bumper) {
                s.setPosition(Robot.servoPullPulled);
            }
            else {
                s.setPosition(Robot.servoPullRetracted);
            }

            //move the intake wheels at the speed of the trigger
            l.setPower(gamepad2.right_trigger>gamepad2.left_trigger?gamepad2.right_trigger:-gamepad2.left_trigger);
            r.setPower(gamepad2.right_trigger>gamepad2.left_trigger?gamepad2.right_trigger:-gamepad2.left_trigger);
        }
    }
}
