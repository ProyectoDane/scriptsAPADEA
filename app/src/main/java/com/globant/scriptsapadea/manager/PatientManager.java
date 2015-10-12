package com.globant.scriptsapadea.manager;

import android.content.Context;

import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;

/**
 * Created by nicolas.quartieri.
 */
public class PatientManager {

    private final Context context;

    private Patient selectedPactient;
    private Script selectedScript;

    public PatientManager(Context context) {
        this.context = context;
    }

    public Patient getSelectedPactient() {
        return selectedPactient;
    }

    public void setSelectedPactient(Patient selectedPactient) {
        this.selectedPactient = selectedPactient;
    }

    public Script getSelectedScript() {
        return selectedScript;
    }

    public void setSelectedScript(Script selectedScript) {
        this.selectedScript = selectedScript;
    }
}
