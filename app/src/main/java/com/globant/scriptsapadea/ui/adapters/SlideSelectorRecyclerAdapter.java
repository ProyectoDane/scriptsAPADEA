package com.globant.scriptsapadea.ui.adapters;

import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.manager.ScreenPlayEditorManager;
import com.globant.scriptsapadea.models.Slide;
import com.globant.scriptsapadea.ui.fragments.ScreenPlayEditorFragment;

/**
 * Created by leonel.mendez on 6/26/2015.
 */
public class SlideSelectorRecyclerAdapter extends RecyclerView.Adapter<SlideSelectorRecyclerAdapter.SlideViewHolder>{

    private ScreenPlayEditorManager screenPlayEditorManager;

    public SlideSelectorRecyclerAdapter(ScreenPlayEditorManager screenPlayEditorManager) {
        this.screenPlayEditorManager = screenPlayEditorManager;
        this.screenPlayEditorManager.setAdapter(SlideSelectorRecyclerAdapter.this);
    }

    @Override
    public SlideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mainView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_card_layout,parent);
        return new SlideViewHolder(mainView);
    }


    @Override
    public void onBindViewHolder(SlideViewHolder holder, int position) {

        Slide slide = screenPlayEditorManager.getSlide(position);
        holder.slideDesc.setText(slide.getText());
        holder.slideImage.setImageResource(slide.getImage());
    }

    @Override
    public int getItemCount() {
        return screenPlayEditorManager.getItemCount();
    }

    public class SlideViewHolder extends RecyclerView.ViewHolder {
        public CardView slideCard;
        public ImageView slideImage;
        public TextView slideDesc;

        public SlideViewHolder(View itemView) {
            super(itemView);
            slideCard = (CardView)itemView.findViewById(R.id.slide_container);
            slideImage = (ImageView)itemView.findViewById(R.id.slide_img);
            slideDesc =(TextView)itemView.findViewById(R.id.slide_txt);
        }
    }

}
