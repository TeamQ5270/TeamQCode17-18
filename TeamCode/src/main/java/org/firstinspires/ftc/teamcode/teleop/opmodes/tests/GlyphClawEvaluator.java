package org.firstinspires.ftc.teamcode.teleop.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.ThreadedServoMovement;

/**
 * Created by Sheridan_Page on 1/6/2018.
 */

public class GlyphClawEvaluator extends LinearOpMode{

    Robot robot = new Robot();
    public void runOpMode() {
        robot.init(hardwareMap);

        if (gamepad2.right_bumper) {
            closeGlyphClaw();
        } else if (gamepad2.left_bumper) {
            openGlyphClaw();
        }

        telemetry.addData("Servo Position: ", robot.getClawPosition());

    }

    public void openGlyphClaw() {


        //open claw arms simultaneously using ThreadedServoMovement class

        ThreadedServoMovement moveLeftServo = new ThreadedServoMovement
                (robot.getLeftServo(), robot.getClawPosition());
        ThreadedServoMovement moveRightServo = new ThreadedServoMovement
                (robot.getRightServo(), robot.getGlyphServoMaxPosition() - robot.getClawPosition());

        //start servo objects
        moveLeftServo.start();
        moveRightServo.start();

        //limit servo to allowed positions, set by servoMinPosition
        if (robot.getClawPosition() >= robot.getGlyphServoMinPosition()) {
            robot.setClawPosition(robot.getClawPosition() - robot.getServoIncrement());
        }
    }

    public void closeGlyphClaw() {
        //close claw arms simultaneously using ThreadedServoMovement class
        ThreadedServoMovement moveLeftServo = new ThreadedServoMovement
                (robot.getLeftServo(), robot.getClawPosition());
        ThreadedServoMovement moveRightServo = new ThreadedServoMovement
                (robot.getRightServo(), robot.getGlyphServoMaxPosition() - robot.getClawPosition());

        //start servo objects
        moveLeftServo.start();
        moveRightServo.start();

        //limit servo to allowed positions, set by servoMaxPosition
        if (robot.getClawPosition() <= robot.getGlyphServoMaxPosition()) {

            robot.setClawPosition(robot.getClawPosition() + robot.getServoIncrement());
        }
    }
}
