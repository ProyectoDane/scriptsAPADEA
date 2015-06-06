package com.globant.scriptsapadea.ui.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.interfaces.OnScreenplayChangeFragmentListener;
import com.globant.scriptsapadea.ui.fragments.ScreenplayFragment;

import roboguice.inject.ContentView;

@ContentView(R.layout.activity_screenplay)
public class ScreenplayActivity extends BaseActivity implements OnScreenplayChangeFragmentListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null)
            navigator.to(new ScreenplayFragment()).noPush().navigate();
    }

    @Override
    public void onChangeFragment(Fragment fragment) {
        navigator.to(fragment).navigate();
    }

}
