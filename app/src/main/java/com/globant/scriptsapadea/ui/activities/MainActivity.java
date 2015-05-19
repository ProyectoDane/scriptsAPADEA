package com.globant.scriptsapadea.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.navigator.anim.SlidingUpAnimation;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.globant.scriptsapadea.ui.fragments.PrincipalFragment;
import com.globant.scriptsapadea.ui.fragments.ScriptsSelectorFragment;
import com.globant.scriptsapadea.ui.fragments.SettingsFragment;

import roboguice.inject.ContentView;

/**
 * @author nicolas.quartieri.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DBTesting
        super.onCreate(savedInstanceState);
        SQLiteHelper ayuda= new SQLiteHelper(getApplicationContext());
        Patient patient = new Patient("Guillermo",2);
        long id = ayuda.createPatient(patient);
        Script script = new Script("Ir al ba�o",2);
        ayuda.createScript(script,id);
        Log.d("All patients", ayuda.getAllPatients().toString());
        Log.d("All scripts patient",ayuda.getAllScriptsFromPatient(patient.getName()).toString());


    }

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
}
