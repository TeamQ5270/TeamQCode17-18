
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiServo;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Autonomous(name="Main Autonomous")
public class AutonomousMain extends LinearOpMode {

    //How long the game has run
    private final ElapsedTime runtime = new ElapsedTime();

    //Vuforia manager
    private final VuforiaManager vuforiaManager = new VuforiaManager(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));


    @Override
    public void runOpMode() {
        //TODO read configuration from a file
        //Assign objects to hardware devices
        //Assign motors to hardware devices (Using names from configuration)
        DcMotor frontLeftMotor = hardwareMap.get(DcMotor.class, "FL Drive");
        DcMotor frontRightMotor = hardwareMap.get(DcMotor.class, "FR Drive");
        DcMotor rearLeftMotor = hardwareMap.get(DcMotor.class, "BL Drive");
        DcMotor rearRightMotor = hardwareMap.get(DcMotor.class, "BR Drive");
        DcMotor liftMotor = hardwareMap.get(DcMotor.class, "Riser Lift");
        //Assign sensors
        LynxI2cColorRangeSensor jewelSensor = hardwareMap.get(LynxI2cColorRangeSensor.class, "Jewel Color");

        //Set up motors
        //Motor Directions
        //front motors
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //rear motors
        rearLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rearRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //glyph lift motor
        liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        //Set up servos/servo manager
        //Set up manager
        MultiServo.ServoPosition currentPosition = MultiServo.ServoPosition.OUT; //defaults to the servos being extended
        //Jewel
        Servo jewelServo = hardwareMap.servo.get("Jewel Servo");
        //Lift
        //Assign servos
        Servo leftLiftServo = hardwareMap.servo.get("Servo Lift L");
        Servo rightLiftServo = hardwareMap.servo.get("Servo Lift R");
        //This is used to simplify flipping the servos in and out
        Servo[] servos = new Servo[] {leftLiftServo, rightLiftServo};
        //Setup the constant positions of the liftservos in the manager (positions are left servo, then right servo ^^^)
        double servoMinPosition = 0.0;
        double servoMaxPosition = 1.0;
        MultiServo.setPositionsInLocal(new double[] {servoMaxPosition, servoMinPosition});
        MultiServo.setPositionsOutLocal(new double[] {servoMinPosition, servoMaxPosition});

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /*
        Detect Vuforia Target - run until the target is found or the opmode starts
        TODO there could be a possible bug with using isStarted, needs to be tested
        */
        while (true) {
            if (!(vuforiaManager.getvisibleTarget() == RelicRecoveryVuMark.UNKNOWN)&&!isStarted()) break;
        }

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        //Run until stopped
        //TODO this wont stop if the user presses the stop button - make sure to check and see if the robot has to stop
        while(opModeIsActive()){
            //Autonomous Instructions
            //Check to see if a vumark was picked up, set a flag if not
            boolean vuMarkFound = vuforiaManager.getvisibleTarget() == RelicRecoveryVuMark.UNKNOWN;

            //TODO find a way to get the position of the robot on the field


            //We need to handle a different set of instructions if the vuMark cant be found
            if (!vuMarkFound) {

            }
            else {

            }

            //Exit Loop
            break;
        }
        //End OpMode
        stop();
    }
}
