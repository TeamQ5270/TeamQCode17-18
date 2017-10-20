package org.firstinspires.ftc.teamcode.autonomous.utilities;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Matthew_Modi on 10/6/2017.
 */

public class MRSensorColor {
    public static int getColor(ColorSensor colorSensor){
        int temp_Color = 0;
        if(colorSensor.red() > colorSensor.blue()){
            temp_Color = 1;
        }else if(colorSensor.blue() > colorSensor.red()){
            temp_Color = 2;
        }
        return temp_Color;
    }

    public static double getCertainty(ColorSensor colorSensor){
        double temp_Certainty = 0.0;
        if(getColor(colorSensor) == 1){
            temp_Certainty = colorSensor.red()/colorSensor.blue();
        }else if(getColor(colorSensor) == 2){
            temp_Certainty = colorSensor.blue()/colorSensor.red();
        }
        return temp_Certainty;
    }

    public static int getMagnitude(ColorSensor colorSensor){
        int temp_Magnitude = 0;
        if (getColor(colorSensor) == 1){
            temp_Magnitude = colorSensor.red();
        }else if(getColor(colorSensor) == 2){
            temp_Magnitude = colorSensor.blue();
        }
        return temp_Magnitude;
    }
}
