package com.globant.scriptsapadea.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.ui.fragments.PictureFragment;
import com.globant.scriptsapadea.ui.fragments.TakePictureFragment;
import com.globant.scriptsapadea.ui.fragments.ScreenPlayFragment;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_screen)
public class ScreenPlayActivity extends BaseActivity implements ScreenPlayFragment.OnScreenplayChangeFragmentListener,PictureFragment.OnSetPictureFragmentImageListener  {

    @InjectView(R.id.toolbar_actionbar)
    private Toolbar toolbar;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ScreenPlayActivity.class);

        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Place in BaseActivity
        setSupportActionBar(toolbar);

        if (savedInstanceState == null)
            navigator.to(new ScreenPlayFragment()).noPush().navigate();
    }

    @Override
    public void onNextButtonClicked(String name) {
        navigator.to(TakePictureFragment.newInstance(name)).navigate();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.pull_down_to_bottom);
    }

    @Override
    public void onSetImage(Bundle imageBundle) {
        navigator.to(PictureFragment.newInstance(imageBundle)).navigate();
    }
}
