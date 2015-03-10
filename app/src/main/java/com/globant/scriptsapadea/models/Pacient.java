package com.globant.scriptsapadea.models;

import java.util.List;

/**
 * Created by nicola.
 */
public class Pacient {

    private String id;
    private String name;
    private int avatar;
    private List<Script> scriptList;


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
}
