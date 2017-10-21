
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.*;

@Autonomous(name="MainAutonomous")
public class AutonomousMain extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor rearLeftMotor = null;
    private DcMotor rearRightMotor = null;
    private DcMotor liftMotor = null;



    @Override
    public void runOpMode() {
        //Motor Directions


        //Assign the motors to classes
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor Drive");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor Drive");
        rearLeftMotor = hardwareMap.get(DcMotor.class, "BL Drive");
        rearRightMotor = hardwareMap.get(DcMotor.class, "BR Drive");
        liftMotor = hardwareMap.get(DcMotor.class, "Riser Lift");

        //Bind motor positions to different motors

        //Let user know that robot has been initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Wait For Play, Reset Timer
        waitForStart();
        runtime.reset();

        //Run until stopped
        while(opModeIsActive()){

        }
    }
}
