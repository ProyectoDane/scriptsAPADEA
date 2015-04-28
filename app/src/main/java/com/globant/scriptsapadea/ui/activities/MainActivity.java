package com.globant.scriptsapadea.ui.activities;

import android.app.Fragment;
import android.view.View;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.ui.fragments.PrincipalFragment;
import com.globant.scriptsapadea.ui.fragments.ScriptsSelectorFragment;
import com.globant.scriptsapadea.ui.fragments.SettingsFragment;

import roboguice.inject.ContentView;

/**
 * @author nicolas.quartieri
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    public void onScriptsClicked(View view) {
        // TODO Create Navigation Module
        Fragment container = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (container == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new ScriptsSelectorFragment()).commit();
        } else {
            getFragmentManager().beginTransaction().remove(container).commit();
        }
    }

    public void onPreferenceClick(View view) {
        // TODO Create Navigation Module
        Fragment container = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (container == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new SettingsFragment()).commit();
        } else {
            getFragmentManager().beginTransaction().remove(container).commit();
        }
    }

    public void onPrincipalClick(View view) {
        // TODO Create Navigation Module
        Fragment container = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (container == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new PrincipalFragment()).commit();
        } else {
            getFragmentManager().beginTransaction().remove(container).commit();
        }
    }
}
