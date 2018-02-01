package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Utils.Robot;

import static java.lang.Thread.sleep;

/**
 * Created by John_Kesler on 2/1/2018.
 */

public class SubFunctions {
    public static void runJewel(Servo jewelServo, LynxI2cColorRangeSensor colorSensor, boolean sideColor, Robot robot, LinearOpMode opMode) throws java.lang.InterruptedException {
        jewelServo.setPosition(AutoConstants.jewelExtended);
        sleep(300);
        float moveDistance = AutoConstants.jewelMoveDistance*(colorSensor.red()<=colorSensor.blue()^sideColor?1:-1);
        MultiMotor.bestMove(robot,moveDistance,AutoConstants.straightPower/2,opMode);
        sleep(300);
        jewelServo.setPosition(AutoConstants.jewelRetracted);
        sleep(300);
    }
}
