package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@Autonomous(name = "Modern Robotics Color Sensor Display")
public class MRColorSensorTest extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    String colorSensorDeviceName = "Test Color Sensor";
    ColorSensor colorSensor;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        //Initialize the color sensor using colorSensorDeviceName
        colorSensor = hardwareMap.colorSensor.get(colorSensorDeviceName);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        telemetry.addData("Red: ", colorSensor.red());
        telemetry.addData("Green: ", colorSensor.green());
        telemetry.addData("Blue: ", colorSensor.blue());


        if(colorSensor.red() > colorSensor.blue()) {
            telemetry.addData("RED", colorSensor.red());
        }
        else {
            telemetry.addData("BLUE", colorSensor.blue());
        }
    }
}