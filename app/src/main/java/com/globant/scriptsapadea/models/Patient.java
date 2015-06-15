package com.globant.scriptsapadea.models;

import com.globant.scriptsapadea.R;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nicolas.quartieri
 */
public class Patient implements Serializable {

    private String id;
    private String name;
    private int avatar;
    private List<Script> scriptList = new LinkedList<Script>();


    public Patient(String id, String name, int avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAvatar() {
        return avatar;
    }

    public List<Script> getScriptList() {
        return scriptList;
    }

    public void setScriptList(List<Script> scriptList) {
        this.scriptList = scriptList;
    }

    // TODO Mocked services
    public static List<Patient> fetchAllPatients(String idPacient) {
		// TODO Remove this
        List<Patient> listScript = new LinkedList<Patient>();

        if (idPacient == null) {
            // TODO Retrive ALL Patients from DB
            Patient patientJuan = new Patient("0", "Juan", R.drawable.avatar_placeholder);
            Script scriptJuan = new Script("0", "Lavar los platos", R.drawable.ic_launcher);
            scriptJuan.getSlides().add(new Slide("0", "Primero....", 0));
            scriptJuan.getSlides().add(new Slide("1", "Segundo....", 0));
            scriptJuan.getSlides().add(new Slide("2", "Tercero....", 0));


            Patient patient = new Patient("0", "Pepe", R.drawable.avatar_placeholder);
            Script script = new Script("0", "Lavar los platos", R.drawable.ic_launcher);
            script.getSlides().add(new Slide("0", "Primero....", 0));
            script.getSlides().add(new Slide("1", "Segundo....", 0));
            script.getSlides().add(new Slide("2", "Tercero....", 0));
            Script scriptPepe = new Script("0", "Lavar los platos", R.drawable.ic_launcher);
            patient.getScriptList().add(script);
            scriptPepe.getSlides().add(new Slide("0", "Primero....", 0));
            scriptPepe.getSlides().add(new Slide("1", "Segundo....", 0));
            scriptPepe.getSlides().add(new Slide("2", "Tercero....", 0));
            patient.getScriptList().add(scriptPepe);

            listScript.add(patient);
        } else {
            // TODO Retrive from DB
        }

        return listScript;
    }
}
