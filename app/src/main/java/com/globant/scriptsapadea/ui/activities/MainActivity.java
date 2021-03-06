package com.globant.scriptsapadea.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.navigator.anim.SlidingUpAnimation;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.globant.scriptsapadea.ui.fragments.PatientListFragment;

import javax.inject.Inject;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author nicolas.quartieri.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements PatientListFragment.PatientListFragmentListener {

    @InjectView(R.id.toolbar_actionbar)
    private Toolbar toolbar;

    @Inject
    private SQLiteHelper mDBHelper;

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
        navigator.to(CreatePatientActivity.createIntent(this)).withAnimations(new SlidingUpAnimation()).navigate();
    }

    @Override
    public void onNavigateToPatient(Patient patient) {
        navigator.to(ScriptSelectorActivity.createIntent(this, patient)).withAnimations(new SlidingUpAnimation()).navigate();
    }

    @Override
    public void deletePatient(Patient patient) {
        mDBHelper.deletePatient(patient);
    }
}
