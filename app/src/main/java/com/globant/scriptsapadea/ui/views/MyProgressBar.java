package com.globant.scriptsapadea.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.globant.scriptsapadea.R;

/**
 * Created by nicolas.quartieri
 */
public class MyProgressBar extends LinearLayout {

    private int current = 0;
    private int size = 1;

    private Context context;
    private ViewGroup parent;

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray theAttrs = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar);
        this.size = theAttrs.getInt(R.styleable.MyProgressBar_dotsize, 1) ;
        this.context = context;
    }

    public MyProgressBar(Context context) {
        super(context);
        this.context = context;
    }

    public void build() {
        prepareLayout();
    }

    /**
     * Loads the layout and sets the initial set of images
     */
    private void prepareLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.myprogressbar, null);
        addView(view);

        parent = (ViewGroup) view.findViewById(R.id.RelativeLayout01);

        for (int i = 0 ; i < size ; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setId(i);

            if (i == 0) {
                imageView.setImageResource(R.drawable.progressbar_top_left);
            } else if (i == size - 1) {
                imageView.setImageResource(R.drawable.progress_bar_right);
            } else {
                imageView.setImageResource(R.drawable.bar_empty);
            }

            parent.addView(imageView);
        }
    }

    public void next() {
        int count = parent.getChildCount();
        if (current + 1 < count) {

            ImageView image = (ImageView) parent.getChildAt(current + 1);
            if (current + 1 == size - 1) {
                image.setImageResource(R.drawable.progressbar_top_right_blue);
            } else {
                image.setImageResource(R.drawable.bar);
            }

            current++;
        }
    }

    public void prev() {
        if (current - 1 >= 0) {

            ImageView imageBefore = (ImageView) parent.getChildAt(current);
            if (current + 1 == size) {
                imageBefore.setImageResource(R.drawable.progress_bar_right);
            } else {
                imageBefore.setImageResource(R.drawable.bar_empty);
            }


            current--;
        }
    }

    public void setSize(int size) {
        this.size = size;
    }
}
