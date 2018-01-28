package org.firstinspires.ftc.teamcode.Utils;

/**
 * Created by Sheridan_Page on 1/16/2018.
 */

public class Utils {

    /*
    * @param x the number you want to map
    * @param in_min lower bound of the current number's possible range
    * @param in_max upper bound of the current number's possible range
    * @param out_min lower bound of the target range
    * @param out_max upper bound of the target range
    *
    * @return x mapped to the second range
    * */

    public static double map(double x, double in_min, double in_max, double out_min, double out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
