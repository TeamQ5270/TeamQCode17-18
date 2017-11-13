
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiServo;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Autonomous(name="Safe Zone Parking")
public class SafeZoneParking extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    //Vuforia manager

    //Initialize Misc

    @Override
    public void runOpMode() {

        //Create hardware devices (Using names from configuration)
        DcMotor frontLeftMotor = hardwareMap.get(DcMotor.class, "Motor Drive FL");
        DcMotor frontRightMotor = hardwareMap.get(DcMotor.class, "Motor Drive FR");
        DcMotor rearLeftMotor = hardwareMap.get(DcMotor.class, "Motor Drive RL");
        DcMotor rearRightMotor = hardwareMap.get(DcMotor.class, "Motor Drive RR");
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rearLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rearRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotor[] motors = new DcMotor[] {frontLeftMotor, frontRightMotor, rearLeftMotor, rearRightMotor};
        DcMotor[] leftMotors = new DcMotor[] {frontLeftMotor, rearLeftMotor};
        DcMotor[] rightMotors = new DcMotor[] {frontRightMotor, frontRightMotor};

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Core Initialized");
        telemetry.update();

        MultiMotor.setOpMode(motors, DcMotor.RunMode.RUN_USING_ENCODER);

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        MultiMotor.moveToPositionAndyMark40(motors, 36, 0.5f, 4);
        while(opModeIsActive()&&MultiMotor.busyMotors(motors)) {}
        MultiMotor.setPower(motors, 0);
/*

        MultiMotor.turnToPositionAndyMark40(leftMotors,rightMotors,360,0.5f,4);
        while(opModeIsActive()&&MultiMotor.busyMotors(motors)) {}
        MultiMotor.setPower(motors, 0);
*/

        //End OpMode
        stop();
    }
}
