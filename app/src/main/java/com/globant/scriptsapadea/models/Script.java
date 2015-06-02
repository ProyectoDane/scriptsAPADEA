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
    private List<Image> images;

    public Script(String id, String name, List<Image> images) {
        this.id = id;
        this.name = name;
        this.images = images;
    }

    public Script(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Script(String name, List<Image> images ) {
        this.name = name;
        this.images = images;
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

    public List<Image> getImageScripts() {
        return images;
    }

    public void setImage(List<Image> images) {
        this.images = images;
    }

    public static List<Script> fetchAllScripts(String idPacient) {
        // TODO get all scripts from idPacient
        LinkedList listScript = new LinkedList<Script>();
        listScript.add(new Script("0", "Pepe", new ArrayList<Image>(R.drawable.ic_launcher)));
        listScript.add(new Script("1", "Juan", new ArrayList<Image>(R.drawable.ic_launcher)));
        listScript.add(new Script("2", "Marcelo", new ArrayList<Image>(R.drawable.ic_launcher)));
        listScript.add(new Script("3", "Pepe", new ArrayList<Image>(R.drawable.ic_launcher)));
        listScript.add(new Script("4", "Juan", new ArrayList<Image>(R.drawable.ic_launcher)));

        return listScript;
    }

    @Override
    public String toString() {
        return "Script{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image=" + images.toString() +
                '}';
    }
}
