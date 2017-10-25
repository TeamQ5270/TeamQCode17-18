package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.robotcore.hardware.Servo;

public class MultiServo {
    //TODO Multithreading for the servo movement
    public enum ServoPosition {IN, OUT}

    private static double[] positionsOutLocal;
    private static double[] positionsInLocal;

    //Flip the servo objects based on inputted distances
    public static ServoPosition flipServos(Servo[] servos, double[] positionsOut, double[] positionsIn, ServoPosition previousStatus) {
        //For each servo
        for (int i = 0; i < servos.length; i++) {
            //Set the ServoPosition of the servo to be in if it was just in, and out if it was just out
            servos[i].setPosition(previousStatus == ServoPosition.IN ? positionsOut[i] : positionsIn[i]);
        }
        //If the servo was just in, set the flag to be out
        return (previousStatus == ServoPosition.IN) ? ServoPosition.OUT : ServoPosition.IN;
    }

    //Flip the servo objects
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
        MultiServo.positionsOutLocal = positionsOutLocal;
    }

    public static void setPositionsInLocal(double[] positionsInLocal) {
        MultiServo.positionsInLocal = positionsInLocal;
    }

    //Move the servos to a specific position
    public static void moveToPositions(Servo[] servos, double[] positions) {
        for (int i = 0; i<servos.length; i++) {
            servos[i].setPosition(positions[i]);
        }
    }
}
