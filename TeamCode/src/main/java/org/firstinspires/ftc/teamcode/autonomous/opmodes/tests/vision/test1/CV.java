package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test1;

import org.firstinspires.ftc.teamcode.res.opencv.core.Core;
import org.firstinspires.ftc.teamcode.res.opencv.core.Mat;
import org.firstinspires.ftc.teamcode.res.opencv.core.Scalar;
import org.firstinspires.ftc.teamcode.res.opencv.imgproc.Imgproc;

/**
 * Created by Matthew_Modi on 11/29/2017.
 */

public class CV extends Pipeline{
    private Mat hsv = new Mat();
    private Mat thresholded = new Mat();
    private Mat thresholded_rgba = new Mat();

    @Override
    public Mat processFrame(Mat rgba, Mat gray){
        Imgproc.cvtColor(rgba, hsv, Imgproc.COLOR_RGB2HSV, 3);

        Core.inRange(hsv, new Scalar(150, 150, 150), new Scalar(200, 200, 200), thresholded);

        Imgproc.cvtColor(thresholded, thresholded_rgba, Imgproc.COLOR_GRAY2BGR);
        return thresholded_rgba;
    }
}
