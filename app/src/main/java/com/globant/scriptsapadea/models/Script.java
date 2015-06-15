package com.globant.scriptsapadea.models;

import com.globant.scriptsapadea.R;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nicolas.quartieri
 */
public class Script implements Serializable {

    private String id;
    private List<Slide> slides = new LinkedList<Slide>();
    private String name;
    private int image;


    public Script(String id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getImageScripts() {
        return image;
    }

    public List<Slide> getSlides() {
        return slides;
    }

    public static List<Script> fetchAllScripts(String idPacient) {
        // TODO get all scripts from idPacient
        LinkedList listScript = new LinkedList<Script>();
        listScript.add(new Script("0", "Pepe", R.drawable.ic_launcher));
        listScript.add(new Script("1", "Juan", R.drawable.ic_launcher));
        listScript.add(new Script("2", "Marcelo", R.drawable.ic_launcher));
        listScript.add(new Script("3", "Pepe", R.drawable.ic_launcher));
        listScript.add(new Script("4", "Juan", R.drawable.ic_launcher));

        return listScript;
    }
}
