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
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.interfaces.OnNavigateToFragmentListener;

/**
 * Created by leonel.mendez on 5/8/2015.
 */
public class ScreenPlayFragment extends BaseFragment {

   private OnNavigateToFragmentListener navigateToFragmentListener;

    private EditText screenplayName;
    public static final String PATIENT_NAME = "patientname";
    public static final String IS_CREATING_SCREENPLAY = "is_creating_guion";

    public static ScreenPlayFragment newInstance(Bundle args){
        ScreenPlayFragment screenPlayFragment = new ScreenPlayFragment();
        screenPlayFragment.setArguments(args);
        return screenPlayFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_screenplay, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            setScreenPlayPanelConfiguration(getArguments().getBoolean(IS_CREATING_SCREENPLAY),view);
        }
        screenplayName = (EditText) view.findViewById(R.id.screenplay_name);
        Button nextButton = (Button) view.findViewById(R.id.next_button);

        showNextButton(screenplayName, nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle takePictureArgs = new Bundle();
                takePictureArgs.putString(PATIENT_NAME, screenplayName.getText().toString());
                takePictureArgs.putBoolean(IS_CREATING_SCREENPLAY, getArguments() != null);
                navigateToFragmentListener.onNavigateToFragment(TakePictureFragment.newInstance(takePictureArgs));
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            navigateToFragmentListener = (OnNavigateToFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getLocalClassName() +  "must be implements OnScreenplayChangeFragmentListener");
        }
    }

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

    private void setScreenPlayPanelConfiguration(boolean isCreatingScreenPlay,View mainContainer){
        TextView panelType = (TextView)mainContainer.findViewById(R.id.whose_is_screenplay_text);
        if(isCreatingScreenPlay){
            panelType.setText(getString(R.string.what_is_guion_name_text));
        }else{
            panelType.setText(getString(R.string.what_is_person_name));
        }
    }
}
