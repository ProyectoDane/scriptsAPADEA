package com.globant.scriptsapadea.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.globant.scriptsapadea.R;

/**
 * Created by leonel.mendez on 5/19/2015.
 */
public class PictureFragment extends BaseFragment implements View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture,container,false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        (view.findViewById(R.id.fromGallery)).setOnClickListener(PictureFragment.this);
        (view.findViewById(R.id.fromCamera)).setOnClickListener(PictureFragment.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

          case  R.id.fromGallery:
            break;
           case R.id.fromCamera:
            break;
        }
    }

    
}
