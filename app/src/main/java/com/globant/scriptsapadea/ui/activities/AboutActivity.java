package com.globant.scriptsapadea.ui.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.globant.scriptsapadea.R;

import roboguice.inject.ContentView;

/**
 * @author nicolas.quartieri
 */
@ContentView(R.layout.about_container)
public class AboutActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            //navigator.to(new AboutFragment()).noPush().navigate();

            showCustomDialog(this);
        }
    }

    public void showCustomDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.about_screen);

        dialog.show();

    }

    public interface AboutListener {
        void onTakeToAboutScreen();
    }
}
