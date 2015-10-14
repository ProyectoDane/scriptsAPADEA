package com.globant.scriptsapadea.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.navigator.anim.SlidingLeftAnimation;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.globant.scriptsapadea.ui.fragments.ScreenScriptsSelectorFragment;

import javax.inject.Inject;

import roboguice.inject.ContentView;

/**
 * Activity build to contain the script grid selection screen.
 *
 * @author nicolas.quartieri
 */
@ContentView(R.layout.activity_screen)
public class ScriptSelectorActivity extends BaseActivity implements ScreenScriptsSelectorFragment.ScreenScriptSelectorListener {

    private static final String PATIENT = "patient";

    @Inject
    private SQLiteHelper mDBHelper;

    public static Intent createIntent(Context context, Patient patient) {
        Intent intent = new Intent(context, ScriptSelectorActivity.class);
        intent.putExtra(PATIENT, patient);
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

        if (savedInstanceState == null) {
            ScreenScriptsSelectorFragment fragment;
            if (getIntent().hasExtra(PATIENT)) {
                fragment = ScreenScriptsSelectorFragment.newInstance((Patient)getIntent().getExtras().getSerializable(PATIENT));
                navigator.to(fragment).noPush().navigate();
            }
        }
    }

    @Override
    public void onNavigateToScriptSlider(Script script) {
        navigator.to(ScreenSliderActivity.createIntent(this, script)).withAnimations(new SlidingLeftAnimation()).navigate();
    }

    public void deleteDBScript(Script script) {
        mDBHelper.deleteScript(script);
    }

    public void editDBScript(Script script) {
        // TODO
    }

    public void copyDBScript(Script script) {
        // TODO
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.pull_down_to_bottom);
    }
}
