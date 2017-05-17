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
import com.globant.scriptsapadea.manager.PatientManager;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.utils.PictureUtils;
import com.globant.scriptsapadea.utils.TEAlertDialog;

import java.io.File;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/**
 * @author leonel.mendez on 5/19/2015.
 */
public class ChooseScriptPictureFragment extends BaseFragment {

    private static final int GALLERY = 0x001;
    private static final int CAMERA = 0x010;

    private OnShowScriptPictureFragmentListener listener;

    @Inject
    private PatientManager patientManager;
    private File photoFile;

    public static ChooseScriptPictureFragment newInstance(Bundle args) {
        ChooseScriptPictureFragment fragment = new ChooseScriptPictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnShowScriptPictureFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + " must be implements OnShowScriptPictureFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_picture_script, container, false);

        TextView txtPatientName = (TextView) view.findViewById(R.id.txt_script_name);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = null;
            if (bundle.containsKey(CreateScriptFragment.SCRIPT_NAME)) {
                name = bundle.getString(CreateScriptFragment.SCRIPT_NAME);
            } else if (bundle.containsKey(CreatePatientFragment.PATIENT_NAME)) {
                name = bundle.getString(CreatePatientFragment.PATIENT_NAME);
            }
            txtPatientName.setText(name);

            if (bundle.containsKey(Script.SCRIPT)) {
                Script script = (Script) bundle.getSerializable(Script.SCRIPT);
                patientManager.setSelectedScript(script);
            }
        }

        ImageView pickPhotoGallery = (ImageView) view.findViewById(R.id.img_gallery);
        pickPhotoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtils.pickPhotoFromGallery(ChooseScriptPictureFragment.this,GALLERY);
            }
        });

        ImageView takePhotoCamera = (ImageView) view.findViewById(R.id.img_camera);
        takePhotoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoFile = PictureUtils.createFilePhotoForCamera();
                PictureUtils.takePhotoFromCamera(ChooseScriptPictureFragment.this,CAMERA, photoFile);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            long scriptId = 0; //new script

            Bundle imageArguments = getArguments();
            if (imageArguments == null) {
                imageArguments = new Bundle();
            } else if (imageArguments.containsKey("edit_mode") && imageArguments.getBoolean("edit_mode")) {
                Script script = (Script) imageArguments.get(Script.SCRIPT);
                scriptId = script.getId();
            }

            if (requestCode == GALLERY) {
                String imagePath = photoFile.getAbsolutePath();
                if (data.getData() != null && imagePath != null) {
                    imageArguments.putString(ShowPatientPictureFragment.PATIENT_IMAGE, imagePath);

                    patientManager.setSelectedScript(new Script(scriptId, getArguments().getString(CreateScriptFragment.SCRIPT_NAME),
                            imagePath, true));

                    listener.onShowScriptPictureFragment(ShowScriptPictureFragment.newInstance(imageArguments));
                } else {
                    TEAlertDialog alert = new TEAlertDialog(getContext());
                    alert.setTitle(R.string.error_image).show();
                }
            } else {
                if (photoFile != null && photoFile.exists()) {
                    imageArguments.putSerializable(ShowScriptPictureFragment.SCRIPT_IMAGE, photoFile);
                    imageArguments.putBoolean(ShowPatientPictureFragment.PICTURE_FROM_CAMERA, true);

                    patientManager.setSelectedScript(new Script(scriptId, getArguments().getString(CreateScriptFragment.SCRIPT_NAME),
                            photoFile.getAbsolutePath(), true));

                    listener.onShowScriptPictureFragment(ShowScriptPictureFragment.newInstance(imageArguments));
                }
            }
        }
    }

    public interface OnShowScriptPictureFragmentListener{
        void onShowScriptPictureFragment(Fragment fragment);
    }
}
