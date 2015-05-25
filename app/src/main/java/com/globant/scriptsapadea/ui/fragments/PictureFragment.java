package com.globant.scriptsapadea.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.utils.ActivityResultBus;
import com.globant.scriptsapadea.utils.ActivityResultEvent;
import com.squareup.otto.Subscribe;

import javax.xml.transform.Result;

/**
 * Created by leonel.mendez on 5/19/2015.
 */
public class PictureFragment extends BaseFragment implements View.OnClickListener{


    private static int GALLERY = 0x001;
    private static int CAMERA = 0x010;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(PictureFragment.this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture,container,false);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        (view.findViewById(R.id.fromGallery)).setOnClickListener(PictureFragment.this);
        (view.findViewById(R.id.fromCamera)).setOnClickListener(PictureFragment.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

          case  R.id.fromGallery:
              pickPhotoFromGallery();
            break;
           case R.id.fromCamera:
               takePhotoFromCamera();
            break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        ActivityResultBus.getInstance().unregister(PictureFragment.this);
    }

    @Subscribe
    public void onActivityResultReceived(ActivityResultEvent event){
        onActivityResult(event.getRequestCode(),event.getResultCode(),event.getData());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(PictureFragment.class.toString(),requestCode + "");
    }

    private void pickPhotoFromGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GALLERY);
    }

    private void takePhotoFromCamera(){

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,CAMERA);
    }
}
