
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;

@Autonomous(name="Path Follow OpMode")
public class PathBasedAutoMode extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    public int breakAndAction(int currentCommand, int nextCommand) {
        if (currentCommand==-1) { //placeholder, format is if command equals something do something and return the next command
            return -1;
        }
        else if (currentCommand==2) {
            sleep(3000);
            return nextCommand;
        }
        else {
            return nextCommand;
        }
    }

    public float[][] parseCommand(String input) {
        String[] splitNL = input.split("\n");
        float[][] splitArray = new float[splitNL.length][4];
        for (int i=0; i<splitArray.length; i++) {
            String[] splitty = splitNL[i].split(",");
            for (int j=0; j<splitty.length; j++) {
                splitArray[i][j] = Float.valueOf(splitty[j]);
            }
        }
        return splitArray;
    }

    @Override
    public void runOpMode() {
        String commandsRaw = "358.68308759420677,36.00951122869964,-358.68308759420677,2\n" +
                "233.29127149169108,58.84161210487923,125.39181610251569,-1\n" +
                "91.46880071438582,48.42970560777392,141.82247077730526,1\n";

        float[][] commands = parseCommand(commandsRaw);

        //Define the motors on the robot
        DcMotor leftMotor;
        DcMotor rightMotor;
        ModernRoboticsI2cGyro gyro;

        //Set the motors to be actual classes
        leftMotor = hardwareMap.get(DcMotor.class, "left motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right motor");
        gyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");

        //Set the directions of the motors
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        //Set the braking of the motors
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DcMotor[] motors = new DcMotor[] {leftMotor, rightMotor};
        DcMotor[] leftMotors = new DcMotor[] {leftMotor};
        DcMotor[] rightMotors = new DcMotor[] {rightMotor};

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Core Initialized");
        telemetry.update();

        MultiMotor.setOpMode(motors, DcMotor.RunMode.RUN_USING_ENCODER);

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        int op = 0;
        while (op!=-1) {
            float[] command = commands[op];
            float a = command[0];
            float d = command[1];
            float ra = command[2];

            //rotate
            while (!MultiMotor.turnBetter(leftMotors, rightMotors, (int) a, gyro.getHeading(), 0.005f, 1) && opModeIsActive()) {}
            MultiMotor.setPower(motors, 0);

            //move
            MultiMotor.moveToPositionAndyMark40(motors, d, -1f, 4);
            while (opModeIsActive() && MultiMotor.busyMotors(motors)) {}
            MultiMotor.setPower(motors, 0);

            //check next op
            op = breakAndAction(op, (int)command[3]);
        }

        //End OpMode
        stop();
    }
}
