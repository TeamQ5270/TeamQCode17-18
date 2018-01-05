package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Robot {
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

    private Servo relicRotatorServo = null;
    private Servo relicClawServo = null;

    //declare other variables
    private double deadzone = 0.1; //deadzone for joysticks

    //declare glyph claw variables
    //adjust these to adjust how far the claw opens and closes
    private static final double glyphServoMaxPosition = 1.0;
    private static final double glyphServoMinPosition = 0.0;
    private double clawPosition = (glyphServoMinPosition); //start open, with servos at minimum position


    //relic rotator servo
    private static final double relicRotatorServoMaxPosition = 1.0;
    private static final double relicRotatorServoMinPosition = 0.0;
    private double relicRotatorServoPosition = relicRotatorServoMinPosition; //start at one extreme

    //relic claw servo
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

    boolean standardInit;
    boolean driveMotorBrakes;

    //standard constructor
    public Robot() {
        standardInit = true;
    }

    /*
    * This constructor is useful for initializing the robot differently than normal
    * (e.g not having drive motors brake).
    *
    * @param driveMotorBraking set drive motor zero power behavior
    *
    */
    public Robot(boolean driveMotorBraking) {
        standardInit = false;
    }



    public void init(HardwareMap ahwMap) {
        if (standardInit) {
            standardInit(ahwMap);
        } else {
            customInit(ahwMap);
        }
    }

    private void standardInit(HardwareMap ahwMap) {
        hwMap = ahwMap;

        getConfig(ahwMap);

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

    private void customInit(HardwareMap ahwMap) {
        hwMap = ahwMap;

        getConfig(ahwMap);

        if (driveMotorBrakes) {
            motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorRelicArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else if (!driveMotorBrakes) {
            motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorRelicArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    private void getConfig(HardwareMap ahwMap) {
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

        relicRotatorServo = hwMap.servo.get("Servo Relic Rotator");
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
    }



    public DcMotor[] getDriveMotors() {

        //left front -- right front -- left back -- right back
        //keep these in the same order or bad stuff happens

        return new DcMotor[] {
            motorLeftFront,
            motorRightFront,
            motorLeftBack,
            motorRightBack
        };
    }

    public DcMotor[] getLeftDriveMotors() {

        return new DcMotor[] {
                motorLeftFront,
                motorLeftBack
        };
    }

    public DcMotor[] getRightDriveMotors() {
        return new DcMotor[]  {
            motorRightFront,
            motorRightBack
        };
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

    public static double getGlyphServoMinPosition() {
        return glyphServoMinPosition;
    }

    public static double getGlyphServoMaxPosition() {
        return glyphServoMaxPosition;
    }

    public double getClawPosition() {
        return clawPosition;
    }

    public static double getServoIncrement() {
        return servoIncrement;
    }

    public int getRelicLimitExtended() {
        return relicLimitExtended;
    }

    public int getRelicLimitRetracted() {
        return relicLimitRetracted;
    }

    public Servo getRelicRotatorServo() {
        return relicRotatorServo;
    }

    public Servo getRelicClawServo() {
        return relicClawServo;
    }

    public static double getRelicClawServoMinPosition() {
        return relicClawServoMinPosition;
    }

    public double getRelicRotatorServoPosition() {
        return relicRotatorServoPosition;
    }

    public static double getRelicRotatorServoMaxPosition() {
        return relicRotatorServoMaxPosition;
    }

    public static double getRelicRotatorServoMinPosition() {
        return relicRotatorServoMinPosition;
    }

    public DcMotor[] getDiagonalLeft() { return new DcMotor[] {motorLeftFront, motorRightBack}; };

    public DcMotor[] getDiagonalRight() { return new DcMotor[] {motorRightFront, motorLeftBack}; };

    //setters

    public void setClawPosition(double clawPosition) {
        this.clawPosition = clawPosition;
    }

    public void setRelicClawServoPosition(double relicServoPosition) {
        this.relicClawServoPosition = relicServoPosition;
    }

    public void setRelicRotatorServoPosition(double relicRotatorServoPosition) {
        this.relicRotatorServoPosition = relicRotatorServoPosition;
    }
}
