package com.globant.scriptsapadea;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;
import roboguice.RoboGuice;

/**
 * @author nicolas.quartieri
 */
public class scriptsAPADEAApplication extends Application  {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Log.d("DEBUG", "Initializing scriptsAPADEA.");

        context = this;

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
                        final Handler handler  = new Handler();
                        final AlertDialog alert = new AlertDialog.Builder(getApplicationContext()).setTitle("Error al cargar la imagen!")
                                .setIcon(R.drawable.teayudo_usuario)
                                .show();
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (alert.isShowing()) {
                                    alert.dismiss();
                                }
                            }
                        };
                        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                handler.removeCallbacks(runnable);
                            }
                        });

                        handler.postDelayed(runnable, 2000);

                        Log.d("DEBUG", "Error Loading an image: " + exception.getMessage());
                    }
                }).build();

        try {
            Picasso.setSingletonInstance(picasso);
        } catch (IllegalStateException ignored) {
            Log.d("DEBUG", ignored.getMessage());
        }
    }

    public static Context getContext(){
        return context;
    }
}
