package com.globant.scriptsapadea.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.ui.fragments.ChoosePatientPictureFragment;
import com.globant.scriptsapadea.ui.fragments.ChooseScriptPictureFragment;
import com.globant.scriptsapadea.ui.fragments.CreatePatientFragment;
import com.globant.scriptsapadea.ui.fragments.CreateScriptFragment;
import com.globant.scriptsapadea.ui.fragments.ShowPatientPictureFragment;
import com.globant.scriptsapadea.ui.fragments.ShowScriptPictureFragment;

import roboguice.inject.ContentView;

@ContentView(R.layout.activity_screen)
public class CreatePatientActivity extends BaseActivity implements CreatePatientFragment.OnChangePictureFragmentListener,
        ChoosePatientPictureFragment.OnShowPatientPictureFragmentListener, ShowPatientPictureFragment.OnNextFragmentListener,
        CreateScriptFragment.OnTakeScriptPictureFragmentListener, ChooseScriptPictureFragment.OnShowScriptPictureFragmentListener,
        ShowScriptPictureFragment.OnEditFragmentListener {

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, CreatePatientActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View pullDownView = findViewById(R.id.img_pulldown_button);
        pullDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (savedInstanceState == null)
            navigator.to(new CreatePatientFragment()).noPush().navigate();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.pull_down_to_bottom);
    }

    @Override
    public void onChangePictureFragment(Fragment fragment) {
        navigator.to(fragment).navigate();
    }

    @Override
    public void onCreateScript(Fragment fragment) {
        navigator.to(fragment).navigate();
    }

    @Override
    public void onShowPictureFragment(Fragment fragment) {
        navigator.to(fragment).navigate();
    }

    @Override
    public void onTakeScriptPictureFragment(Fragment fragment) {
        navigator.to(fragment).navigate();
    }

    @Override
    public void onShowScriptPictureFragment(Fragment fragment) {
        navigator.to(fragment).navigate();
    }

    @Override
    public void onEditFragment(Fragment fragment) {
        navigator.to(fragment).navigate();
    }
}
