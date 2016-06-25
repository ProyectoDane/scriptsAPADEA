package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.manager.PatientManager;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Inject;

/**
 * This class provides the screen to select an images or take a camera picture to identify a Patient.
 *
 * @author nicolas.quartieri
 */
public class ShowScriptPictureFragment extends BaseFragment {

    public static final String SCREENPLAY_IMAGE = "picture_image";
    public static final String PICTURE_FROM_CAMERA = "picture_from_camera";
    public static final String SCRIPT_IMAGE = "script_image";

    private OnEditFragmentListener listener;

    @Inject
    private PatientManager patientManager;

    @Inject
    private SQLiteHelper mDBHelper;

    public static ShowScriptPictureFragment newInstance(Bundle imageBundle) {
        ShowScriptPictureFragment showPictureFragment = new ShowScriptPictureFragment();
        showPictureFragment.setArguments(imageBundle);
        return showPictureFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
           listener = (OnEditFragmentListener) activity;
        }catch (ClassCastException e){
           throw new ClassCastException(activity.getLocalClassName() + "must implement OnEditFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_picture, container, false);
        TextView screenplayName = (TextView) mainView.findViewById(R.id.txt_patient_name);
        Button editButton = (Button) mainView.findViewById(R.id.start_edit_button);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("edit_mode") && bundle.getBoolean("edit_mode")) {
                editButton.setText(R.string.save_action_text);
                final Script script = patientManager.getSelectedScript();

                // Update Script
                screenplayName.setText(getArguments().getString(CreatePatientFragment.PATIENT_NAME));
                showImage(getArguments(), (ImageView) mainView.findViewById(R.id.screenplay_image));

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        int value = mDBHelper.updateScript(script);

                        getActivity().finish();
                    }
                });
            } else {
                screenplayName.setText(getArguments().getString(CreatePatientFragment.PATIENT_NAME));
                showImage(getArguments(), (ImageView) mainView.findViewById(R.id.screenplay_image));

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        listener.onEditFragment(ScreenPlayEditorFragment.newInstance(args, false));
                    }
                });
            }
        }

        return mainView;
    }

    /**
     * Show the image after been taken.
     *
     * @param imageBundle    - the image bundle with the contained path to the image.
     * @param imageContainer - the ImageView for this image selected.
     */
    private void showImage(Bundle imageBundle, ImageView imageContainer) {
        boolean pictureFromCamera = imageBundle.getBoolean(PICTURE_FROM_CAMERA);

        Script script = patientManager.getSelectedScript();
        patientManager.getSelectedPatient().getScriptList().add(script);

        if (pictureFromCamera) {
            File photoFile = (File) imageBundle.getSerializable(SCRIPT_IMAGE);
            if (photoFile != null && photoFile.exists()) {
                Uri uri = Uri.fromFile(photoFile);
                imageContainer.setImageURI(uri);
            }
        } else {
            if (script.isResourceImage()) {
                Picasso.with(getActivity())
                        .load(script.getResImage())
                        .into(imageContainer);
            } else {
                Picasso.with(getActivity())
                        .load(new File(script.getImageScripts()))
                        .into(imageContainer);
            }
        }
    }

    public interface OnEditFragmentListener{
        void onEditFragment(Fragment fragment);
    }
}
