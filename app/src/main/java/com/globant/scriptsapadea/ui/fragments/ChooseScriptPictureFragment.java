package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.utils.PictureUtils;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by leonel.mendez on 5/19/2015.
 */
public class ChooseScriptPictureFragment extends BaseFragment {

    private static final int GALLERY = 0x001;
    private static final int CAMERA = 0x010;

    private OnShowScriptPictureFragmentListener listener;

    @Inject
    private PatientManager patientManager;

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
            throw new ClassCastException(getActivity().getLocalClassName() + "must be implements OnShowScriptPictureFragmentListener");
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
                PictureUtils.pickPhotoFromGallery(ChooseScriptPictureFragment.this,GALLERY);
            }
        });

        ImageView takePhotoCamera = (ImageView) view.findViewById(R.id.img_camera);
        takePhotoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtils.takePhotoFromCamera(ChooseScriptPictureFragment.this,CAMERA);
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
            if (data != null && data.getData() != null) {
                imageArguments.putString(ShowPatientPictureFragment.PATIENT_IMAGE, PictureUtils.getImagePath(getActivity(), data.getData()));

                List<Script> list = new ArrayList<>();
                list.add(new Script(0, getArguments().getString(CreateScriptFragment.SCRIPT_NAME),
                        PictureUtils.getImagePath(getActivity(), data.getData())));

                patientManager.getSelectedPatient().setScriptList(list);

                listener.onShowScriptPictureFragment(ShowScriptPictureFragment.newInstance(imageArguments));
            }
        } else {
            if (data != null && data.getExtras() != null) {
                // TODO: Create folder to save images from camera
                imageArguments.putParcelable(ShowPatientPictureFragment.PATIENT_IMAGE, ((Bitmap) data.getExtras().get("data")));

                listener.onShowScriptPictureFragment(ShowScriptPictureFragment.newInstance(imageArguments));
            }
        }
    }


    public interface OnShowScriptPictureFragmentListener{
        void onShowScriptPictureFragment(Fragment fragment);
    }
}
