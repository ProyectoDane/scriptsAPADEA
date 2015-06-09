package com.globant.scriptsapadea.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.navigator.anim.SlidingUpAnimation;
import com.globant.scriptsapadea.ui.fragments.PatientListFragment;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author nicolas.quartieri.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements PatientListFragment.PatientListFragmentListener {

    @InjectView(R.id.toolbar_actionbar)
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Place in BaseActivity
        setSupportActionBar(toolbar);
        
        if (savedInstanceState == null) {
            navigator.to(new PatientListFragment()).noPush().navigate();
        }
    }

    @Override
    public void onNavigateToCreateNewPatient() {
        navigator.to(ScreenPlayActivity.createIntent(this)).withAnimations(new SlidingUpAnimation()).navigate();
    }
}
