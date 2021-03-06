package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;


public class Robot {
    //declare motors
 /* Declare OpMode members. */

    //declare motors
    private DcMotor motorLeftFront = null;
    private DcMotor motorRightFront = null;
    private DcMotor motorLeftBack = null;
    private DcMotor motorRightBack = null;
    private DcMotor motorGlyphLift = null;
    private DcMotor motorRelicArm = null;

    //new things
    private DcMotorSimple motorLift = null; //this is the apeture motor
    private DcMotor motorFlipper = null;
    private DcMotor motorIntakeLeft = null;
    private DcMotor motorIntakeRight = null;

    //declare servos
    private Servo leftServo = null;
    private Servo rightServo = null;

    private Servo relicRotatorServo = null;
    private Servo relicRotator2Servo = null;
    private Servo relicClawServo = null;

    private CRServo relicRotatorCR = null;

    private Servo glyphExtender = null;

    private Servo pusher = null;

    //declare other variables
    private double deadzone = 0.1; //deadzone for joysticks

    //declare glyph claw variables
    //adjust these to adjust how far the claw opens and closes
    private static final double glyphServoMaxPosition = 0.5; //closed
    private static final double glyphServoMinPosition = 0.1; //open
    private double clawPosition = glyphServoMinPosition; //start open, with servos at minimum position


    //left servo
    private static final double lGlyphMin = 0.1;
    private static final double lGlyphMax = 0.65;
    private double lGlyphPos = lGlyphMin;

    private static final double rGlyphMin = 0.65;
    private static final double rGlyphMax = 0.1;
    private double rGlyphPos = rGlyphMin;

    //relic rotator servo
    private static final double relicRotatorServoMaxPosition = 1.0;
    private static final double relicRotatorServoMinPosition = 0.0;
    private double relicRotatorServoPosition = relicRotatorServoMinPosition; //start at one extreme

    //relic claw servo
    private static final double relicClawServoMaxPosition = 1.0;
    private static final double relicClawServoMinPosition = 0.0;
    private double relicClawServoPosition = relicClawServoMinPosition;

    //lift final extender limits
    public static final float servoPullPulled = 0.7f;
    public static final float servoPullRetracted = 0.1f;


    //declare general servo variables
    private static final double servoIncrement = 0.008; //adjust this to adjust the speed of all servos

    //lift limit variables
    private final int liftTop = -5300;
    private final int liftBottom = -120;

    private final int relicLimitExtended = 9999;
    private final int relicLimitRetracted = -9999;

    public static final float pushSpeed = 0.2f;


    HardwareMap hwMap = null;

    InitTypes initType;
    boolean driveMotorBrakes;

    //standard constructor
    public Robot(boolean newBot) {
        if (newBot) {
            initType = InitTypes.NEWBOT;
        } else {
            this.initType = InitTypes.OLDBOT;
        }
    }
    public Robot() {
        this(false); //TODO Dont Update this for the new robot
    }

    /*
     * This constructor is useful for initializing the robot differently than normal
     * (e.g not having drive motors brake).
     *
     * @param driveMotorBraking set drive motor zero power behavior
     *
     */
    public Robot(InitTypes initType) {
        this.initType = initType;
    }



    public void init(HardwareMap ahwMap) {
        switch (this.initType) {
            case CLASSIC:
                oldGetConfig(ahwMap);
                standardInit(ahwMap);
                break;
            case CUSTOMSHERIDAN:
                oldGetConfig(ahwMap);
                standardInit(ahwMap);
                customInit(ahwMap);
                break;
            case NEWBOT:
                newInit(ahwMap);
                break;
            case OLDBOT:
                oldGetConfig(ahwMap);
                standardInit(ahwMap);
                oldInit(ahwMap);
            default:
                newInit(ahwMap);
                break;
        }
    }

    private void oldInit(HardwareMap ahwMap) {
        hwMap = ahwMap;
        motorLeftFront = hwMap.dcMotor.get("Motor Drive FL");
        motorLeftBack = hwMap.dcMotor.get("Motor Drive RL");
        motorRightFront = hwMap.dcMotor.get("Motor Drive FR");
        motorRightBack = hwMap.dcMotor.get("Motor Drive RR");

        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotor.Direction.FORWARD);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);

        motorGlyphLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRelicArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //reset lift encoder to zero, then enable lift encoder for lift limiter
        motorGlyphLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorGlyphLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorRelicArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRelicArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void newInit(HardwareMap ahwMap) {
        hwMap = ahwMap;
        motorLeftFront = hwMap.dcMotor.get("Motor Drive FL");
        motorLeftBack = hwMap.dcMotor.get("Motor Drive RL");
        motorRightFront = hwMap.dcMotor.get("Motor Drive FR");
        motorRightBack = hwMap.dcMotor.get("Motor Drive RR");
        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotor.Direction.FORWARD);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);

        motorLift = hwMap.dcMotor.get("Motor Lift");
        motorLift.setDirection(DcMotor.Direction.FORWARD);

        motorIntakeLeft = hwMap.dcMotor.get("Motor Intake L");
        motorIntakeRight = hwMap.dcMotor.get("Motor Intake R");
        motorIntakeLeft.setDirection(DcMotor.Direction.FORWARD);
        motorIntakeRight.setDirection(DcMotor.Direction.REVERSE);

        glyphExtender = hwMap.servo.get("Servo Pull");

        pusher = hwMap.servo.get("Servo Push");
//
//        relicClawServo = hwMap.servo.get("Servo Relic Claw");
//        relicRotatorServo = hwMap.servo.get("Servo Relic Rotator");
//
        motorRelicArm = hwMap.dcMotor.get("Motor Relic Arm");
    }

    private void initRelic(HardwareMap hwMap) {

    }

    private void standardInit(HardwareMap ahwMap) {
        hwMap = ahwMap;
        //set zero power behavior to BRAKE
        motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private void customInit(HardwareMap ahwMap) {
        hwMap = ahwMap;
        if (driveMotorBrakes) {
            motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorGlyphLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorRelicArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else if (!driveMotorBrakes) {
            motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motorGlyphLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorRelicArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    private void oldGetConfig(HardwareMap ahwMap) {
        hwMap = ahwMap;
        //assign appropriate motors from config to the motors
        motorLeftFront = hwMap.dcMotor.get("Motor Drive FL");
        motorLeftBack = hwMap.dcMotor.get("Motor Drive RL");
        motorRightFront = hwMap.dcMotor.get("Motor Drive FR");
        motorRightBack = hwMap.dcMotor.get("Motor Drive RR");
        motorGlyphLift = hwMap.dcMotor.get("Motor Glyph");
        motorRelicArm = hwMap.dcMotor.get("Motor Relic");

        //assign appropriate servos from config to the servos
        leftServo = hwMap.servo.get("Servo Glyph L");
        rightServo = hwMap.servo.get("Servo Glyph R");

        //relicRotatorServo = hwMap.servo.get("Servo Relic Rotator");
        relicClawServo = hwMap.servo.get("Servo Relic Claw");
        relicRotatorCR = hwMap.crservo.get("Servo Relic Rotator");

        relicRotatorCR.setDirection(CRServo.Direction.FORWARD);

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
        motorGlyphLift.setDirection(DcMotor.Direction.FORWARD);
        motorRelicArm.setDirection(DcMotor.Direction.FORWARD);
    }

    public void resetLiftEncoder() {
        motorGlyphLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorGlyphLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

    public CRServo getRelicRotatorCR() {
        return relicRotatorCR;
    }

    public DcMotor[] getLeftDriveMotors() {

        return new DcMotor[] {
                motorLeftFront,
                motorLeftBack
        };
    }

    public DcMotor[] getRightDriveMotors() {
        return new DcMotor[] {
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

    public DcMotor getMotorGlyphLift() {
        return motorGlyphLift;
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

    public DcMotor[] getDiagonalLeft() {
        return new DcMotor[] {
                motorLeftFront,
                motorRightBack
        };
    };

    public DcMotor[] getDiagonalRight() {
        return new DcMotor[] {
                motorRightFront,
                motorLeftBack
        };
    };

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

    public Servo getRelicRotator2Servo() {
        return relicRotator2Servo;
    }

    public DcMotorSimple getMotorLift() {
        return motorLift;
    }

    public DcMotor getMotorFlipper() {
        return motorFlipper;
    }

    public DcMotor getMotorIntakeLeft() {
        return motorIntakeLeft;
    }

    public DcMotor getMotorIntakeRight() {
        return motorIntakeRight;
    }

    public Servo getServoLiftPuller() {
        return glyphExtender;
    }

    public Servo getServoPush() {
        return pusher;
    }

    public void moveIntakeSpeed(float speed) {
        getMotorIntakeLeft().setPower(speed);
        getMotorIntakeRight().setPower(speed);
    }

}