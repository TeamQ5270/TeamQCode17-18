package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Matthew_Modi on 10/6/2017.
 */

public class MRSensorColor {
    public static int getColor(ColorSensor colorSensor){
        int tempColor = 0;
        if(colorSensor.red() > colorSensor.blue()){
            tempColor = 1;
        }else if(colorSensor.blue() > colorSensor.red()){
            tempColor = 2;
        }
        return tempColor;
    }

    public static double getCertainty(ColorSensor colorSensor){
        double tempCertainty = 0.0;
        if(getColor(colorSensor) == 1){
            tempCertainty = colorSensor.red()/colorSensor.blue();
        }else if(getColor(colorSensor) == 2){
            tempCertainty = colorSensor.blue()/colorSensor.red();
        }
        return tempCertainty;
    }

    public static int getMagnitude(ColorSensor colorSensor){
        int tempMagnitude = 0;
        if (getColor(colorSensor) == 1){
            tempMagnitude = colorSensor.red();
        }else if(getColor(colorSensor) == 2){
            tempMagnitude = colorSensor.blue();
        }
        return tempMagnitude;
    }
}
