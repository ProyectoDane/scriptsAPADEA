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

import java.net.CookieHandler;

/**
 * Created by leonel.mendez on 6/26/2015.
 */
public class SlideSelectorRecyclerAdapter extends RecyclerView.Adapter<SlideSelectorRecyclerAdapter.CommonViewHolder>{

    private ScreenPlayEditorManager screenPlayEditorManager;

    public SlideSelectorRecyclerAdapter(ScreenPlayEditorManager screenPlayEditorManager) {
        this.screenPlayEditorManager = screenPlayEditorManager;
        this.screenPlayEditorManager.setAdapter(SlideSelectorRecyclerAdapter.this);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case Slide.ONLY_TEXT:
                return new TextViewHolder(layoutInflater.inflate(R.layout.slide_card_text_layout,parent));
            case Slide.ONLY_IMAGE:
                return new ImageViewHolder(layoutInflater.inflate(R.layout.slide_card_image_layout,parent));
            case Slide.IMAGE_TEXT:
                return new ImageAndTextViewHolder(layoutInflater.inflate(R.layout.slide_card_image_text_layout,parent));
            default:
                return null;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return screenPlayEditorManager.getSlide(position).getType();
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
            Slide slide = screenPlayEditorManager.getSlide(position);
            switch (slide.getType()){
                case Slide.ONLY_TEXT:
                    break;
                case Slide.ONLY_IMAGE:
                    break;
                case Slide.IMAGE_TEXT:
                    ImageAndTextViewHolder imageAndTextViewHolder = (ImageAndTextViewHolder)holder;
                    imageAndTextViewHolder.slideImage.setImageResource(slide.getImage());
                    imageAndTextViewHolder.slideDesc.setText(slide.getText());
                    break;
            }
    }

    @Override
    public int getItemCount() {
        return screenPlayEditorManager.getItemCount();
    }

    public class CommonViewHolder extends RecyclerView.ViewHolder{
        public CommonViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class TextViewHolder extends CommonViewHolder{
        public TextViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ImageViewHolder extends CommonViewHolder{
        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ImageAndTextViewHolder extends CommonViewHolder {
        public CardView slideCard;
        public ImageView slideImage;
        public TextView slideDesc;

        public ImageAndTextViewHolder(View itemView) {
            super(itemView);
            slideCard = (CardView)itemView.findViewById(R.id.slide_container);
            slideImage = (ImageView)itemView.findViewById(R.id.slide_img);
            slideDesc =(TextView)itemView.findViewById(R.id.slide_txt);
        }
    }

}
