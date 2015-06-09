package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.globant.scriptsapadea.R;

/**
 * Created by leonel.mendez on 5/8/2015.
 */
public class ScreenPlayFragment extends BaseFragment {

    private OnScreenplayChangeFragmentListener screenplayChangeFragmentListener;

    private EditText screenplayName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guion, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        screenplayName = (EditText) view.findViewById(R.id.screenplay_name);
        Button nextButton = (Button) view.findViewById(R.id.next_button);

        showNextButton(screenplayName, nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenplayChangeFragmentListener.onNextButtonClicked(screenplayName.getText().toString());
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            screenplayChangeFragmentListener = (OnScreenplayChangeFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getLocalClassName() +  "must be implements OnScreenplayChangeFragmentListener");
        }
    }

    //Method to show the hidden next button
    private void showNextButton(EditText screenplayName, final Button nextButton) {
        screenplayName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used
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

    public interface OnScreenplayChangeFragmentListener {
        void onNextButtonClicked(String name);
    }
}
