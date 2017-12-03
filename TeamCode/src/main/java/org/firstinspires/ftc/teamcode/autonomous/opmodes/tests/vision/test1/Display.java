package org.firstinspires.ftc.teamcode.autonomous.opmodes.tests.vision.test1;

import android.content.Context;
import android.view.View;

/**
 * Created by Matthew_Modi on 11/29/2017.
 */

public interface Display {
    void setCurrentView(Context context, View view);
    void removeCurrentView(Context context);
}