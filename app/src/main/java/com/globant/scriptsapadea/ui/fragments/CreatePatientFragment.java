package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;

/**
 * Created by leonel.mendez on 5/8/2015.
 */
public class CreatePatientFragment extends BaseFragment {

    private OnChangePictureFragmentListener listener;

    private EditText screenplayName;

    private Patient patient = null;

    public static final String PATIENT_NAME = "patientname";

    public static CreatePatientFragment newInstance(Bundle bundle) {
        CreatePatientFragment screenPlayFragment = new CreatePatientFragment();
        screenPlayFragment.setArguments(bundle);
        return screenPlayFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("patient")) {
            patient = (Patient) bundle.getSerializable("patient");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_screenplay, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle takePictureArgs = new Bundle();

        final TextView panelType = (TextView) view.findViewById(R.id.whose_is_screenplay_text);
        panelType.setText(getString(R.string.what_is_person_name));

        screenplayName = (EditText) view.findViewById(R.id.screenplay_name);
        Button nextButton = (Button) view.findViewById(R.id.next_button);

        if (patient != null) { // Edit MODE
            screenplayName.setText(patient.getName());
            nextButton.setText(R.string.save_action_text);
            takePictureArgs.putSerializable("edit_mode", true);
            takePictureArgs.putSerializable("patient", patient);
        } else {
            takePictureArgs.putSerializable("edit_mode", false);
        }

        showNextButton(screenplayName, nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureArgs.putString(PATIENT_NAME, screenplayName.getText().toString());

                listener.onChangePictureFragment(ChoosePatientPictureFragment.newInstance(takePictureArgs));
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnChangePictureFragmentListener) activity;
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

    public interface OnChangePictureFragmentListener {
        void onChangePictureFragment(Fragment fragment);
    }
}
