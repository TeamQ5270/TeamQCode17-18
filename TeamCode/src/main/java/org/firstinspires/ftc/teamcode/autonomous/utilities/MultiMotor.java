package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;

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

    public static void moveToPositionAndyMark40(DcMotor[] motors, float distance, float power, float wheelDiameter) {
        //Calculate ticks
        float andyMarkTicks = 1120;
        float circumference = (float)Math.PI*wheelDiameter;
        float ticks = (andyMarkTicks*distance)/circumference;

        //Move to tick position
        moveToPosition(motors, (int)ticks, power);
    }
}
