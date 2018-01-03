
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;

@Autonomous(name="Path Follow OpMode")
public class PathBasedAutoMode extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    //Define the motors on the robot
    DcMotor leftMotor;
    DcMotor rightMotor;
    ModernRoboticsI2cGyro gyro;
    ModernRoboticsTouchSensor touch;

    boolean swapSide = false;

    public float swapSide(float in) {
        return in<180?180-in:540-in;
    }

    public int breakAndAction(int currentCommand, int nextCommand) {
        if (currentCommand==-1) { //placeholder, format is if command equals something do something and return the next command
            return -1;
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
        String commandsRaw = "357.3974377975002,36.451391247799016,-357.3974377975002,2\n" +
                "182.60256220249983,36.451391247799016,174.79487559500035,2\n" +
                "90.0,48.41379310344828,92.60256220249983,-1\n";

        float[][] commands = parseCommand(commandsRaw);


        //Set the motors to be actual classes
        leftMotor = hardwareMap.get(DcMotor.class, "left motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right motor");
        gyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");
        touch = hardwareMap.get(ModernRoboticsTouchSensor.class, "touch");

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

        //Get current side
        //TODO get a valid sensor hooked up and working for this
        if (touch.isPressed()) {
            swapSide=true;
        }

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        int op = 0;
        while (op!=-1) {
            float[] command = commands[op];
            float a = swapSide?swapSide(command[0]):command[0];
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
