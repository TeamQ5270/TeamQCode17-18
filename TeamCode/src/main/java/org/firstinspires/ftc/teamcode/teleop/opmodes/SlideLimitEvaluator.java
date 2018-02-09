package org.firstinspires.ftc.teamcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name = "Slide Limit Evaluator", group = "Linear Opmode")
@Disabled
public class SlideLimitEvaluator extends LinearOpMode {

    // Declare OpMode members.

    private DcMotor motorLift = null;
    private DcMotor motorRelicArm = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Gamepad: ", "Use gamepad 2");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        motorLift = hardwareMap.dcMotor.get("Motor Glyph");
        motorRelicArm = hardwareMap.dcMotor.get("Motor Relic");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        motorLift.setDirection(DcMotor.Direction.FORWARD);
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorRelicArm.setDirection(DcMotor.Direction.FORWARD);
        motorRelicArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRelicArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRelicArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            double liftPower = gamepad2.right_stick_y;
            double relicPower = gamepad2.left_stick_y;

            //determines if the lift motor or the relic arm is being evaluated
            //true for lift
            //false for relic arm
            boolean checkingLift = true;

            if (checkingLift) {
                motorLift.setPower(liftPower);
                telemetry.addData("Current motor: ", "Glyph Lift - press y to change");
                telemetry.addData("Lift encoder value: ", motorLift.getCurrentPosition());
                telemetry.update();
            } else if (!checkingLift) {
                motorRelicArm.setPower(relicPower);
                telemetry.addData("Current motor: ", "Relic Arm - press y to change");
                telemetry.addData("Relic arm encoder value", motorRelicArm.getCurrentPosition());
            }

            if (gamepad2.y) {
                checkingLift = !checkingLift;
            }

        }
    }
}
