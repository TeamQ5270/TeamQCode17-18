package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test3;

import com.disnodeteam.dogecv.detectors.CryptoboxDetector;
import com.disnodeteam.dogecv.detectors.GlyphDetector;
import com.disnodeteam.dogecv.detectors.JewelDetector;

import org.opencv.core.Mat;

/**
 * Created by Matthew_Modi on 1/6/2018.
 */

public class Detectors {
    private CryptoboxDetector cryptoboxDetector;
    private GlyphDetector glyphDetector;
    private JewelDetector jewelDetector;

    public void init(){
        cryptoboxDetector = new CryptoboxDetector();
        jewelDetector = new JewelDetector();
        glyphDetector = new GlyphDetector();
    }

    public Mat processCryptoBox(Mat in){
        Mat gray = new Mat();
        Mat out = cryptoboxDetector.processFrame(in, gray);
        return out;
    }

    public Mat processGlyph(Mat in){
        Mat gray = new Mat();
        Mat out = glyphDetector.processFrame(in, gray);
        return out;
    }

    public Mat processJewel(Mat in){
        Mat gray = new Mat();
        Mat out = jewelDetector.processFrame(in, gray);
        return out;
    }

    public JewelDetector getJewelDetector(){
        return jewelDetector;
    }
}
