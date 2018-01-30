package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;
import org.firstinspires.ftc.teamcode.Utils.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Relic Servo Range Tester", group =  "Linear Opmode")
public class RelicTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Robot robot = new Robot();
        robot.init(hardwareMap);

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {


            double clawServoPos = Utils.map(gamepad2.right_stick_y, -1, 1, 0, 1);


            robot.getRelicRotatorCR().setPower(-gamepad1.right_stick_y);
            robot.getRelicClawServo().setPosition(clawServoPos);


            telemetry.addData("Claw Servo: ", clawServoPos);
            telemetry.update();
        }
    }
}