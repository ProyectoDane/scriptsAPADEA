package com.globant.scriptsapadea.manager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.globant.scriptsapadea.models.Slide;
import com.globant.scriptsapadea.sql.SQLiteHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * With this screen we can add/edit/erase slides into the selected script.
 *
 * @author leonel.mendez
 */
public class ScreenPlayEditorManager {

    private List<Slide> slides;
    private RecyclerView.Adapter adapter;
    private Context mContext;
    private PatientManager patientManager;
    private SQLiteHelper mDBHelper;

    public ScreenPlayEditorManager(Context context, PatientManager patientManager, SQLiteHelper mDBHelper, List<Slide> slides) {
        this.mContext = context;
        // TODO This is not correct. Find another way
        this.patientManager = patientManager;
        this.mDBHelper = mDBHelper;
        if (slides != null && !slides.isEmpty()) {
            this.slides = slides;
        } else {
            this.slides = new LinkedList<>();
        }

    }

    public void addSlide(Slide slide) {
        if (adapter != null) {
            slides.add(0, slide);
            adapter.notifyDataSetChanged();
        }
    }

    public void addSlide(Slide slide, int position) {
        if (adapter != null) {
            slides.add(position, slide);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Delete the selected slide from the slide list setup in memory.
     *
     * @param slide
     */
    public void deleteSlide(Slide slide) {
        if (adapter != null) {
            int position = slides.indexOf(slide);
            slides.remove(slide);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, slides.size());
            adapter.notifyDataSetChanged();
        }
    }

    public Slide getSlide(int position) {
        return slides.get(position);
    }

    public Slide createSlide(long id, String urlImage, String description, int type){
        return new Slide(id, urlImage, description, type);
    }

    /**
     * Save the selected slide from the slide list setup into de Data Base.
     *
     * @param slide
     * @return
     */
    public long saveSlide(Slide slide) {
        return mDBHelper.createSlide(slide, patientManager.getSelectedScript().getId());
    }

    /**
     * Delete the selected slide from the slide list setup in the Data Base.
     *
     * @param slide
     * @return
     */
    public int removeSlide(Slide slide) {
        return mDBHelper.deleteSlide(slide, patientManager.getSelectedScript().getId());
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
