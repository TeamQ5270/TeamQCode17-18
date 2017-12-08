package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test2;

import org.opencv.core.Mat;

/**
 * Created by Matthew_Modi on 12/5/2017.
 */

public class Utils {
    private static boolean initComplete = false;
    private static Mat currentMat = null;

    public static boolean getInitComplete(){
        return initComplete;
    }

    public static void setInitComplete(boolean value){
        initComplete = value;
    }

    public static Mat getCurrentMat(){
        return currentMat;
    }

    public static void setCurrentMat(Mat input){
        currentMat = input;
    }
}
