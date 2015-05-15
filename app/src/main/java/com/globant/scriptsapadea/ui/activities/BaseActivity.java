package com.globant.scriptsapadea.ui.activities;

import android.os.Bundle;

import com.google.inject.Inject;
import com.squareup.otto.Bus;

import roboguice.activity.RoboActionBarActivity;

/**
 * Base Activity class provides access to the event bus.
 *
 * @author nicolas.quartieri
 */
public abstract class BaseActivity extends RoboActionBarActivity {

    @Inject
    private Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
