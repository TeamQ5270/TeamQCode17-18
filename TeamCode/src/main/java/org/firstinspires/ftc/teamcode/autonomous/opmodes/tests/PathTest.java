package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiServo;

@Autonomous(name="Pathtest", group="AutonomousTest")
public class PathTest extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    static final double ticksPerRotation = 1440;    // eg: TETRIX Motor Encoder
    static final double driveGearReduction = 2.0;     // This is < 1.0 if geared UP
    static final double wheelDiameterInches = 4.0;     // For figuring circumference
    static final double ticksPerInch = (ticksPerRotation * driveGearReduction) / (wheelDiameterInches * 3.1415);
    static final double drivePower = 0.6;
    static final double turnPower = 0.5;

    @Override
    public void runOpMode() {
        //Setup motors
        //Create hardware devices (Using names from configuration)
        DcMotor frontLeftMotor = hardwareMap.get(DcMotor.class, "Motor Drive FL");
        DcMotor frontRightMotor = hardwareMap.get(DcMotor.class, "Motor Drive FR");
        DcMotor rearLeftMotor = hardwareMap.get(DcMotor.class, "Motor Drive BL");
        DcMotor rearRightMotor = hardwareMap.get(DcMotor.class, "Motor Drive BR");
        DcMotor liftMotor = hardwareMap.get(DcMotor.class, "Motor Glyph");

        LynxI2cColorRangeSensor jewelColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Jewel");
        LynxI2cColorRangeSensor frontLeftColor = null;
        LynxI2cColorRangeSensor frontRightColor = null;
        LynxI2cColorRangeSensor rearLeftColor = null;
        LynxI2cColorRangeSensor rearRightColor = null;

        Servo jewelServo = hardwareMap.get(Servo.class, "Servo Jewel");
        Servo leftLiftServo = hardwareMap.servo.get("Servo Glyph L");
        Servo rightLiftServo = hardwareMap.servo.get("Servo Glyph R");

        //Motor Directions
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rearLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rearRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}