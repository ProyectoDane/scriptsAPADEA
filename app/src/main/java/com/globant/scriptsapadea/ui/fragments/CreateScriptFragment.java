package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.globant.scriptsapadea.R;

/**
 * This class provides the setup step for a new Script.
 *
 * @author leonel.mendez on 5/8/2015.
 */
public class CreateScriptFragment extends BaseFragment {

    private OnTakeScriptPictureFragmentListener listener;

    private EditText screenplayName;

    public static final String SCRIPT_NAME = "scriptname";

    public static CreateScriptFragment newInstance(Bundle args) {
        CreateScriptFragment screenPlayFragment = new CreateScriptFragment();
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

        TextView panelType = (TextView) view.findViewById(R.id.whose_is_screenplay_text);
        panelType.setText(getString(R.string.what_is_guion_name_text));

        screenplayName = (EditText) view.findViewById(R.id.screenplay_name);
        Button nextButton = (Button) view.findViewById(R.id.next_button);

        showNextButton(screenplayName, nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle takePictureArgs = new Bundle();
                takePictureArgs.putString(SCRIPT_NAME, screenplayName.getText().toString());

                // Hide Keyboard
                InputMethodManager imm = (InputMethodManager)CreateScriptFragment.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                listener.onTakeScriptPictureFragment(ChooseScriptPictureFragment.newInstance(takePictureArgs));
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnTakeScriptPictureFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getLocalClassName() +  "must be implements OnTakeScriptPictureFragmentListener");
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

    public interface OnTakeScriptPictureFragmentListener {
        void onTakeScriptPictureFragment(Fragment fragment);
    }
}
