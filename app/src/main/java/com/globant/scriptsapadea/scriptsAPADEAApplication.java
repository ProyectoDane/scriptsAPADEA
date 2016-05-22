package com.globant.scriptsapadea;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import com.squareup.picasso.Picasso;

import roboguice.RoboGuice;

/**
 * @author nicolas.quartieri
 */
public class scriptsAPADEAApplication extends Application  {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DEBUG", "Initializing scriptsAPADEA.");

        initPicasso();
        initRoboGuice();
    }

    private void initRoboGuice() {
        RoboGuice.setUseAnnotationDatabases(false);
        RoboGuice.getOrCreateBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(this), new scriptsAPADEAModule());
        RoboGuice.injectMembers(this, this);
    }

    private void initPicasso() {
        Picasso picasso = new Picasso.Builder(this)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.d("DEBUG", "Error Loading an image: " + exception.getMessage());
                    }
                }).build();

        try {
            Picasso.setSingletonInstance(picasso);
        } catch (IllegalStateException ignored) {
            Log.d("DEBUG", ignored.getMessage());
        }
    }
}
