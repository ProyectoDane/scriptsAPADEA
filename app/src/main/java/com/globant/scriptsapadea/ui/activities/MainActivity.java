package com.globant.scriptsapadea.ui.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.globant.scriptsapadea.ui.fragments.ScriptsSelectorFragment;
import com.globant.scriptsapadea.ui.fragments.SettingsFragment;

import roboguice.inject.ContentView;

/**
 * @author nicolas.quartieri
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {






    @Override
    public void onCreate(Bundle savedInstanceState) {

        /*DBTesting
        super.onCreate(savedInstanceState);
        SQLiteHelper ayuda= new SQLiteHelper(getApplicationContext());
        Patient patient = new Patient("Guillermo",2);
        long id = ayuda.createPatient(patient);
        Script script = new Script("Ir al baño",2);
        ayuda.createScript(script,id);

        Log.d("All patients", ayuda.getAllPatients().toString());
        Log.d("All scripts patient",ayuda.getAllScriptsFromPatient(patient.getName()).toString());
        */



    }




    public void onScriptsClicked(View view) {
        // TODO Create Navigation Module
        Fragment scriptsSelectorFragment = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (scriptsSelectorFragment == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new ScriptsSelectorFragment()).commit();
        } else {
            getFragmentManager().beginTransaction().remove(scriptsSelectorFragment).commit();
        }
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
