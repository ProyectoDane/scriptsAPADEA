package com.globant.scriptsapadea.ui.activities;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.interfaces.OnChangeFragmentListener;
import com.globant.scriptsapadea.ui.fragments.ScreenplayFragment;
import com.globant.scriptsapadea.navigator.anim.SlidingUpAnimation;
import com.globant.scriptsapadea.ui.fragments.PrincipalFragment;
import com.globant.scriptsapadea.ui.fragments.ScriptsSelectorFragment;
import com.globant.scriptsapadea.ui.fragments.SettingsFragment;
import com.globant.scriptsapadea.manager.ActivityResultEvent;

import roboguice.inject.ContentView;

/**
 * @author nicolas.quartieri.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnChangeFragmentListener {

    public void onScriptsClicked(View view) {
        // TODO Create Navigation Module
        Fragment scriptsSelectorFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (scriptsSelectorFragment == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ScriptsSelectorFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().remove(scriptsSelectorFragment).commit();
        }
    }

    public void onPreferenceClick(View view) {
        // TODO Create Navigation Module
        Fragment container = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (container == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new SettingsFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().remove(container).commit();
        }
    }

    public void onPrincipalClick(View view) {
        // TODO Create Navigation Module
        Fragment container = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (container == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new PrincipalFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().remove(container).commit();
        }
    }

    public void onSliderClick(View view) {
        startActivity(new Intent(this, ScreenSliderActivity.class));
    }

    public void onAboutClicked(View view) {
        Fragment settingsFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (settingsFragment == null) {
            navigator.to(new Intent(this, AboutActivity.class)).withAnimations(new SlidingUpAnimation()).navigate();
        } else {
            getSupportFragmentManager().beginTransaction().remove(settingsFragment).commit();
        }
    }


    public void onCreateNewScreenplay(View view) {

        Fragment screenplayFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (screenplayFragment == null) {
            navigator.to(new ScreenplayFragment()).navigate();
        } else {
            getSupportFragmentManager().beginTransaction().remove(screenplayFragment).commit();
        }
    }

    // TODO This methods should go inside property class.
    public void clickRemove(View view) {
        Log.i("INFO", "clickRemove");
    }

    // TODO This methods should go inside property class.
    public void clickView(View view) {
        Log.i("INFO", "clickView");
    }

    // TODO This methods should go inside property class.
    public void clickEdit(View view) {
        Log.i("INFO", "clickEdit");
    }

    // TODO This methods should go inside property class.
    public void clickReadScript(View view) {
        Log.i("INFO", "clickReadScript");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bus.post(new ActivityResultEvent(requestCode, resultCode, data));
    }

    // TODO: Use Otto to create this event
    @Override
    public void onChangeFragment(Fragment fragment, boolean addInStack, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (addInStack)
            fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(tag).commit();
        else
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }
}
