package com.globant.scriptsapadea.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by leonel.mendez on 6/11/2015.
 */
public class PictureFragment extends BaseFragment {

    public static final String SCREENPLAY_IMAGE = "picture_image";
    public static final String PICTURE_FROM_CAMERA = "picture_from_camera";
    public static final String PATIENT_NAME = "patient_name";

    public static PictureFragment newInstance(Bundle imageBundle) {
        PictureFragment pictureFragment = new PictureFragment();
        pictureFragment.setArguments(imageBundle);
        return pictureFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_picture, container, false);

        TextView screenplayName = (TextView) mainView.findViewById(R.id.txt_patient_name);
        if (getArguments() != null) {
            screenplayName.setText(getArguments().getString(PATIENT_NAME));
            showImage(getArguments(), (ImageView) mainView.findViewById(R.id.screenplay_image));
        }
        return mainView;
    }

    private void showImage(Bundle imageBundle, ImageView imageContainer) {

        boolean pictureFromCamera = imageBundle.getBoolean(PICTURE_FROM_CAMERA);
        if (pictureFromCamera) {
            imageContainer.setImageBitmap((Bitmap) imageBundle.getParcelable(SCREENPLAY_IMAGE));
        } else {
            Log.d(PictureFragment.class.getSimpleName(), imageBundle.getString(SCREENPLAY_IMAGE));
            Picasso.with(getActivity())
                    .load(new File(imageBundle.getString(SCREENPLAY_IMAGE)))
                    .into(imageContainer);
        }
    }

    public interface OnSetPictureFragmentImageListener {
        void onSetImage(Bundle imageBundle);
    }
}
