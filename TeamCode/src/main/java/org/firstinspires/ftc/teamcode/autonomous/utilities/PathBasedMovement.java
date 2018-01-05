package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;


public class PathBasedMovement {
    public static void followPath(String commandSet, boolean swapSide, DcMotor[] leftMotors, DcMotor[] rightMotors, GyroSensor gyro) {
        DcMotor[] motors = new DcMotor[leftMotors.length+rightMotors.length];
        for (int i=0;i<leftMotors.length;i++) {
            motors[i] = leftMotors[i];
        }
        for (int j=0;j<rightMotors.length;j++) {
            motors[j+ leftMotors.length]=rightMotors[j];
        }
        float[][] commands = parseCommand(commandSet);
        int op=0;
        while (op!=-1) {
            float[] command = commands[op];
            float a = swapSide?swapSide(command[0]):command[0];
            float d = command[1];
            float ra = command[2];

            //rotate
            while (!MultiMotor.turnBetter(leftMotors, rightMotors, (int) a, gyro.getHeading(), 0.005f, 1)) {}
            MultiMotor.setPower(motors, 0);

            //move
            MultiMotor.moveToPositionAndyMark40(motors, d, -1f, 4);
            while (MultiMotor.busyMotors(motors)) {}
            MultiMotor.setPower(motors, 0);
        }
    }

    public static float swapSide(float in) {
            return in<180?180-in:540-in;
        }

    public static float[][] parseCommand(String input) {
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
}
