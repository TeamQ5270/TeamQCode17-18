
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;

@Autonomous(name="MainAutonomous")
public class AutonomousMain extends LinearOpMode {

    //How long the game has run
    private ElapsedTime runtime = new ElapsedTime();

    //Initialize HardwareMaps
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor rearLeftMotor = null;
    private DcMotor rearRightMotor = null;
    private DcMotor liftMotor = null;

    //Initialize Misc
     private RelicRecoveryVuMark targetImage = null;

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


        //Detect Vuforia Target

        VuforiaManager vuforiaManager = new VuforiaManager(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        targetImage = vuforiaManager.getvisibleTarget();

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Wait For Play, Start Timer
        waitForStart();
        runtime.reset();

        //Run until stopped
        while(opModeIsActive()){
            //Autonomous Instructions


            //Exit Loop
            break;
        }
        //End OpMode
        stop();
    }
}
