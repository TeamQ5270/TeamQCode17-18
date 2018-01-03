package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test2;

import org.opencv.core.Mat;

/**
 * Created by Matthew_Modi on 12/5/2017.
 */

public class Utils {
    private static boolean initComplete = false;
    private static boolean frameSet = false;

    private static double[] hsvc = new double[3];
    private static double[] hsvr = new double[3];


    private static Mat currentMat = null;

    public static int getHeightCenter(){
        return (int) (currentMat.height() / 2);
    }

    public static int getWidthCenter(){
        return (int) (currentMat.width() / 2);
    }

    public static boolean getFrameSet(){
        return frameSet;
    }

    public static void setFrameSet(boolean value){
        frameSet = value;
    }

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

    /**
     *
     * @param hsvc double[3] The center (desired) value for hsv. 0-255, -1 means no change.
     */
    public static void setHsvc(double[] hsvc){
        for(int i = 0; i < 3; i++){
            if(hsvc[i] >= 0 && hsvc[i] < 256){
                Utils.hsvc[i] = hsvc[i];
            }
        }
    }

    public static double[] getHsvc(){
        return Utils.hsvc;
    }

    /**
     *
     * @param hsvr double[3] How far the desired hsv stray from the hsv center. 0-255, -1 means no change.
     */
    public static void setHsvr(double[] hsvr){
        for(int i = 0; i < 3; i++){
            if(hsvr[i] >= 0 && hsvr[i] < 256){
                if(hsvc[i] + hsvr[i] > 256){
                    Utils.hsvr[i] = 255 - hsvc[i];
                }else if(hsvc[i] - hsvr[i] < 0){
                    Utils.hsvr[i] = hsvc[i];
                }
                Utils.hsvr[i] = hsvr[i];
            }
        }
    }

    public static double[] getHsvr(){
        return Utils.hsvr;
    }

    /**
     *
     * @return double[3] The lower bound for desired hsvs. This is used for hsv ranges.
     */
    public static double[] getHsvLower(){
        double[] output = new double[3];
        for(int i = 0; i < 3; i++){
            output[i] = hsvc[i] - hsvr[i];
        }
        return output;
    }

    /**
     *
     * @return double[3] The lower bound for desired hsvs. This is used for hsv ranges.
     */
    public static double[] getHsvUpper(){
        double[] output = new double[3];
        for(int i = 0; i < 3; i++){
            output[i] = hsvc[i] + hsvr[i];
        }
        return output;
    }

    public static String toString(double[] input){
        String output = "[ ";
        for(double val: input){
            output += Double.toString((int) val) + " , ";
        }
        return output.substring(0, output.length() - 2) + " ]";
    }
}
