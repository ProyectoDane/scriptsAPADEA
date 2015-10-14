package com.globant.scriptsapadea.manager;

import android.content.Context;

import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;

/**
 * Created by nicolas.quartieri.
 */
public class PatientManager {

    private final Context context;

    private Patient selectedPatient;
    private Script selectedScript;

    public PatientManager(Context context) {
        this.context = context;
    }

    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
    }

    public Script getSelectedScript() {
        return selectedScript;
    }

    public void setSelectedScript(Script selectedScript) {
        this.selectedScript = selectedScript;
    }
}
