package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.robotcore.hardware.Servo;

public class MultiServo {
    public enum ServoPosition {IN, OUT}

    private static double[] positionsInLocal;
    private static double[] positionsOutLocal;

    //Flip the servo objects based on inputted distances
    public static ServoPosition flipServos(Servo[] servos, double[] positionsOut, double[] positionsIn, ServoPosition previousStatus) {
        //Move the servos to positions using the threaded method
        threadedMoveToPositions(servos, previousStatus==ServoPosition.IN ? positionsOut : positionsIn);

        //TODO see below
        /*
        //For each servo
        for (int i = 0; i < servos.length; i++) {
            //Set the ServoPosition of the servo to be in if it was just in, and out if it was just out
            servos[i].setPosition(previousStatus == ServoPosition.IN ? positionsOut[i] : positionsIn[i]);
        }*/

        //If the servo was just in, set the flag to be out
        return (previousStatus == ServoPosition.IN) ? ServoPosition.OUT : ServoPosition.IN;
    }

    //Flip the servo objects
    public static ServoPosition flipServos(Servo[] servos, ServoPosition previousStatus) {
        //Move the servos to positions using the threaded method
        threadedMoveToPositions(servos, previousStatus==ServoPosition.IN ? positionsOutLocal : positionsInLocal);

        //TODO get rid of this code when done testing
        /*
        //For each servo
        for (int i = 0; i < servos.length; i++) {
            //Set the ServoPosition of the servo to be in if it was just in, and out if it was just out
            servos[i].setPosition(previousStatus == ServoPosition.IN ? positionsOutLocal[i] : positionsInLocal[i]);
        }*/

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

    //Move to positions, but threaded
    public static void threadedMoveToPositions(Servo[] servos, double[] positions) {
        for (int i = 0; i<servos.length; i++) {
            ThreadedServoMovement threadServo = new ThreadedServoMovement(servos[i], positions[i]);
            threadServo.start();
        }
    }
}
