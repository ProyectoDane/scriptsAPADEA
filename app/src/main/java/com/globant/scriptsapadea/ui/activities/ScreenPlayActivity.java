package com.globant.scriptsapadea.ui.activities;


import android.os.Bundle;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.ui.fragments.PictureFragment;
import com.globant.scriptsapadea.ui.fragments.ScreenPlayFragment;

import roboguice.inject.ContentView;

@ContentView(R.layout.activity_screenplay)
public class ScreenPlayActivity extends BaseActivity implements ScreenPlayFragment.OnScreenplayChangeFragmentListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null)
            navigator.to(new ScreenPlayFragment()).noPush().navigate();
    }

    @Override
    public void onNextButtonClicked() {
        navigator.to(new PictureFragment()).navigate();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.pull_down_to_bottom);
    }
}
