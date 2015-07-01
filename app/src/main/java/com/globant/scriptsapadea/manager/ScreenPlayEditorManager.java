package com.globant.scriptsapadea.manager;

import android.support.v7.widget.RecyclerView;

import com.globant.scriptsapadea.models.Slide;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by leonel.mendez on 6/26/2015.
 */
public class ScreenPlayEditorManager {

    private List<Slide> slides;
    private RecyclerView.Adapter adapter;

    public ScreenPlayEditorManager(){
        this.slides = new LinkedList<>();
        Collections.reverse(slides);

    }

    public ScreenPlayEditorManager(List<Slide> slides) {
        this.slides = slides;
        Collections.reverse(this.slides);
    }

    public void addSlide(Slide slide){
        if (adapter != null){
            slides.add(slide);
            adapter.notifyDataSetChanged();
        }
    }
    public void addSlide(Slide slide, int position){
        if(adapter != null) {
            slides.add(position, slide);
            adapter.notifyDataSetChanged();
        }
    }
    public void deleteSlide(int position){
        if(adapter != null) {
            slides.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    public Slide getSlide(int position){
        return slides.get(position);
    }


    public Slide createSlide(String id,String urlImage, String desc){
        return new Slide(id,urlImage,desc);
    }

    public void saveScript(){
        //TODO: Add implementation to save script into database
    }

    public void setSlides(List<Slide> slides) {
        this.slides = slides;
    }

    public List<Slide> getSlides() {
        return slides;
    }

    public int getItemCount(){
        return getSlides() != null?getSlides().size():0;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }
}
