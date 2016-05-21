package com.globant.scriptsapadea.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nicolas.quartieri
 */
public class Script implements Serializable {

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private String name;
    private String image;
    private int resImage;

    private List<Slide> slides = new LinkedList<Slide>();

    public Script(long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Script(long id, String name, int resImage) {
        this.id = id;
        this.name = name;
        this.resImage = resImage;
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

    public static List<Script> fetchAllScripts(String idPacient) {
        // TODO get all scripts from idPacient
        LinkedList listScript = new LinkedList<Script>();
        listScript.add(new Script(0, "Pepe", null));
        listScript.add(new Script(1, "Juan", null));
        listScript.add(new Script(2, "Marcelo", null));
        listScript.add(new Script(2, "Pepe", null));
        listScript.add(new Script(4, "Juan", null));

        return listScript;
    }

    public static Script createEmpty() {
        return new Script(0, "Script Vacio", null);
    }
}
