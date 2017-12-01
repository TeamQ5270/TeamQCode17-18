package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by John_Kesler on 11/9/2017.
 */

public class MultiMotor {
    public static void setOpMode(DcMotor[] motors, DcMotor.RunMode mode) {
        for (DcMotor m : motors) {
            m.setMode(mode);
        }
    }

    public static void moveToPosition(DcMotor[] motors, int position, float power) {
        setOpMode(motors, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        for (DcMotor m : motors) {
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setTargetPosition(position);
            m.setPower(power);
        }
    }

    public static boolean busyMotors(DcMotor[] motors) {
        for (DcMotor m : motors) {
            if (m.isBusy()) {
                return true;
            }
        }
        return false;
    }

    public static void setPower(DcMotor[] motors, float power) {
        for (DcMotor m : motors) {
            m.setPower(power);
        }
    }

    /**
     *
     * @param motors the motor array passed into the class
     * @param distance how far to move, in whatever units (as long as they match the diameter's units)
     * @param power the power to move the motors at
     * @param wheelDiameter the diameter of the wheels, same units as distance
     */
    public static void moveToPositionAndyMark40(DcMotor[] motors, float distance, float power, float wheelDiameter) {
        //Calculate ticks
        float andyMarkTicks = 1120;
        float circumference = (float)Math.PI*wheelDiameter;
        float ticks = (andyMarkTicks*distance)/circumference;

        //Move to tick position
        moveToPosition(motors, (int)ticks, power);
    }

    public static void turnToPositionAndyMark40(DcMotor[] leftMotors, DcMotor[] rightMotors, float degrees, float power) {
        //Get the distance for each wheel set
        float tickPerDeg = 13.8f;
        float convertedDistance = tickPerDeg*degrees*1.33333333333f;

        //Move motors
        moveToPosition(leftMotors,(int)convertedDistance,power);
        moveToPosition(rightMotors,(int)-convertedDistance,power);
    }

    public static boolean turnBetter(DcMotor[] left, DcMotor[] right, int targetPosition, int currentPosition, float speed, float okErrror) {
        if (targetPosition<=targetPosition+okErrror&&targetPosition>=targetPosition-okErrror) {
            return false;
        }
        float speedBoi = clamp(PMove(targetPosition,currentPosition,speed),-1,1);
        setPower(left, speedBoi);
        setPower(right, -speedBoi);
        return true;
    }

    public static void turnToDegrees(DcMotor[] left, DcMotor[] right, GyroSensor gyro, int degreesToTurn, float speed) {
        int tolerance = 1;
        while (!turnBetter(left, right, 180-degreesToTurn, gyro.getHeading(), speed, tolerance)); //Turn using the passed in values, and +-tolerance degree
    }

    public static float PMove(float setPoint, float measuredOutput, float Kp) {
        float error = setPoint-measuredOutput;
        return error*Kp;
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}
