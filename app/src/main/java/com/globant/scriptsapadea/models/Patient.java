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
    private List<Script> scriptList;


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
    public static List<Patient> fetchAllPatients() {

        LinkedList listScript = new LinkedList<Patient>();
        listScript.add(new Patient("0", "Pepe", R.drawable.avatar_placeholder));
        listScript.add(new Patient("1", "Juan", R.drawable.avatar_placeholder));
        listScript.add(new Patient("2", "Marcelo", R.drawable.avatar_placeholder));
        listScript.add(new Patient("3", "Roberto", R.drawable.avatar_placeholder));
        listScript.add(new Patient("4", "Juan", R.drawable.avatar_placeholder));

        return listScript;
    }
}
