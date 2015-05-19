package com.globant.scriptsapadea.ui.activities;

import android.os.Bundle;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.navigator.Navigator;
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

    protected Navigator navigator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigator = new Navigator(this, savedInstanceState, R.id.fragment_container, new Navigator.NavigationListener() {
            // TODO
        });
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
