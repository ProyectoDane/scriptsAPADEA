package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Slide;
import com.software.shell.fab.ActionButton;

/**
 * Created by nicolas.quartieri
 */
public class SliderFragment extends BaseFragment {

    private static final String EXTRA_MESSAGE = "message";

    private SliderCallback listener;

    private Slide slide;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        listener = (SliderCallback)activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slide = (Slide)getArguments().getSerializable(EXTRA_MESSAGE);

        View view = inflater.inflate(R.layout.slider_layout, container, false);

        final ImageView imgCard = (ImageView) view.findViewById(R.id.img_card);
        if (slide.getImage() != 0) {
            imgCard.setImageResource(slide.getImage());
        } else {
            imgCard.setImageResource(R.drawable.ic_launcher);
        }
        final TextView txtSlideLegend = (TextView) view.findViewById(R.id.txt_slide_legend);
        txtSlideLegend.setText(slide.getText());

        // TODO Set text below image

        ActionButton fabNext = (ActionButton) view.findViewById(R.id.fab_next);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nextSlide();
            }
        });

        ActionButton fabPrev = (ActionButton) view.findViewById(R.id.fab_prev);
        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.previousSlide();
            }
        });

        return view;
    }

    public static Object newInstance(Slide slide) {
        SliderFragment fragment = new SliderFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_MESSAGE, slide);

        fragment.setArguments(bundle);

        return fragment;
    }

    public interface SliderCallback {
        public void nextSlide();
        public void previousSlide();
    }
}
