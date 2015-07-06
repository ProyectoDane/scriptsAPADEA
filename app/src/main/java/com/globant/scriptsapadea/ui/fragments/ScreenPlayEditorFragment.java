package com.globant.scriptsapadea.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.manager.ActivityResultEvent;
import com.globant.scriptsapadea.manager.ScreenPlayEditorManager;
import com.globant.scriptsapadea.models.Slide;
import com.globant.scriptsapadea.ui.adapters.SlideSelectorRecyclerAdapter;
import com.globant.scriptsapadea.utils.PictureUtils;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by leonel.mendez on 6/23/2015.
 */
public class ScreenPlayEditorFragment extends Fragment{

    private ScreenPlayEditorManager screenPlayEditorManager;
    private static final int REQUEST_CODE_GALLERY = 0x100;
    private static final int REQUEST_CODE_CAMERA = 0x010;
    private ImageView slidePicture;
    private String imageGalleryUrl;


    public static ScreenPlayEditorFragment newInstance(Bundle args){
        ScreenPlayEditorFragment screenPlayEditorFragment = new ScreenPlayEditorFragment();
        screenPlayEditorFragment.setArguments(args);
        return screenPlayEditorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenPlayEditorManager = new ScreenPlayEditorManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_screenplay_editor,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        slidePicture = (ImageView)view.findViewById(R.id.screenplay_slide_image);
        final EditText slideDesc = (EditText)view.findViewById(R.id.editor_slide_text);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.screenplay_slide_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SlideSelectorRecyclerAdapter slideSelectorRecyclerAdapter = new SlideSelectorRecyclerAdapter(screenPlayEditorManager);
        recyclerView.setAdapter(slideSelectorRecyclerAdapter);

        view.findViewById(R.id.editor_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtils.pickPhotoFromGallery(ScreenPlayEditorFragment.this, REQUEST_CODE_GALLERY);
            }
        });

        view.findViewById(R.id.editor_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtils.takePhotoFromCamera(ScreenPlayEditorFragment.this, REQUEST_CODE_CAMERA);
            }
        });
        screenPlayEditorManager.addSlide(new Slide(getString(R.string.editor_add_button), getString(R.string.editor_add_button), 0));
        slideSelectorRecyclerAdapter.setOnSlideSelectorItemClickListener(new SlideSelectorRecyclerAdapter.OnSlideSelectorItemClickListener() {
            @Override
            public void onSlideSelectorItemClick(RecyclerView.Adapter adapter, View view, int position) {
                if(position == 0) {
                    Slide slide = screenPlayEditorManager.createSlide("slide " + (position + 1), imageGalleryUrl, slideDesc.getText().toString(),Slide.IMAGE_TEXT);
                    screenPlayEditorManager.addSlide(slide);
                    Toast.makeText(getActivity(), "position: " + position, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Subscribe
    public void onActivityResultReceived(ActivityResultEvent event) {
        onActivityResult(event.getRequestCode(), event.getResultCode(), event.getData());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showImage(data,requestCode);
    }

    private void showImage(Intent data, int requestCode){
        if (data != null ) {
            if (requestCode == REQUEST_CODE_GALLERY) {
                imageGalleryUrl = PictureUtils.getImagePath(getActivity(), data.getData());
                Picasso.with(getActivity())
                        .load(new File(imageGalleryUrl))
                        .into(slidePicture);
            } else {
                if(data.getExtras() != null) {
                    slidePicture.setImageBitmap((Bitmap) data.getExtras().get("data"));
                }
            }
        }
    }
}
