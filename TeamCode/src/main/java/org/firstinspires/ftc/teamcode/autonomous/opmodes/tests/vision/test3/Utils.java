package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test3;

import com.disnodeteam.dogecv.detectors.JewelDetector;

import org.opencv.core.Mat;

/**
 * Created by Matthew_Modi on 12/5/2017.
 */

public class Utils {
    private static boolean initComplete = false;

    private static JewelDetector.JewelOrder jewelOrder = JewelDetector.JewelOrder.UNKNOWN;

    public static boolean getInitComplete(){
        return initComplete;
    }

    public static void setInitComplete(boolean value){
        initComplete = value;
    }

    public static JewelDetector.JewelOrder getJewelOrder(){
        return jewelOrder;
    }

    public static void setJewelOrder(JewelDetector.JewelOrder value){
        jewelOrder = value;
    }
}
