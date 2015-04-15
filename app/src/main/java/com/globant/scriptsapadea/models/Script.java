package com.globant.scriptsapadea.models;

import com.globant.scriptsapadea.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nicolas.quartieri
 */
public class Script {

    private String id;
    private String name;
    private int image;
    private List<ScriptSlide> slides;


    public Script(String id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.slides = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImageScripts() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public List<ScriptSlide> getSlides() {
        return slides;
    }

    public void setSlides(List<ScriptSlide> slides) {
        this.slides = slides;
    }

    public static List<Script> fetchAllScripts(String idPacient) {
        // TODO get all scripts from idPacient
        LinkedList listScript = new LinkedList<ScriptSlide>();
        listScript.add(new Script("0", "Pepe", R.drawable.ic_launcher));
        listScript.add(new Script("1", "Juan", R.drawable.ic_launcher));
        listScript.add(new Script("2", "Marcelo", R.drawable.ic_launcher));
        listScript.add(new Script("3", "Pepe", R.drawable.ic_launcher));
        listScript.add(new Script("4", "Juan", R.drawable.ic_launcher));

        return listScript;
    }
}
