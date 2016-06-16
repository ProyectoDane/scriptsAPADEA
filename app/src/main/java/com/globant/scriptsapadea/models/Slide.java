package com.globant.scriptsapadea.models;

import com.globant.scriptsapadea.R;

import java.io.Serializable;

/**
 * @author nicolas.quartieri
 */
public class Slide implements Serializable {

    public static final String SLIDE = "slide";

    public static final int ONLY_TEXT = 0;
    public static final int ONLY_IMAGE = 1;
    public static final int IMAGE_TEXT = 2;

    private long id;
    private String urlImage;
    private int resImage;
    private String text;
    private int type = 0x110;

    public Slide(long id, String urlImage, String text, int type) {
        this.id = id;
        this.urlImage = urlImage;
        this.text = text;
        this.type = type;
    }

    public Slide(long id, int image, String text, int type) {
        this.id = id;
        this.resImage = image;
        this.text = text;
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    public boolean isResourceImage() {
        return urlImage == null;
    }

    public int getResImage() {
        return resImage;
    }


    public static Slide createEmpty() {
        return new Slide(0, R.drawable.teayudo_usuario, "Slide Vacio", ONLY_IMAGE);
    }

    public void updateImage(String imageGalleryUrl) {
        this.urlImage = imageGalleryUrl;
    }
}
