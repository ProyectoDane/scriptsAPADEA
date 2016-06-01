package com.globant.scriptsapadea.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nicolas.quartieri
 */
public class Script implements Serializable {

    public static String SCRIPT = "script";

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private String name;
    private String image;
    private int resImage;
    private boolean editable;

    private List<Slide> slides = new LinkedList<>();

    public Script(long id, String name, String image, boolean editable) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.editable = editable;
    }

    public Script(long id, String name, int resImage, boolean editable) {
        this.id = id;
        this.name = name;
        this.resImage = resImage;
        this.editable = editable;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getImageScripts() {
        return image;
    }

    public void setSlides(List<Slide> slides) {
        this.slides = slides;
    }

    public List<Slide> getSlides() {
        return slides;
    }

    public boolean isResourceImage() {
        return image == null;
    }

    public int getResImage() {
        return resImage;
    }

    public boolean isEditable() {
        return editable;
    }

    public static List<Script> fetchAllScripts(String idPacient) {
        // TODO get all scripts from idPacient
        LinkedList listScript = new LinkedList<>();
        listScript.add(new Script(0, "Pepe", null, true));
        listScript.add(new Script(1, "Juan", null, true));
        listScript.add(new Script(2, "Marcelo", null, true));
        listScript.add(new Script(2, "Pepe", null, true));
        listScript.add(new Script(4, "Juan", null, true));

        return listScript;
    }

    public static Script createEmpty() {
        return new Script(0, "Script Vacio", null, true);
    }
}
