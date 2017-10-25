package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.robotcore.hardware.Servo;

public class ServoFlipper {
    public enum ServoPosition {IN, OUT}

    private static double[] positionsOutLocal;
    private static double[] positionsInLocal;

    public static ServoPosition flipServos(Servo[] servos, double[] positionsOut, double[] positionsIn, ServoPosition previousStatus) {
        //For each servo
        for (int i = 0; i < servos.length; i++) {
            //Set the ServoPosition of the servo to be in if it was just in, and out if it was just out
            servos[i].setPosition(previousStatus == ServoPosition.IN ? positionsOut[i] : positionsIn[i]);
        }
        //If the servo was just in, set the flag to be out
        return (previousStatus == ServoPosition.IN) ? ServoPosition.OUT : ServoPosition.IN;
    }

    public static ServoPosition flipServos(Servo[] servos, ServoPosition previousStatus) {
        //For each servo
        for (int i = 0; i < servos.length; i++) {
            //Set the ServoPosition of the servo to be in if it was just in, and out if it was just out
            servos[i].setPosition(previousStatus == ServoPosition.IN ? positionsOutLocal[i] : positionsInLocal[i]);
        }
        //If the servo was just in, set the flag to be out
        return (previousStatus == ServoPosition.IN) ? ServoPosition.OUT : ServoPosition.IN;
    }

    //Setters for the positions flags (if used)
    public static void setPositionsOutLocal(double[] positionsOutLocal) {
        ServoFlipper.positionsOutLocal = positionsOutLocal;
    }

    public static void setPositionsInLocal(double[] positionsInLocal) {
        ServoFlipper.positionsInLocal = positionsInLocal;
    }
}
