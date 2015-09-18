package com.globant.scriptsapadea.manager;

import android.content.Context;

import com.globant.scriptsapadea.models.Patient;

/**
 * Created by nicolas.quartieri.
 */
public class PatientManager {

    private final Context context;

    private Patient selectedPactient;

    public PatientManager(Context context) {
        this.context = context;
    }

    public Patient getSelectedPactient() {
        return selectedPactient;
    }

    public void setSelectedPactient(Patient selectedPactient) {
        this.selectedPactient = selectedPactient;
    }
}
