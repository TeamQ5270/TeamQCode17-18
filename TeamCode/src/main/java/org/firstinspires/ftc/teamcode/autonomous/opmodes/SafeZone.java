
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiServo;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Autonomous(name="Safe Zone")
public class SafeZone extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    //Vuforia manager

    //Initialize Misc

    @Override
    public void runOpMode() {
        //TODO read configuration from a file

        //Create hardware devices (Using names from configuration)
        DcMotor frontLeftMotor = hardwareMap.get(DcMotor.class, "Motor Drive FL");
        DcMotor frontRightMotor = hardwareMap.get(DcMotor.class, "Motor Drive FR");
        DcMotor rearLeftMotor = hardwareMap.get(DcMotor.class, "Motor Drive RL");
        DcMotor rearRightMotor = hardwareMap.get(DcMotor.class, "Motor Drive RR");
//        DcMotor liftMotor = hardwareMap.get(DcMotor.class, "Motor Glyph");

//        LynxI2cColorRangeSensor jewelColor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Sensor Color Jewel");
//        LynxI2cColorRangeSensor frontLeftColor = null;
//        LynxI2cColorRangeSensor frontRightColor = null;
//        LynxI2cColorRangeSensor rearLeftColor = null;
//        LynxI2cColorRangeSensor rearRightColor = null;
//
//        Servo jewelServo = hardwareMap.get(Servo.class, "Servo Jewel");
//        Servo leftLiftServo = hardwareMap.servo.get("Servo Glyph L");
//        Servo rightLiftServo = hardwareMap.servo.get("Servo Glyph R");

        //Motor Directions
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rearLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rearRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        //Set up Servos/ServoManager
//        MultiServo.ServoPosition currentPosition = MultiServo.ServoPosition.OUT; //defaults to the servos being extended

        //Create servo array(This is used to simplify flipping the servos in and out)
//        Servo[] servos = new Servo[] {leftLiftServo, rightLiftServo};

        //Setup the constant positions of the liftservos in the manager (positions are left servo, then right servo ^^^)
//        double servoMinPosition = 0.0;
//        double servoMaxPosition = 1.0;
//        MultiServo.setPositionsInLocal(new double[] {servoMaxPosition, servoMinPosition});
//        MultiServo.setPositionsOutLocal(new double[] {servoMinPosition, servoMaxPosition});

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /*
        Detect Vuforia Target - run until the target is found or the opmode starts
        TODO there could be a possible bug with using isStarted, needs to be tested
        Make sure VuforiaManager is during init so that it doesn't error during app start. Also the status of the camera might change and only be availible when we determine so (and press init).
        */

        VuforiaManager vuforiaManager = new VuforiaManager(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        RelicRecoveryVuMark targetImage = RelicRecoveryVuMark.UNKNOWN;
        while (true) {
            if (!(vuforiaManager.getvisibleTarget() == RelicRecoveryVuMark.UNKNOWN)&&!isStarted()){
                telemetry.addData("Vuforia Target: ", targetImage.toString());
                telemetry.update();
                break;
            }
            targetImage = vuforiaManager.getvisibleTarget();
        }
        telemetry.addData("Vuforia Target: ", targetImage.toString());

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        //Run until stopped
        //TODO this wont stop if the user presses the stop button - make sure to check and see if the robot has to stop
        while(opModeIsActive()){
            /*
                1. Find BB Color
                2. Find Side
                3. Run Instructions based on vumark
             */

//            frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            rearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            rearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//            frontLeftMotor.setTargetPosition(2000);
//            frontRightMotor.setTargetPosition(-2000);
//            rearLeftMotor.setTargetPosition(-2000);
//            rearRightMotor.setTargetPosition(2000);
//
//            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            rearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            rearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            frontLeftMotor.setPower(1.0);
            frontRightMotor.setPower(1.0);
            rearLeftMotor.setPower(1.0);
            rearRightMotor.setPower(1.0);
        }
        //End OpMode
        stop();
    }
}
