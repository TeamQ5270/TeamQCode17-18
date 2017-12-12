package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test1;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by Matthew_Modi on 11/16/2017.
 */

@Disabled
@Autonomous (name = "Glyph Recognition Test 1")
public class GlyphRecognitionTest extends LinearOpMode{

    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        try{
            File file = new File(Environment.getDataDirectory().getAbsolutePath(), "log.txt");
            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter writer = new PrintWriter(fos);

            CV cv = new CV();

            writer.println("cv declared");

            cv.init(hardwareMap.appContext, CameraView.getInstance());

            writer.println("initialized");

            waitForStart();
            runtime.reset();

            while (opModeIsActive()){
                cv.enable();
                writer.println("enabled");
            }


        }catch(Exception e){
            System.err.print("Error");
        }
    }
}
