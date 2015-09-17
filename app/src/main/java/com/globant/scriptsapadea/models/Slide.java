package com.globant.scriptsapadea.models;

import java.io.Serializable;

/**
 * Created by nicolas.quartieri
 */
public class Slide implements Serializable {

    public static final int ONLY_TEXT = 0;
    public static final int ONLY_IMAGE = 1;
    public static final int IMAGE_TEXT = 2;

    private long id;
    private String image;
    private String urlImage;

    private int resImage;
    private String text;
    private int type = 0x110;

    public Slide(long id, String text, String image) {
        this.id = id;
        this.image = image;
        this.text = text;
    }

    public Slide(long id, String urlImage, String text, int type) {
        this.id = id;
        this.urlImage = urlImage;
        this.text = text;
        this.type = type;
    }

    public Slide(long id, String text, int image) {
        this.id = id;
        this.resImage = image;
        this.text = text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getImage() {
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

    public boolean isResourceAvatar() {
        return resImage != 0;
    }

    public int getResImage() {
        return resImage;
    }
}
