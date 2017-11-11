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
            m.setTargetPosition(position);
            m.setPower(power);
        }
        setOpMode(motors, DcMotor.RunMode.RUN_TO_POSITION);
    }

    public static boolean busyMotors(DcMotor[] motors) {
        for (DcMotor m : motors) {
            if (m.isBusy()) {
                return true;
            }
        }
        return false;
    }
}
