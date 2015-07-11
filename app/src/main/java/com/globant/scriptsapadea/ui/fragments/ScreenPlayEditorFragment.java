package com.globant.scriptsapadea.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.globant.scriptsapadea.R;

/**
 * Created by leonel.mendez on 6/23/2015.
 */
public class ScreenPlayEditorFragment extends Fragment{

    public static ScreenPlayEditorFragment newInstance(Bundle args){
        ScreenPlayEditorFragment screenPlayEditorFragment = new ScreenPlayEditorFragment();
        screenPlayEditorFragment.setArguments(args);
        return screenPlayEditorFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_screenplay_editor,container,false);
    }
}
