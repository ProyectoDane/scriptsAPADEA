package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.manager.PatientManager;
import com.globant.scriptsapadea.models.Patient;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Inject;

/**
 * @author leonel.mendez on 6/11/2015.
 */
public class ShowPatientPictureFragment extends BaseFragment {

    public static final String PATIENT_IMAGE = "patient_image";
    public static final String PICTURE_FROM_CAMERA = "picture_from_camera";

    private OnNextFragmentListener listener;

    @Inject
    private PatientManager patientManager;

    public static ShowPatientPictureFragment newInstance(Bundle imageBundle) {
        ShowPatientPictureFragment showPatientPictureFragment = new ShowPatientPictureFragment();
        showPatientPictureFragment.setArguments(imageBundle);
        return showPatientPictureFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnNextFragmentListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.getLocalClassName() + "must implement OnNavigateToFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_picture, container, false);
        TextView screenplayName = (TextView) mainView.findViewById(R.id.txt_patient_name);

        if (getArguments() != null) {
            screenplayName.setText(getArguments().getString(CreatePatientFragment.PATIENT_NAME));
            showImage(getArguments(), (ImageView) mainView.findViewById(R.id.screenplay_image));

            mainView.findViewById(R.id.start_edit_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    listener.onCreateScript(CreateScriptFragment.newInstance(args));
                }
            });
        }

        return mainView;
    }

    private void showImage(Bundle imageBundle, ImageView imageContainer) {
        boolean pictureFromCamera = imageBundle.getBoolean(PICTURE_FROM_CAMERA, false);

        Patient patient = patientManager.getSelectedPatient();

        if (pictureFromCamera) {
            File photoFile = (File) imageBundle.getSerializable(PATIENT_IMAGE);
            if (photoFile != null && photoFile.exists()) {
                Uri uri = Uri.fromFile(photoFile);
                imageContainer.setImageURI(uri);
            }
        } else {
            if (patient.isResourceAvatar()) {
                Picasso.with(getActivity())
                        .load(patient.getResAvatar())
                        .into(imageContainer);
            } else {
                Picasso.with(getActivity())
                        .load(new File(patient.getAvatar()))
                        .into(imageContainer);
            }
        }
    }

    public interface OnNextFragmentListener {
        void onCreateScript(Fragment fragment);
    }
}
