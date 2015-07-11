package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class ShowPictureFragment extends BaseFragment {

    public static final String SCREENPLAY_IMAGE = "picture_image";
    public static final String PICTURE_FROM_CAMERA = "picture_from_camera";
    private OnNextFragmentListener nextFragmentListener;

    public static ShowPictureFragment newInstance(Bundle imageBundle) {
        ShowPictureFragment showPictureFragment = new ShowPictureFragment();
        showPictureFragment.setArguments(imageBundle);
        return showPictureFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
           nextFragmentListener = (OnNextFragmentListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.getLocalClassName() + "must implement OnNavigateToFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_picture, container, false);
        TextView screenplayName = (TextView) mainView.findViewById(R.id.txt_patient_name);
        if (getArguments() != null) {
            screenplayName.setText(getArguments().getString(ScreenPlayFragment.PATIENT_NAME));
            showImage(getArguments(), (ImageView) mainView.findViewById(R.id.screenplay_image));

            mainView.findViewById(R.id.start_edit_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNextFragment(getArguments().getBoolean(ScreenPlayFragment.IS_CREATING_SCREENPLAY,false));
                }
            });
        }
        return mainView;
    }

    private void showImage(Bundle imageBundle, ImageView imageContainer) {

        boolean pictureFromCamera = imageBundle.getBoolean(PICTURE_FROM_CAMERA);
        if (pictureFromCamera) {
            imageContainer.setImageBitmap((Bitmap) imageBundle.getParcelable(SCREENPLAY_IMAGE));
        } else {
            Picasso.with(getActivity())
                    .load(new File(imageBundle.getString(SCREENPLAY_IMAGE)))
                    .into(imageContainer);
        }
    }

    private void setNextFragment(boolean isCreatingScreenPlay){
        Bundle args = new Bundle();
        if(isCreatingScreenPlay){
            nextFragmentListener.onNextFragment(ScreenPlayEditorFragment.newInstance(args));
        }else{
            args.putBoolean(ScreenPlayFragment.IS_CREATING_SCREENPLAY,true);
            nextFragmentListener.onNextFragment(ScreenPlayFragment.newInstance(args));
        }
    }

    public interface OnNextFragmentListener{
        void onNextFragment(Fragment fragment);
    }
}
