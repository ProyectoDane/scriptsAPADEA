package com.globant.scriptsapadea.models;

import java.util.List;

/**
 * Created by nicola.
 */
public class Patient {

    private String id;
    private String name;
    private int avatar;
    private List<Script> scriptList;

    public Patient(String name, int avatar) {
        this.name = name;
        this.avatar = avatar;
    }


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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public void setScriptList(List<Script> scriptList) {
        this.scriptList = scriptList;
    }


    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avatar=" + avatar +
                ", scriptList=" + scriptList +
                '}';
    }
}
