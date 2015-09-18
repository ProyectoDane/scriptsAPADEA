package com.globant.scriptsapadea.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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
public class ScreenPlayEditorFragment extends BaseFragment {

    private static final int REQUEST_CODE_GALLERY = 0x100;
    private static final int REQUEST_CODE_CAMERA = 0x010;
    private static final int INITIAL_POSITION = 0;

    private ScreenPlayEditorManager screenPlayEditorManager;
    private ImageView slidePicture;
    private String imageGalleryUrl;


    public static ScreenPlayEditorFragment newInstance(Bundle args) {
        ScreenPlayEditorFragment screenPlayEditorFragment = new ScreenPlayEditorFragment();
        screenPlayEditorFragment.setArguments(args);
        return screenPlayEditorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenPlayEditorManager = new ScreenPlayEditorManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_screenplay_editor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        slidePicture = (ImageView) view.findViewById(R.id.screenplay_slide_image);
        final EditText slideDesc = (EditText) view.findViewById(R.id.editor_slide_text);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.screenplay_slide_list);
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
        screenPlayEditorManager.addSlide(new Slide(0, getString(R.string.editor_add_button), getString(R.string.editor_add_button), 0));
        slideSelectorRecyclerAdapter.setOnSlideSelectorItemClickListener(new SlideSelectorRecyclerAdapter.OnSlideSelectorItemClickListener() {
            @Override
            public void onSlideSelectorItemClick(RecyclerView.Adapter adapter, View view, int position) {
                addSlideInAdapter(position, slideDesc);
                setSlideContentToEditor(position, slideDesc, slidePicture);
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
        showImage(data, requestCode);
    }

    private void showImage(Intent data, int requestCode) {
        if (data != null) {
            Bundle extras = data.getExtras();
            if (requestCode == REQUEST_CODE_GALLERY) {
                imageGalleryUrl = PictureUtils.getImagePath(getActivity(), data.getData());
                Picasso.with(getActivity())
                        .load(new File(imageGalleryUrl))
                        .into(slidePicture);
            } else {
                if (extras != null) {
                    slidePicture.setImageBitmap((Bitmap) extras.get("data"));
                }
            }
        }
    }

    private void addSlideInAdapter(int position, EditText slideDesc) {
        if (position == INITIAL_POSITION) {
            if (!TextUtils.isEmpty(imageGalleryUrl) && !TextUtils.isEmpty(slideDesc.getText().toString())) {
                Slide slide = screenPlayEditorManager.createSlide(position + 1, imageGalleryUrl, slideDesc.getText().toString(), Slide.IMAGE_TEXT);
                screenPlayEditorManager.addSlide(slide);

            } else if (!TextUtils.isEmpty(imageGalleryUrl) && TextUtils.isEmpty(slideDesc.getText().toString())) {
                Slide slide = screenPlayEditorManager.createSlide(position + 1, imageGalleryUrl, slideDesc.getText().toString(), Slide.ONLY_IMAGE);
                screenPlayEditorManager.addSlide(slide);

            } else if (!TextUtils.isEmpty(slideDesc.getText().toString())) {
                Slide slide = screenPlayEditorManager.createSlide(position + 1, imageGalleryUrl, slideDesc.getText().toString(), Slide.ONLY_TEXT);
                screenPlayEditorManager.addSlide(slide);
            } else {
            }
        }
    }

    private void setSlideContentToEditor(int position, EditText slideDesc, ImageView slideImage) {
        if (position != INITIAL_POSITION) {
            Slide slide = screenPlayEditorManager.getSlide(position);
            if (slide != null) {
                slideDesc.setText(slide.getText());
                if (slide.isResourceImage()) {
                    Picasso.with(getActivity())
                            .load(slide.getResImage())
                            .into(slideImage);
                } else {
                    if (!TextUtils.isEmpty(slide.getUrlImage())) {
                        Picasso.with(getActivity())
                                .load(new File(slide.getUrlImage()))
                                .into(slideImage);
                    }
                }
            }
        }
    }
}
