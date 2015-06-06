package com.globant.scriptsapadea.ui.activities;


import android.content.Intent;
import android.os.Bundle;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.navigator.anim.SlidingUpAnimation;
import com.globant.scriptsapadea.ui.fragments.PatientListFragment;

import roboguice.inject.ContentView;

/**
 * @author nicolas.quartieri.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements PatientListFragment.PatientListFragmentListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            navigator.to(new PatientListFragment()).noPush().navigate();
        }
    }

    @Override
    public void onNavigateToCreateNewPatient() {
        navigator.to(new Intent(this, ScreenPlayActivity.class)).withAnimations(new SlidingUpAnimation()).navigate();
    }
}
