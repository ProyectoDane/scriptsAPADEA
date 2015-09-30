package com.globant.scriptsapadea.manager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.globant.scriptsapadea.models.Slide;
import com.globant.scriptsapadea.sql.SQLiteHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leonel.mendez on 6/26/2015.
 */
public class ScreenPlayEditorManager {

    private List<Slide> slides;
    private RecyclerView.Adapter adapter;
    private Context mContext;

    private PatientManager patientManager;
    private SQLiteHelper mDBHelper;

    public ScreenPlayEditorManager(Context context, PatientManager patientManager, SQLiteHelper mDBHelper) {
        this.mContext = context;
		// TODO This is not correct. Find another way
        this.patientManager = patientManager;
        this.mDBHelper = mDBHelper;
        this.slides = new LinkedList<>();
    }

    public ScreenPlayEditorManager(Context context,List<Slide> slides) {
        this.mContext = context;
        this.slides = slides;
    }

    public void addSlide(Slide slide) {
        if (adapter != null) {
            if (slides.size() >= 2) {
                slides.add(1, slide);
            } else {
                slides.add(slide);
            }

            adapter.notifyDataSetChanged();
        }
    }

    public void addSlide(Slide slide, int position) {
        if (adapter != null) {
            slides.add(position, slide);

            adapter.notifyDataSetChanged();
        }
    }

    public void deleteSlide(int position) {
        if (adapter != null) {
            slides.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    public Slide getSlide(int position) {
        return slides.get(position);
    }

    public Slide createSlide(long id, String urlImage, String description, int type){
        return new Slide(id, urlImage, description, type);
    }

    public void saveSlide(Slide slide) {
        mDBHelper.createSlide(slide, patientManager.getSelectedScript().getId());
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
