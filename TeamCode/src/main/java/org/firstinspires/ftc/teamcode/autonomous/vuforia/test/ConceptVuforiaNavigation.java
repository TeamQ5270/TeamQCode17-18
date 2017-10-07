
package org.firstinspires.ftc.teamcode.autonomous.vuforia.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name="Concept: Vuforia Navigation", group ="Concept")

public class ConceptVuforiaNavigation extends LinearOpMode {

    public static final String TAG = "Vuforia Navigation Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    @Override public void runOpMode() {
        //Open the camera with the parameters
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        //Vuforia Key
        parameters.vuforiaLicenseKey = "AVoUxWP/////AAAAGUy+Zss4vk4tjAZCNIhTWLkz1FOTnb0fR5HxBuBPKHXBjir6ItpKOokFS43cZBSdBtTYCir8UCb0eO7MSrmyUnpYdYBHggjlUYOkDQLIATuKNEh2RbAapPR7+6wtvu2PNsNROTigHDvafUwEvb8tkq+9Je2MwQQYWK+B7ZbQVSq9RsrXziFPkeozfOc81NjNDec6gWiVhtL/2YOz9TGuzN4PZ2YV/0GMXKAJdHLcbASJ8ElXeGkQUq6VdWLTkpAmFRP9lAyHu/5VssBXNQUX55MW+RuSciNkTyg0/BgFLyAOELffB8CzfrBRqJVAvrRAy87NFPhtzVPiNuoas4EPqnYbLtcT/QR/xApGr42xE4EC\n";

        //Continue configuring vuforia
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

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