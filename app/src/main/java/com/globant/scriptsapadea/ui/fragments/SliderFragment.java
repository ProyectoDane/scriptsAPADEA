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
import com.globant.scriptsapadea.manager.PatientManager;
import com.globant.scriptsapadea.models.Slide;
import com.software.shell.fab.ActionButton;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by nicolas.quartieri
 */
public class SliderFragment extends BaseFragment {

    private static final String EXTRA_MESSAGE = "message";

    private SliderCallback listener;

    @Inject
    private PatientManager patientManager;

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
        final ImageView imgCardBackground = (ImageView) view.findViewById(R.id.img_card_background);
        if (slide.isResourceImage()) {
            imgCard.setVisibility(View.GONE);
            if (slide.getResImage() == R.drawable.teayudo_iconovacio) {
                imgCard.setVisibility(View.GONE);
            } else {
                imgCard.setVisibility(View.VISIBLE);
                view.findViewById(R.id.ly_one).setVisibility(View.GONE);
                view.findViewById(R.id.ly_two).setVisibility(View.GONE);

                Picasso.with(getActivity()).load(slide.getResImage()).error(R.drawable.teayudo_usuario)
                        .into(imgCard);
            }
        } else {
            imgCard.setVisibility(View.VISIBLE);
            imgCardBackground.setVisibility(View.GONE);
            view.findViewById(R.id.ly_one).setVisibility(View.GONE);
            view.findViewById(R.id.ly_two).setVisibility(View.GONE);

            Picasso.with(getActivity()).load(new File(slide.getUrlImage())).error(R.drawable.teayudo_usuario)
                    .into(imgCard);
        }

        final TextView txtSlideLegend = (TextView) view.findViewById(R.id.txt_slide_legend);
        txtSlideLegend.setText(slide.getText());

        ActionButton fabNext = (ActionButton) view.findViewById(R.id.fab_next);
        fabNext.setImageResource(R.drawable.rightarrow_icon);
        fabNext.setShadowResponsiveEffectEnabled(true);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nextSlide();
            }
        });

        ActionButton fabPrev = (ActionButton) view.findViewById(R.id.fab_prev);
        fabPrev.setImageResource(R.drawable.leftarrow_icon);
        fabPrev.setShadowResponsiveEffectEnabled(true);
        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.previousSlide();
            }
        });

        return view;
    }

    public static SliderFragment newInstance(Slide slide) {
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
