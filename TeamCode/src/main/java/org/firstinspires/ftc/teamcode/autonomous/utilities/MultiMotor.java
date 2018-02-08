package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Utils.Robot;

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
        float convertedDistance = tickPerDeg*degrees*AutoConstants.turnCoefficient;

        //Move motors
        moveToPosition(leftMotors,(int)convertedDistance,power);
        moveToPosition(rightMotors,(int)-convertedDistance,power);
    }

    public static void turnToPositionAndyMark40(DcMotor[] leftMotors, DcMotor[] rightMotors, float degrees, float power, float turnCoof) {
        //Get the distance for each wheel set
        float tickPerDeg = 13.8f;
        float convertedDistance = tickPerDeg*degrees*turnCoof;

        //Move motors
        moveToPosition(leftMotors,(int)convertedDistance,power);
        moveToPosition(rightMotors,(int)-convertedDistance,power);
    }

    public static boolean turnBetter(DcMotor[] left, DcMotor[] right, int targetPosition, int currentPosition, float speed, float okErrror) {
        setOpMode(left, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setOpMode(right, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if (currentPosition<=targetPosition+okErrror&&currentPosition>=targetPosition-okErrror) {
            return true;
        }
        float speedBoi = clamp(PMove(targetPosition,currentPosition,speed),-1,1);
        setPower(left, speedBoi);
        setPower(right, -speedBoi);
        return false;
    }

    public static float PMove(float setPoint, float measuredOutput, float Kp) {
        float error = setPoint-measuredOutput;
        float value = error*Kp>0?1:-1;
        return clamp(Math.abs(error*Kp),0.2f,1)*value;
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static void bestTurn(DcMotor[] motors, DcMotor[] leftMotors, DcMotor[] rightMotors, int heading, GyroSensor gyro, LinearOpMode mode) {
        while (!MultiMotor.turnBetter(leftMotors, rightMotors, (int) heading, gyro.getHeading(), 0.005f, 1)&&mode.opModeIsActive()) {}
        MultiMotor.setPower(motors, 0);
    }

    public static void bestTurn(Robot r, float degrees, float power, LinearOpMode m) {
        turnToPositionAndyMark40(r.getLeftDriveMotors(),r.getRightDriveMotors(),degrees,power);
        while (MultiMotor.busyMotors(r.getDriveMotors())&&m.opModeIsActive()) {}
        MultiMotor.setPower(r.getDriveMotors(), 0);
    }

    public static void bestMove(Robot robot, float distance, float power, LinearOpMode m) {
        MultiMotor.moveToPositionAndyMark40(robot.getDriveMotors(),distance,power,4);
        while (MultiMotor.busyMotors(robot.getDriveMotors())&&m.opModeIsActive()) {}
        MultiMotor.setPower(robot.getDriveMotors(), 0);
    }

    public static void bestMove(Robot robot, float distance, float power, float timeout, LinearOpMode m) {
        MultiMotor.moveToPositionAndyMark40(robot.getDriveMotors(),distance,power,4);
        ElapsedTime stalltimer = new ElapsedTime();
        stalltimer.reset();
        while (MultiMotor.busyMotors(robot.getDriveMotors())&&m.opModeIsActive()&&stalltimer.seconds()<=timeout) {}
        MultiMotor.setPower(robot.getDriveMotors(), 0);
    }
    public static void bestTurn(Robot r, float degrees, float power, LinearOpMode m, float turnCoof) {
        turnToPositionAndyMark40(r.getLeftDriveMotors(),r.getRightDriveMotors(),degrees,power, turnCoof);
        while (MultiMotor.busyMotors(r.getDriveMotors())&&m.opModeIsActive()) {}
        MultiMotor.setPower(r.getDriveMotors(), 0);
    }
}