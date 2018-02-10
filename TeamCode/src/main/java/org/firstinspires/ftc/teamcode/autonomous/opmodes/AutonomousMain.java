
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Utils.InitTypes;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.AutoConstants;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.utilities.SubFunctions;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

import static java.lang.Runtime.getRuntime;
import static org.firstinspires.ftc.teamcode.autonomous.utilities.AutoConstants.straightPower;

public class AutonomousMain {

    public static void runOpMode(LinearOpMode opMode, boolean side) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        //initialize robot
        Robot robot = new Robot(InitTypes.NEWBOT);
        robot.init(hardwareMap);

        LynxI2cColorRangeSensor jewelColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Jewel");      //Color sensor onboard jewel arm
        LynxI2cColorRangeSensor boardColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Ground");     //sensor to read the board

        Servo jewelServo = hardwareMap.get(Servo.class, "Servo Jewel");     //Jewel servo

        //Let user know that robot has been initialized
        printConsole("initialized", opMode);

        //get color of the side the robot is on
        int colorR = boardColor.red();
        int colorB = boardColor.blue();
        boolean sideColor = colorR>colorB;  //true if red

        //get side of the field that the robot is on
        boolean sideField = false;  //true if on not audience side

        //Wait For Play, Start Timer
        opMode.waitForStart();

        float moveBack = 0;
        try {
            moveBack = SubFunctions.runJewel(jewelServo, jewelColor, sideColor, robot, opMode);
        }
        catch (java.lang.InterruptedException e) {
            e.printStackTrace();
        }

        printConsole("moving back into alignment for vumark", opMode);
        //move forwards back onto the board
        MultiMotor.bestMove(robot,-moveBack, AutoConstants.straightPower,opMode);

        printConsole("locking onto vumark", opMode);
        RelicRecoveryVuMark target = SubFunctions.getVumark(5,opMode);

        //turn to face the correct box
        if (sideField) {
            MultiMotor.bestTurn(robot,180,AutoConstants.turnPower,opMode);
        }
        printConsole("aligning with box", opMode);

        //move to be ready to turn into the box
        MultiMotor.bestMove(robot,SubFunctions.getMoveDistance(target,sideColor), AutoConstants.straightPower,opMode);
        printConsole("facing box", opMode);

        //turn -90 degrees again
        MultiMotor.bestTurn(robot,SubFunctions.transferDegrees(-90,sideField),AutoConstants.turnPower,opMode);
        printConsole("entering box", opMode);

        //move into the box
        MultiMotor.bestMove(robot,-15,AutoConstants.straightPower/2,4,opMode);
        printConsole("moving out of box", opMode);

        //and out
        MultiMotor.bestMove(robot,1,AutoConstants.straightPower/2,opMode);
        printConsole("raising lift", opMode);

        //and flip in
        robot.getMotorFlipper().setPower(-0.1);
        opMode.sleep(1000);
        robot.getMotorFlipper().setPower(0);
        printConsole("flipping lift", opMode);

        robot.getServoLiftPuller().setPosition(Robot.servoPullPulled);
        opMode.sleep(1000);
        robot.getServoLiftPuller().setPosition(Robot.servoPullRetracted);
        printConsole("moving out of crypto", opMode);

        //further out
        MultiMotor.bestMove(robot,5,AutoConstants.straightPower/2,opMode);
        printConsole("complete", opMode);

        opMode.sleep(30000);

        opMode.stop();
    }

    public static void printConsole(String print, LinearOpMode mode) {
        mode.telemetry.addData("stage", print);
        mode.telemetry.update();
    }
}
