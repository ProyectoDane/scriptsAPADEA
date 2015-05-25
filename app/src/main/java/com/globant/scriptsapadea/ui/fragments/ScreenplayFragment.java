package com.globant.scriptsapadea.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.ui.activities.MainActivity;

import roboguice.inject.InjectView;

/**
 * Created by leonel.mendez on 5/8/2015.
 */
public class ScreenplayFragment extends BaseFragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guion,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        EditText screenplayName = (EditText)view.findViewById(R.id.screenplay_name);
        Button nextButton = (Button)view.findViewById(R.id.next_button);

        showNextButton(screenplayName,nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PictureFragment()).addToBackStack("picture_fragment").commit();
            }
        });
    }


    //Method to show the hidden next button
    private void showNextButton(EditText screenplayName, final Button nextButton){
        screenplayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                nextButton.setVisibility(View.VISIBLE);
                if (s.toString().length() == 0) {
                    nextButton.setVisibility(View.GONE);
                }

            }
        });
    }




}
