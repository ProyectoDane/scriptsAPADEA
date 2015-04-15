package com.globant.scriptsapadea.ui.activities;

import android.support.v4.app.Fragment;
import android.view.View;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.ui.fragments.ScriptsSelectorFragment;
import com.globant.scriptsapadea.ui.fragments.UseGuideFragment;

import roboguice.inject.ContentView;

/**
 * @author nicolas.quartieri
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements UseGuideFragment.Listener {

    public void onScriptsClicked(View view) {
        // TODO Create Navigation Module
        Fragment someFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (someFragment == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ScriptsSelectorFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().remove(someFragment).commit();
        }
    }

    public void onGuiaDeUsoClicked(View view) {
        // TODO Create Navigation Module
        Fragment someFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_main_container);
        if (someFragment == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_main_container, new UseGuideFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().remove(someFragment).commit();
        }
    }

    @Override
    public void navigateToScriptListView() {
        // TODO Create Navigation Module
        Fragment someFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_main_container);
        if (someFragment == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ScriptsSelectorFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().remove(someFragment).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ScriptsSelectorFragment()).commit();
        }
    }
}
