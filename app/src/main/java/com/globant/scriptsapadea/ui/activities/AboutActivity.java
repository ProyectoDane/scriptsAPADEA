package com.globant.scriptsapadea.ui.activities;

import android.os.Bundle;

import com.globant.scriptsapadea.R;

import roboguice.inject.ContentView;

/**
 * Created by nicolas.quartieri
 */
@ContentView(R.layout.about_container)
public class AboutActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            navigator.to(new AboutFragment()).navigate();
        }
    }
}
