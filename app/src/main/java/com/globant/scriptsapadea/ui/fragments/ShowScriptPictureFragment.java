package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
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
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by nicolas.quartieri on 6/11/2015.
 */
public class ShowScriptPictureFragment extends BaseFragment {

    public static final String SCREENPLAY_IMAGE = "picture_image";
    public static final String PICTURE_FROM_CAMERA = "picture_from_camera";

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

        if (getArguments() != null) {
            screenplayName.setText(getArguments().getString(CreatePatientFragment.PATIENT_NAME));
            showImage(getArguments(), (ImageView) mainView.findViewById(R.id.screenplay_image));

            mainView.findViewById(R.id.start_edit_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();

                    mDBHelper.createPatient(patientManager.getSelectedPactient());

                    listener.onEditFragment(ScreenPlayEditorFragment.newInstance(args));
                }
            });
        }

        return mainView;
    }

    private void showImage(Bundle imageBundle, ImageView imageContainer) {
        boolean pictureFromCamera = imageBundle.getBoolean(PICTURE_FROM_CAMERA);

        Patient patient = patientManager.getSelectedPactient();
        Script script = patient.getScriptList().get(0);

        if (pictureFromCamera) {
            // TODO
            //imageContainer.setImageBitmap((Bitmap) imageBundle.getParcelable(SCREENPLAY_IMAGE));
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
