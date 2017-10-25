
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.autonomous.utilities.ServoFlipper;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Autonomous(name="Main Autonomous")
public class AutonomousMain extends LinearOpMode {

    //How long the game has run
    private ElapsedTime runtime = new ElapsedTime();


    //TODO Also these should probably be moved into the runOpMode method for more conciseness and efficiency
    //Setup servo management
    //Servo position flag
    ServoFlipper.ServoPosition currentPosition = ServoFlipper.ServoPosition.OUT; //defaults to the servos being extended
    //Servo position constants
    private final double servoMaxPosition = 1.0;
    private final double servoMinPosition = 0.0;

    //Initialize HardwareMaps
    //Motors
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor rearLeftMotor = null;
    private DcMotor rearRightMotor = null;
    private DcMotor liftMotor = null;

    //Servos
    private Servo leftServo = null;
    private Servo rightServo = null;

    //Sensors

    //Vuforia
    private VuforiaManager vuforiaManager = new VuforiaManager(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));

    @Override
    public void runOpMode() {
        //Assign motors to hardware devices (Using names from configuration)
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FL Drive");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FR Drive");
        rearLeftMotor = hardwareMap.get(DcMotor.class, "BL Drive");
        rearRightMotor = hardwareMap.get(DcMotor.class, "BR Drive");
        liftMotor = hardwareMap.get(DcMotor.class, "Riser Lift");

        //Motor Directions


        //Bind motor positions to different motors
        //front motors
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //rear motors
        rearLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rearRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //glyph lift motor
        liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        //Set up servos/servo manager
        leftServo = hardwareMap.servo.get("Servo Lift L");
        rightServo = hardwareMap.servo.get("Servo Lift R");
        //This is used to simplify flipping the servos in and out
        Servo[] servos = new Servo[] {leftServo, rightServo};
        //Setup the constant positions of the servos in the manager (positions are left servo, then right servo ^^^)
        ServoFlipper.setPositionsInLocal(new double[] {servoMaxPosition,servoMinPosition});
        ServoFlipper.setPositionsOutLocal(new double[] {servoMinPosition,servoMaxPosition});

        /*
        Detect Vuforia Target - run until the target is found (as of now)
        TODO Implement a timer so that the robot will commence autonomous if a target is not found - maybe move this code into the autonomous script, and use a timer there?
        */
        while (true) {
            if (!(vuforiaManager.getvisibleTarget() == RelicRecoveryVuMark.UNKNOWN)) break;
        }

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        //Run until stopped
        //TODO this wont stop if the user presses the stop button
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
