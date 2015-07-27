package com.globant.scriptsapadea.models;

import java.io.Serializable;

/**
 * Created by nicolas.quartieri
 */
public class Slide implements Serializable {

    public static final int ONLY_TEXT = 0;
    public static final int ONLY_IMAGE = 1;
    public static final int IMAGE_TEXT = 2;


    private String id;
    private int image = 0;
    private String urlImage;
    private String text;
    private int type = 0x110;



    public Slide(String id, String text, int image) {
        this.id = id;
        this.image = image;
        this.text = text;
    }

    public Slide(String id, String urlImage, String text,int type) {
        this.id = id;
        this.urlImage = urlImage;
        this.text = text;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
