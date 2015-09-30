package com.globant.scriptsapadea.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.ui.fragments.AboutFragment;

import roboguice.inject.ContentView;

/**
 * Created by nicolas.quartieri
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
            navigator.to(new AboutFragment()).noPush().navigate();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.pull_down_to_bottom);
    }
}
