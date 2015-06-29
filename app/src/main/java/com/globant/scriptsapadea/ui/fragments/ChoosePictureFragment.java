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
import com.globant.scriptsapadea.interfaces.OnNavigateToFragmentListener;
import com.globant.scriptsapadea.manager.ActivityResultEvent;
import com.globant.scriptsapadea.utils.PictureUtils;
import com.squareup.otto.Subscribe;

/**
 * Created by leonel.mendez on 5/19/2015.
 */
public class ChoosePictureFragment extends BaseFragment {

    private static final int GALLERY = 0x001;
    private static final int CAMERA = 0x010;
    private OnNavigateToFragmentListener onNavigateToFragmentListener;

    public static ChoosePictureFragment newInstance(Bundle args) {
        ChoosePictureFragment fragment = new ChoosePictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onNavigateToFragmentListener = (OnNavigateToFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + "must be implements OnSetPictureFragmentImageListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_picture, container, false);

        TextView txtPatientName = (TextView) view.findViewById(R.id.txt_patient_name);

        if (getArguments() != null) {
            String name = getArguments().getString(ScreenPlayFragment.PATIENT_NAME);
            txtPatientName.setText(name);
        }

        ImageView pickPhotoGallery = (ImageView) view.findViewById(R.id.img_gallery);
        pickPhotoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtils.pickPhotoFromGallery(ChoosePictureFragment.this,GALLERY);
            }
        });

        ImageView takePhotoCamera = (ImageView) view.findViewById(R.id.img_camera);
        takePhotoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtils.takePhotoFromCamera(ChoosePictureFragment.this,CAMERA);
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
        imageArguments.putString(ScreenPlayFragment.PATIENT_NAME, getArguments().getString(ScreenPlayFragment.PATIENT_NAME));
        if (requestCode == GALLERY) {
            if (data != null && data.getData() != null) {
                imageArguments.putString(ShowPictureFragment.SCREENPLAY_IMAGE, PictureUtils.getImagePath(getActivity(), data.getData()));
                imageArguments.putBoolean(ShowPictureFragment.PICTURE_FROM_CAMERA, false);
                imageArguments.putBoolean(ScreenPlayFragment.IS_CREATING_SCREENPLAY,getArguments().getBoolean(ScreenPlayFragment.IS_CREATING_SCREENPLAY,false));
                onNavigateToFragmentListener.onNavigateToFragment(ShowPictureFragment.newInstance(imageArguments));
            }
        } else {
            if (data != null && data.getExtras() != null) {
                // TODO: Create folder to save images from camera
                imageArguments.putParcelable(ShowPictureFragment.SCREENPLAY_IMAGE, ((Bitmap) data.getExtras().get("data")));
                imageArguments.putBoolean(ShowPictureFragment.PICTURE_FROM_CAMERA, true);
                imageArguments.putBoolean(ScreenPlayFragment.IS_CREATING_SCREENPLAY,getArguments().getBoolean(ScreenPlayFragment.IS_CREATING_SCREENPLAY,false));
                onNavigateToFragmentListener.onNavigateToFragment(ShowPictureFragment.newInstance(imageArguments));
            }
        }
    }



}
