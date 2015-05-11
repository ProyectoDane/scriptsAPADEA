package com.globant.scriptsapadea.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.globant.scriptsapadea.R;

/**
 * Created by nicolas.quartieri
 */
public class SliderFragment extends BaseFragment {

    private static final String EXTRA_MESSAGE = "message";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String message = getArguments().getString(EXTRA_MESSAGE);

        View view = inflater.inflate(R.layout.slider_layout, container, false);

        // TODO Remove
        TextView title = (TextView) view.findViewById(R.id.txt_title);
        title.setText(message);

        return view;
    }

    public static Object newInstance(String message) {
        SliderFragment slider = new SliderFragment();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MESSAGE, message);

        slider.setArguments(bundle);

        return slider;
    }
}
