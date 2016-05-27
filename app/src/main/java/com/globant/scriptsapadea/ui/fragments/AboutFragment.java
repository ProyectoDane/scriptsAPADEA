package com.globant.scriptsapadea.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.globant.scriptsapadea.R;

/**
 * @author nicolas.quartieri
 */
public class AboutFragment extends BaseFragment {

    public static AboutFragment newInstance(Bundle args) {
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_screen, container, false);

        return view;
    }

    public interface AboutListener {
        void onTakeToAboutScreen();
    }
}
