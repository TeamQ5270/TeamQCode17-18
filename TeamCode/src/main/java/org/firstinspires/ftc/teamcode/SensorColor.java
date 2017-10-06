package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;

/**
 * Created by Matthew_Modi on 10/6/2017.
 */

public class SensorColor {
    public int getColor(LynxI2cColorRangeSensor colorSensor){
        int temp_Color = 0;
        if(colorSensor.red() > colorSensor.blue()){
            temp_Color = 1;
        }else if(colorSensor.blue() > colorSensor.red()){
            temp_Color = 2;
        }
        return temp_Color;
    }

    public double getCertainty(LynxI2cColorRangeSensor colorSensor){
        double temp_Certainty = 0.0;

    }
}
