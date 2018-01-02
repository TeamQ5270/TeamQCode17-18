package org.firstinspires.ftc.teamcode.teleop.opmodes.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class TeleOpRobot {
    //declare motors
    /* Declare OpMode members. */

    //declare motors
    private DcMotor motorLeftFront = null;
    private DcMotor motorRightFront = null;
    private DcMotor motorLeftBack = null;
    private DcMotor motorRightBack = null;
    private DcMotor motorLift = null;
    private DcMotor motorRelicArm = null;

    //declare servos
    private Servo leftServo = null;
    private Servo rightServo = null;

    private Servo relicServo = null;
    private Servo relicClawServo = null;



    //declare other variables
    private double deadzone = 0.1; //deadzone for joysticks

    //declare glyph claw variables
    //adjust these to adjust how far the claw opens and closes
    private static final double servoMaxPosition = 1.0;
    private static final double servoMinPosition = 0.0;



    private double clawPosition = (servoMinPosition); //start open, with servos at minimum position

    private static final double relicServoMaxPosition = 1.0;
    private static final double relicServoMinPosition = 0.0;
    private double relicServoPosition = relicServoMinPosition; //start at one extreme


    private static final double relicClawServoMaxPosition = 1.0;
    private static final double relicClawServoMinPosition = 0.0;
    private double relicClawServoPosition = relicClawServoMinPosition;


    //declare general servo variables
    private static final double servoIncrement = 0.005; //adjust this to adjust the speed of all servos

    //lift limit variables
    private final int liftTop = -5600;
    private final int liftBottom = 50;

    private final int relicLimitExtended = 7000;
    private final int relicLimitRetracted = 50;

    HardwareMap hwMap = null;

    //constructor
    public TeleOpRobot() {

    }

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        //assign appropriate motors from config to the motors
        motorLeftFront = hwMap.dcMotor.get("Motor Drive FL");
        motorLeftBack = hwMap.dcMotor.get("Motor Drive RL");
        motorRightFront = hwMap.dcMotor.get("Motor Drive FR");
        motorRightBack = hwMap.dcMotor.get("Motor Drive RR");
        motorLift = hwMap.dcMotor.get("Motor Glyph");
        motorRelicArm = hwMap.dcMotor.get("Motor Relic");

        //assign appropriate servos from config to the servos
        leftServo = hwMap.servo.get("Servo Glyph L");
        rightServo = hwMap.servo.get("Servo Glyph R");

        relicServo = hwMap.servo.get("Servo Relic");
        relicClawServo = hwMap.servo.get("Servo Relic Claw");

        //assign motor directions
        //keep the directions as follows or else bad stuff happens:
        //left front: FORWARD
        //left back: FORWARD
        //right front: REVERSE
        //right back: REVERSE
        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotor.Direction.FORWARD);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);
        motorLift.setDirection(DcMotor.Direction.FORWARD);
        motorRelicArm.setDirection(DcMotor.Direction.FORWARD);

        //set zero power behavior to BRAKE
        motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRelicArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //reset lift encoder to zero, then enable lift encoder for lift limiter
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorRelicArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRelicArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public DcMotor[] getDriveMotors() {

        //left front -- right front -- left back -- right back

        DcMotor[] driveMotors = {
            motorLeftFront,
            motorRightFront,
            motorLeftBack,
            motorRightBack
        };

        return driveMotors;
    }

    public DcMotor[] getLeftDriveMotors() {

        DcMotor[] leftDriveMotors = {
            motorLeftFront,
            motorLeftBack
        };

        return leftDriveMotors;
    }

    public DcMotor[] getRightDriveMotors() {
        DcMotor[] rightDriveMotors = {
                motorRightFront,
                motorRightBack
        };

        return rightDriveMotors;
    }

    public double getDeadzone() {
        return deadzone;
    }

    public int getLiftTop() {
        return liftTop;
    }

    public int getLiftBottom() {
        return liftBottom;
    }

    public DcMotor getMotorLift() {
        return motorLift;
    }

    public DcMotor getMotorRelicArm() {
        return motorRelicArm;
    }

    public Servo getRightServo() {
        return rightServo;
    }

    public Servo getLeftServo() {

        return leftServo;
    }



    public double getRelicClawServoPosition() {
        return relicClawServoPosition;
    }

    public static double getRelicClawServoMaxPosition() {

        return relicClawServoMaxPosition;
    }
}
