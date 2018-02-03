
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.AutoConstants;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.utilities.SubFunctions;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Autonomous(name="Autonomous jewel test")
public class AutonomousJewel extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        //initialize robot
        Robot robot = new Robot();
        robot.init(hardwareMap);

        LynxI2cColorRangeSensor jewelColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Jewel");      //Color sensor onboard jewel arm
        LynxI2cColorRangeSensor boardColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Ground");     //sensor to read the board

        Servo jewelServo = hardwareMap.get(Servo.class, "Servo Jewel");     //Jewel servo

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Core Initialized");
        telemetry.update();

        //get color of the side the robot is on
        int colorR = boardColor.red();
        int colorB = boardColor.blue();
        boolean sideColor = colorR>colorB;  //true if red

        //get side of the field that the robot is on
        boolean sideField = false;  //true if on doublebox side

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        float moveBack = 0;
        try {
            moveBack = SubFunctions.runJewel(jewelServo, jewelColor, sideColor, robot, this);
        }
        catch (java.lang.InterruptedException e) {
            e.printStackTrace();
        }

        telemetry.addData("moving","yes");
        telemetry.update();

        RelicRecoveryVuMark target = SubFunctions.getVumark(5,this);

        //move forwards back onto the board
        MultiMotor.bestMove(robot,-moveBack,AutoConstants.straightPower,this);
        //turn to face the correct box
        if (sideField) {
            MultiMotor.bestTurn(robot,180,AutoConstants.turnPower,this);
        }
        telemetry.addData("stage","charge");
        telemetry.update();
        SubFunctions.moveClawsIn(robot);
        //move to be ready to turn into the box
        MultiMotor.bestMove(robot,SubFunctions.getMoveDistance(target,sideColor), AutoConstants.straightPower,this);
        telemetry.addData("stage","turn to face crypto");
        telemetry.update();
        //turn 90 degrees again
        MultiMotor.bestTurn(robot,SubFunctions.transferDegrees(90,sideField),AutoConstants.turnPower,this);
        telemetry.addData("stage","going into the box");
        telemetry.update();
        //move into the box
        SubFunctions.moveClawsOut(robot);
        MultiMotor.bestMove(robot,-15,AutoConstants.straightPower/2,4,this);
        telemetry.addData("stage","out of box");
        telemetry.update();
        //and out
        MultiMotor.bestMove(robot,10,AutoConstants.straightPower/2,this);
        telemetry.addData("stage","yes");
        telemetry.update();

        sleep(30000);

        stop();
    }
}
