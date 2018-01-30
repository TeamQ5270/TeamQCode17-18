package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test3;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test3.Utils;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.core.Mat;

/**
 * Created by Matthew_Modi on 12/1/2017.
 */

public class CV implements CameraBridgeViewBase.CvCameraViewListener2{

    static {
        System.loadLibrary("opencv_java3");
    }

    private JavaCameraView cameraView;
    private Activity activity;
    private Context context;
    private View view;

    private Mat hsv = new Mat();
    private Mat rgba = new Mat();
    private Mat thresholded = new Mat();
    private Mat thresholded_rgba = new Mat();

    private Detectors detectors;

    public void init(final Context context, final int camNum){
        detectors = new Detectors();
        detectors.init();
        this.context = context;
        activity = (Activity) context;
        final CameraBridgeViewBase.CvCameraViewListener2 self = this;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cameraView = new JavaCameraView(context, camNum);
                cameraView.setCameraIndex(camNum);
                cameraView.setCvCameraViewListener(self);
                Utils.setInitComplete(true);
            }
        });
    }

    public void enable() {
        cameraView.enableView();
        // finding the resID dynamically allows this class to exist outside of the TeamCode module
        final int resID = context.getResources().getIdentifier("RelativeLayout", "id", context.getPackageName());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup l = (ViewGroup) activity.findViewById(resID); //R.id.RelativeLayout);
                if (view != null) {
                    l.removeView(view);
                }
                l.addView(cameraView);
                view = cameraView;
            }
        });
    }

    public void disable(){
        cameraView.disableView();
        final int resID = context.getResources().getIdentifier("RelativeLayout", "id", context.getPackageName());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //cameraMonitorViewId
                ViewGroup l = (ViewGroup) activity.findViewById(resID); // .id.RelativeLayout);
                if (view != null) {
                    l.removeView(view);
                }
                view = null;
            }
        });
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Utils.setJewelOrder(detectors.getJewelDetector().getCurrentOrder());
        return detectors.processGlyph(inputFrame.rgba());
    }

    @Override
    public void onCameraViewStopped() {

    }
}
