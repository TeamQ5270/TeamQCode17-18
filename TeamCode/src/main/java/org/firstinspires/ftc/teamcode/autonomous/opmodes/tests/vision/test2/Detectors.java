package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test2;

import com.disnodeteam.dogecv.detectors.CryptoboxDetector;
import com.disnodeteam.dogecv.detectors.GlyphDetector;
import com.disnodeteam.dogecv.detectors.JewelDetector;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Matthew_Modi on 1/6/2018.
 */

public class Detectors {
    private CryptoboxDetector cryptoboxDetector;
    private JewelDetector jewelDetector;
    private GlyphDetector glyphDetector;

    public void init(){
        cryptoboxDetector = new CryptoboxDetector();
        jewelDetector = new JewelDetector();
        glyphDetector = new GlyphDetector();
    }

    public Mat processJewel(Mat in){
        Mat gray = new Mat();
        jewelDetector.processFrame(in, gray);
        return in;
    }
}
