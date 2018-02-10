
package org.firstinspires.ftc.teamcode.autonomous.opmodes;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Utils.InitTypes;
import org.firstinspires.ftc.teamcode.Utils.Robot;
import org.firstinspires.ftc.teamcode.autonomous.utilities.AutoConstants;
import org.firstinspires.ftc.teamcode.autonomous.utilities.MultiMotor;
import org.firstinspires.ftc.teamcode.autonomous.utilities.SubFunctions;
import org.firstinspires.ftc.teamcode.autonomous.vuforia.VuforiaManager;


@Autonomous(name="Autonomous NOT AUDIENCE Side")
public class AutonomousPlayerSide extends LinearOpMode {
    @Override
    public void runOpMode() {
        AutonomousMain.runOpMode(this,true); //run true, as that is the side for the non-audience
    }
}
