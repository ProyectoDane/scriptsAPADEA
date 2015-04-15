package com.globant.scriptsapadea.models;

import android.media.Image;

/**
 * Created by nicolas.quartier
 */
public class ScriptSlide {

    private String text;
    private Image image;


    public ScriptSlide(String text, Image image) {
        this.text = text;
        this.image = image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Image getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
