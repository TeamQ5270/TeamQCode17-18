package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.robotcore.hardware.Servo;

public class ThreadedServoMovement extends Thread {
    //TODO add more operations, probably enumerate possible operations on servo
    //Servo to operate on
    private Servo s;
    //Position to move servo to
    private double position;

    //Set local variables
    public ThreadedServoMovement(Servo s, double position) {
        this.s = s;
        this.position = position;
    }

    //Run as a new thread
    @Override
    public void run() {
        s.setPosition(position);
    }
}
