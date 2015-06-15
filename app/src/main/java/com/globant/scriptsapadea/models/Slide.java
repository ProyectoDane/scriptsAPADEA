package com.globant.scriptsapadea.models;

import java.io.Serializable;

/**
 * Created by nicolas.quartieri
 */
public class Slide implements Serializable {

    private String id;
    private int image = 0;
    private String text;

    public Slide(String id, String text, int image) {
        this.id = id;
        this.image = image;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
