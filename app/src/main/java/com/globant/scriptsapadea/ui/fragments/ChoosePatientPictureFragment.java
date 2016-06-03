package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.manager.ActivityResultEvent;
import com.globant.scriptsapadea.manager.PatientManager;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.utils.PictureUtils;
import com.globant.scriptsapadea.utils.TEAlertDialog;
import com.squareup.otto.Subscribe;

import java.io.File;

import javax.inject.Inject;

/**
 * @author leonel.mendez on 5/19/2015.
 */
public class ChoosePatientPictureFragment extends BaseFragment {

    private static final int GALLERY = 0x001;
    private static final int CAMERA = 0x010;

    @Inject
    private PatientManager patientManager;

    private OnShowPatientPictureFragmentListener showPictureFragmentListener;
    private File photoFile;

    public static ChoosePatientPictureFragment newInstance(Bundle args) {
        ChoosePatientPictureFragment fragment = new ChoosePatientPictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            showPictureFragmentListener = (OnShowPatientPictureFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + "must be implements OnSetPictureFragmentImageListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_picture, container, false);

        TextView txtPatientName = (TextView) view.findViewById(R.id.txt_patient_name);

        if (getArguments() != null) {
            String name = getArguments().getString(CreatePatientFragment.PATIENT_NAME);
            txtPatientName.setText(name);
        }

        ImageView pickPhotoGallery = (ImageView) view.findViewById(R.id.img_gallery);
        pickPhotoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtils.pickPhotoFromGallery(ChoosePatientPictureFragment.this, GALLERY);
            }
        });

        ImageView takePhotoCamera = (ImageView) view.findViewById(R.id.img_camera);
        takePhotoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoFile = PictureUtils.createFilePhotoForCamera();
                PictureUtils.takePhotoFromCamera(ChoosePatientPictureFragment.this, CAMERA, photoFile);
            }
        });

        return view;
    }

    @Subscribe
    public void onActivityResultReceived(ActivityResultEvent event) {
        onActivityResult(event.getRequestCode(), event.getResultCode(), event.getData());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle imageArguments = new Bundle();
        if (requestCode == GALLERY) {
            String imagePath = PictureUtils.getImagePath(getActivity(), data.getData(), true);
            if (data.getData() != null && imagePath != null) {
                patientManager.setSelectedPatient(new Patient(0, getArguments().getString(CreatePatientFragment.PATIENT_NAME),
                        imagePath, true));

                showPictureFragmentListener.onShowPictureFragment(ShowPatientPictureFragment.newInstance(imageArguments));
            } else {
                TEAlertDialog alert = new TEAlertDialog(getContext());
                alert.setTitle(R.string.error_image).show();
            }
        } else {
            if (photoFile != null && photoFile.exists()) {
                imageArguments.putSerializable(ShowPatientPictureFragment.PATIENT_IMAGE, photoFile);
                imageArguments.putBoolean(ShowPatientPictureFragment.PICTURE_FROM_CAMERA, true);

                patientManager.setSelectedPatient(new Patient(0, getArguments().getString(CreatePatientFragment.PATIENT_NAME),
                        photoFile.getAbsolutePath(), true));

                showPictureFragmentListener.onShowPictureFragment(ShowPatientPictureFragment.newInstance(imageArguments));
            }
        }
    }

    public interface OnShowPatientPictureFragmentListener {
        void onShowPictureFragment(Fragment fragment);
    }
}
