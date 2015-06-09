package com.globant.scriptsapadea.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.manager.ActivityResultEvent;
import com.squareup.otto.Subscribe;

/**
 * Created by leonel.mendez on 5/19/2015.
 */
public class PictureFragment extends BaseFragment implements View.OnClickListener {

    private static final String PATIENT_NAME = "patientname";

    private static int GALLERY = 0x001;
    private static int CAMERA = 0x010;

    public static PictureFragment newInstance(String patientName) {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        args.putString(PATIENT_NAME, patientName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);

        TextView txtPatientName = (TextView) view.findViewById(R.id.txt_patient_name);

        if (getArguments() != null) {
            String name = getArguments().getString(PATIENT_NAME);
            txtPatientName.setText(name);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        (view.findViewById(R.id.img_gallery)).setOnClickListener(PictureFragment.this);
        (view.findViewById(R.id.img_camera)).setOnClickListener(PictureFragment.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_gallery:
                pickPhotoFromGallery();
                break;
            case R.id.img_camera:
                takePhotoFromCamera();
                break;
        }
    }

    @Subscribe
    public void onActivityResultReceived(ActivityResultEvent event) {
        onActivityResult(event.getRequestCode(), event.getResultCode(), event.getData());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(PictureFragment.class.toString(), requestCode + "");
    }

    private void pickPhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA);
    }
}
