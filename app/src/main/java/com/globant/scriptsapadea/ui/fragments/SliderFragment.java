package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.globant.scriptsapadea.R;
import com.software.shell.fab.ActionButton;

/**
 * Created by nicolas.quartieri
 */
public class SliderFragment extends BaseFragment {

    private static final String EXTRA_MESSAGE = "message";

    private SliderCallback listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        listener = (SliderCallback)activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String message = getArguments().getString(EXTRA_MESSAGE);

        View view = inflater.inflate(R.layout.slider_layout, container, false);

        // TODO Set image
        final ImageView imgCard = (ImageView) view.findViewById(R.id.img_card);

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

    public static Object newInstance(String message) {
        SliderFragment slider = new SliderFragment();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MESSAGE, message);

        slider.setArguments(bundle);

        return slider;
    }

    public interface SliderCallback {
        public void nextSlide();
        public void previousSlide();
    }
}
