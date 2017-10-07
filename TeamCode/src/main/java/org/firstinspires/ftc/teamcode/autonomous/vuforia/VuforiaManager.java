package org.firstinspires.ftc.teamcode.autonomous.vuforia;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John_Kesler on 10/6/2017.
 */

public class VuforiaManager {
    public static final String TAG = "Vuforia Navigation Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    public void initialize() {
        //Open the camera with the parameters
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        //Vuforia Key
        parameters.vuforiaLicenseKey = "AVoUxWP/////AAAAGUy+Zss4vk4tjAZCNIhTWLkz1FOTnb0fR5HxBuBPKHXBjir6ItpKOokFS43cZBSdBtTYCir8UCb0eO7MSrmyUnpYdYBHggjlUYOkDQLIATuKNEh2RbAapPR7+6wtvu2PNsNROTigHDvafUwEvb8tkq+9Je2MwQQYWK+B7ZbQVSq9RsrXziFPkeozfOc81NjNDec6gWiVhtL/2YOz9TGuzN4PZ2YV/0GMXKAJdHLcbASJ8ElXeGkQUq6VdWLTkpAmFRP9lAyHu/5VssBXNQUX55MW+RuSciNkTyg0/BgFLyAOELffB8CzfrBRqJVAvrRAy87NFPhtzVPiNuoas4EPqnYbLtcT/QR/xApGr42xE4EC\n";

        //Continue configuring vuforia
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

    }


    @Override public void runOpMode() {

        //Load the tracking targets
        VuforiaTrackables loadedTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable target = loadedTrackables.get(0);

        /** For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(loadedTrackables);

        //mm to inches conversion factor ratios
        float mmPerInch        = 25.4f;
        float mmBotWidth       = 18 * mmPerInch;            // ... or whatever is right for your robot
        float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels


        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();


        //Activate the tracking set
        loadedTrackables.activate();
        while (opModeIsActive()) {
            //Check and see what trackables are visible
            for (VuforiaTrackable trackable : allTrackables) {
                telemetry.addData(trackable.getName(), RelicRecoveryVuMark.from((VuforiaTrackableDefaultListener)trackable.getListener()).toString());
            }

            //Update the telemetry
            telemetry.update();
        }
    }

}
