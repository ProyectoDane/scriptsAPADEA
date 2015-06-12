package com.globant.scriptsapadea.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.ui.fragments.ScreenScriptsSelectorFragment;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_screen)
public class ScreenSelectorActivity extends BaseActivity {

    private static final String PATIENT = "patient";

    @InjectView(R.id.toolbar_actionbar)
    private Toolbar toolbar;

    public static Intent createIntent(Context context, Patient patient) {
        Intent intent = new Intent(context, ScreenSelectorActivity.class);
        intent.putExtra(PATIENT, patient);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Place in BaseActivity
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            ScreenScriptsSelectorFragment fragment;
            if (getIntent().hasExtra(PATIENT)) {
                fragment = ScreenScriptsSelectorFragment.newInstance((Patient)getIntent().getExtras().getSerializable(PATIENT));
                navigator.to(fragment).noPush().navigate();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.pull_down_to_bottom);
    }
}