package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
 * This class provides the screen Slide representation of an already save Slide contained in a Script.
 *
 * @author nicolas.quartieri
 */
public class SliderFragment extends BaseFragment {

    private static final String EXTRA_MESSAGE = "message";

    private SliderCallback listener;

    @Inject
    private PatientManager patientManager;

    private Slide slide;

    public static SliderFragment newInstance(Slide slide) {
        SliderFragment fragment = new SliderFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_MESSAGE, slide);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        listener = (SliderCallback)activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slide = (Slide)getArguments().getSerializable(EXTRA_MESSAGE);
        return inflater.inflate(R.layout.slider_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get View.
        final ImageView imgCard = (ImageView) view.findViewById(R.id.img_card);
        final View legendOne = view.findViewById(R.id.ly_one);
        final View legendTwo = view.findViewById(R.id.ly_two);
        final ImageView imgCardBackground = (ImageView) view.findViewById(R.id.img_card_background);

        // Asserts values & asserts into view components.
        if (slide.isResourceImage()) {
            imgCard.setVisibility(View.GONE);
            if (slide.getResImage() == R.drawable.teayudo_iconovacio) {
                imgCard.setVisibility(View.GONE);
            } else {
                // Hide empty view.
                imgCard.setVisibility(View.VISIBLE);
                legendOne.setVisibility(View.GONE);
                legendTwo.setVisibility(View.GONE);
                // Setup the image Resource in the slide.
                if (slide.getResImage() != 0) {
					Picasso.with(getActivity()).load(slide.getResImage())
							.error(R.drawable.teayudo_usuario).into(imgCard);
                } else {
                    Picasso.with(getActivity()).load(android.R.color.transparent)
                            .into(imgCard);
                }
            }
        } else {
            if (slide.getUrlImage() != null && !TextUtils.isEmpty(slide.getUrlImage())) {
                // Hide empty view.
                imgCardBackground.setVisibility(View.GONE);
                legendOne.setVisibility(View.GONE);
                legendTwo.setVisibility(View.GONE);
                // Setup the image URL in the slide.
                Picasso.with(getActivity()).load(new File(slide.getUrlImage()))
                        .error(R.drawable.teayudo_iconovacio)
                        .into(imgCard);
                imgCard.setVisibility(View.VISIBLE);
            }
        }

        final TextView txtSlideLegend = (TextView) view.findViewById(R.id.txt_slide_legend);
        txtSlideLegend.setText(slide.getText());

        final ActionButton fabNext = (ActionButton) view.findViewById(R.id.fab_next);
        fabNext.setImageResource(R.drawable.rightarrow_icon);
        fabNext.setShadowResponsiveEffectEnabled(true);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nextSlide();
            }
        });

        final ActionButton fabPrev = (ActionButton) view.findViewById(R.id.fab_prev);
        fabPrev.setImageResource(R.drawable.leftarrow_icon);
        fabPrev.setShadowResponsiveEffectEnabled(true);
        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.previousSlide();
            }
        });
    }

    public interface SliderCallback {
        void nextSlide();
        void previousSlide();
    }
}
