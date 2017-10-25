package org.firstinspires.ftc.teamcode.autonomous.vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


public class VuforiaManager {
    private final VuforiaTrackable target;

    public VuforiaManager(int cameraMonitorViewID) {
        //Open the camera with the parameters
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewID);

        //Vuforia Key
        parameters.vuforiaLicenseKey = "AVoUxWP/////AAAAGUy+Zss4vk4tjAZCNIhTWLkz1FOTnb0fR5HxBuBPKHXBjir6ItpKOokFS43cZBSdBtTYCir8UCb0eO7MSrmyUnpYdYBHggjlUYOkDQLIATuKNEh2RbAapPR7+6wtvu2PNsNROTigHDvafUwEvb8tkq+9Je2MwQQYWK+B7ZbQVSq9RsrXziFPkeozfOc81NjNDec6gWiVhtL/2YOz9TGuzN4PZ2YV/0GMXKAJdHLcbASJ8ElXeGkQUq6VdWLTkpAmFRP9lAyHu/5VssBXNQUX55MW+RuSciNkTyg0/BgFLyAOELffB8CzfrBRqJVAvrRAy87NFPhtzVPiNuoas4EPqnYbLtcT/QR/xApGr42xE4EC\n";

        //Continue configuring vuforia
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        //Load the tracking targets
        VuforiaTrackables loadedTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        target = loadedTrackables.get(0);

        loadedTrackables.activate();
    }

    public RelicRecoveryVuMark getvisibleTarget() {
        return RelicRecoveryVuMark.from(target.getListener());
    }

}
