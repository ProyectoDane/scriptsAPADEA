package com.globant.scriptsapadea.models;

/**
 * Created by guillermo.rosales on 5/26/15.
 */
public class Image {

    private String id;
    private int androidId;


    public Image(int androidId) {
        this.androidId = androidId;
    }

    public Image(String id, int androidId) {
        this.id = id;
        this.androidId = androidId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAndroidId() {
        return androidId;
    }

    public void setAndroidId(int androidId) {
        this.androidId = androidId;
    }
}
