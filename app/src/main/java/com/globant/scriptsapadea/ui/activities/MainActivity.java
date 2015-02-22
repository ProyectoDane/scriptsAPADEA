package com.globant.scriptsapadea.ui.activities;

import android.app.Fragment;
import android.view.View;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.ui.fragments.SettingsFragment;

import roboguice.inject.ContentView;

/**
 * @author nicolas.quartieri
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    public void onScriptsClicked(View view) {
        // TODO Create Navigation Module
        Fragment settingsFragment = getFragmentManager().findFragmentById(R.id.fragment_container);
        // TODO
        // Navigate to ScriptsFragment
    }

    public void onPreferenceClick(View view) {
        // TODO Create Navigation Module
        Fragment settingsFragment = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (settingsFragment == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new SettingsFragment()).commit();
        } else {
            getFragmentManager().beginTransaction().remove(settingsFragment).commit();
        }
    }
}
