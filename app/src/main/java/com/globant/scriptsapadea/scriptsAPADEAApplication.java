package com.globant.scriptsapadea;

import android.app.Application;
import android.util.Log;

import roboguice.RoboGuice;

/**
 * @author nicolas.quartieri
 */
public class scriptsAPADEAApplication extends Application  {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DEBUG", "Initializing scriptsAPADEA.");

        // TODO
        RoboGuice.setUseAnnotationDatabases(false);
        RoboGuice.getOrCreateBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(this), new scriptsAPADEAModule());
        RoboGuice.injectMembers(this, this);
    }
}
