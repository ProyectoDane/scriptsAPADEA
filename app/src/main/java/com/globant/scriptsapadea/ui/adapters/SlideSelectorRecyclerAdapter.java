package com.globant.scriptsapadea.ui.adapters;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by leonel.mendez on 6/26/2015.
 */
public class SlideSelectorRecyclerAdapter extends RecyclerView.Adapter<SlideSelectorRecyclerAdapter.CommonViewHolder>{

    private ScreenPlayEditorManager screenPlayEditorManager;
    private OnSlideSelectorItemClickListener onSlideSelectorItemClickListener;

    public SlideSelectorRecyclerAdapter(ScreenPlayEditorManager screenPlayEditorManager) {
        this.screenPlayEditorManager = screenPlayEditorManager;
        this.screenPlayEditorManager.setAdapter(SlideSelectorRecyclerAdapter.this);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case Slide.ONLY_TEXT:
                return new TextViewHolder(layoutInflater.inflate(R.layout.slide_card_text_layout,parent,false));
            case Slide.ONLY_IMAGE:
                return new ImageViewHolder(layoutInflater.inflate(R.layout.slide_card_image_layout,parent,false));
            case Slide.IMAGE_TEXT:
                return new ImageAndTextViewHolder(layoutInflater.inflate(R.layout.slide_card_image_text_layout,parent,false));
            default:
                return new CommonViewHolder(layoutInflater.inflate(R.layout.slide_add_layout,parent,false));
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
                    TextViewHolder textViewHolder = (TextViewHolder)holder;
                    textViewHolder.text.setText(slide.getText());
                    break;

                case Slide.ONLY_IMAGE:
                    ImageViewHolder imageViewHolder = (ImageViewHolder)holder;
                    showSlideImage(imageViewHolder.imageView.getContext(), slide, imageViewHolder.imageView);

                    break;
                case Slide.IMAGE_TEXT:
                    ImageAndTextViewHolder imageAndTextViewHolder = (ImageAndTextViewHolder)holder;
                    showSlideImage(imageAndTextViewHolder.slideImage.getContext(), slide, imageAndTextViewHolder.slideImage);
                    imageAndTextViewHolder.slideDesc.setText(slide.getText());
                    break;

                default:
                    break;
            }
    }

    @Override
    public int getItemCount() {
        return screenPlayEditorManager.getItemCount();
    }

    public class CommonViewHolder extends RecyclerView.ViewHolder{
        public CommonViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(onSlideSelectorItemClickListener != null){
                    onSlideSelectorItemClickListener.onSlideSelectorItemClick(SlideSelectorRecyclerAdapter.this,itemView,getPosition());
                }
                }
            });
        }
    }

    private void showSlideImage(Context context, Slide slide, ImageView imageContainer) {
        if (slide.isResourceImage()) {
            Picasso.with(context)
                    .load(slide.getResImage())
                    .into(imageContainer);
        } else {
            Picasso.with(context)
                    .load(new File(slide.getUrlImage()))
                    .into(imageContainer);
        }
    }

    public class TextViewHolder extends CommonViewHolder {
        public TextView text;
        public TextViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.slide_only_text);
        }
    }

    public class ImageViewHolder extends CommonViewHolder{
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.slide_only_image);
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

    public void setOnSlideSelectorItemClickListener(OnSlideSelectorItemClickListener onSlideSelectorItemClickListener) {
        this.onSlideSelectorItemClickListener = onSlideSelectorItemClickListener;
    }

    public interface OnSlideSelectorItemClickListener {
        void onSlideSelectorItemClick(RecyclerView.Adapter adapter, View view, int position);
    }
}
