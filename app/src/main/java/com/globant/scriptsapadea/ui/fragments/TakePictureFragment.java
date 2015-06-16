package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.manager.ActivityResultEvent;
import com.globant.scriptsapadea.utils.ImageRealPath;
import com.squareup.otto.Subscribe;

/**
 * Created by leonel.mendez on 5/19/2015.
 */
public class TakePictureFragment extends BaseFragment {

    private static final String PATIENT_NAME = "patientname";
    private static final int GALLERY = 0x001;
    private static final int CAMERA = 0x010;
    private PictureFragment.OnSetPictureFragmentImageListener onSetPictureFragmentImageListener;

    public static TakePictureFragment newInstance(String patientName) {
        TakePictureFragment fragment = new TakePictureFragment();
        Bundle args = new Bundle();
        args.putString(PATIENT_NAME, patientName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onSetPictureFragmentImageListener = (PictureFragment.OnSetPictureFragmentImageListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + "must be implements OnSetPictureFragmentImageListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_picture, container, false);

        TextView txtPatientName = (TextView) view.findViewById(R.id.txt_patient_name);

        if (getArguments() != null) {
            String name = getArguments().getString(PATIENT_NAME);
            txtPatientName.setText(name);
        }

        ImageView pickPhotoGallery = (ImageView) view.findViewById(R.id.img_gallery);
        pickPhotoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhotoFromGallery();

            }
        });

        ImageView takePhotoCamera = (ImageView) view.findViewById(R.id.img_camera);
        takePhotoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoFromCamera();

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
        imageArguments.putString(PictureFragment.PATIENT_NAME, getArguments().getString(PATIENT_NAME));
        if (requestCode == GALLERY) {
            if (data != null && data.getData() != null) {
                imageArguments.putString(PictureFragment.SCREENPLAY_IMAGE, ImageRealPath.getImagePath(getActivity(), data.getData()));
                imageArguments.putBoolean(PictureFragment.PICTURE_FROM_CAMERA, false);
                onSetPictureFragmentImageListener.onSetImage(imageArguments);
            }
        } else {
            if (data != null && data.getExtras() != null) {
                // TODO: Create folder to save images from camera
                imageArguments.putParcelable(PictureFragment.SCREENPLAY_IMAGE, ((Bitmap) data.getExtras().get("data")));
                imageArguments.putBoolean(PictureFragment.PICTURE_FROM_CAMERA, true);
                onSetPictureFragmentImageListener.onSetImage(imageArguments);
            }
        }
    }

    private void pickPhotoFromGallery() {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, GALLERY);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY);
        }
    }

    private void takePhotoFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA);
    }

}
