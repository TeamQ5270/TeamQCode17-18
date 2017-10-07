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

public class VuforiaMg {
    public static final String TAG = "Vuforia Navigation Sample";

    OpenGLMatrix lastLocation = null;


    VuforiaLocalizer vuforia;
    VuforiaTrackables loadedTrackables;
    VuforiaTrackable target;
    List<VuforiaTrackable> allTrackables;

    public VuforiaMg(int cameraMonitorViewID) {
        //Open the camera with the parameters
        int cameraMonitorViewId = cameraMonitorViewID;
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        //Vuforia Key
        parameters.vuforiaLicenseKey = "AVoUxWP/////AAAAGUy+Zss4vk4tjAZCNIhTWLkz1FOTnb0fR5HxBuBPKHXBjir6ItpKOokFS43cZBSdBtTYCir8UCb0eO7MSrmyUnpYdYBHggjlUYOkDQLIATuKNEh2RbAapPR7+6wtvu2PNsNROTigHDvafUwEvb8tkq+9Je2MwQQYWK+B7ZbQVSq9RsrXziFPkeozfOc81NjNDec6gWiVhtL/2YOz9TGuzN4PZ2YV/0GMXKAJdHLcbASJ8ElXeGkQUq6VdWLTkpAmFRP9lAyHu/5VssBXNQUX55MW+RuSciNkTyg0/BgFLyAOELffB8CzfrBRqJVAvrRAy87NFPhtzVPiNuoas4EPqnYbLtcT/QR/xApGr42xE4EC\n";

        //Continue configuring vuforia
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        //Load the tracking targets
        loadedTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        target = loadedTrackables.get(0);

        /** For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(loadedTrackables);

        loadedTrackables.activate();
    }


    public RelicRecoveryVuMark getvisibleTarget() {
        return RelicRecoveryVuMark.from((VuforiaTrackableDefaultListener)target.getListener());
    }

}
