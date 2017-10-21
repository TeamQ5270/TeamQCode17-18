
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.*;



import static com.sun.tools.javac.main.Option.S;

@Autonomous(name="MainAutonomous")
public class AutonomousMain extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FL = null;
    private DcMotor FR = null;
    private DcMotor RL = null;
    private DcMotor RR = null;
    private DcMotor L = null;



    @Override
    public void runOpMode() {
        //Initialize Telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Motor Initialzation
        FL = hardwareMap.get(DcMotor.class, "FL Drive");
        FR = hardwareMap.get(DcMotor.class, "FR Drive");
        RL = hardwareMap.get(DcMotor.class, "BL Drive");
        RR = hardwareMap.get(DcMotor.class, "BR Drive");
        L = hardwareMap.get(DcMotor.class, "Riser Lift");

        //Motor Directions

        //Wait For Play, Reset Timer
        waitForStart();
        runtime.reset();

        //Run until stop
        while(opModeIsActive()){

        }
    }
}
