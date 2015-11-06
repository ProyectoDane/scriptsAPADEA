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
import com.globant.scriptsapadea.manager.PatientManager;
import com.globant.scriptsapadea.manager.ScreenPlayEditorManager;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.models.Slide;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.globant.scriptsapadea.ui.adapters.SlideSelectorRecyclerAdapter;
import com.globant.scriptsapadea.utils.PictureUtils;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

/**
 * @author leonel.mendez
 */
public class ScreenPlayEditorFragment extends BaseFragment {

    private static final int REQUEST_CODE_GALLERY = 0x100;
    private static final int REQUEST_CODE_CAMERA = 0x010;
    private static final int INITIAL_POSITION = 0;
    private static final String SCRIPT = "script";

    @Inject
    private PatientManager patientManager;

    @Inject
    private SQLiteHelper mDBHelper;

    private ScreenPlayEditorManager screenPlayEditorManager;
    private ImageView slidePicture;
    private String imageGalleryUrl;


    public static ScreenPlayEditorFragment newInstance(Bundle args) {
        ScreenPlayEditorFragment screenPlayEditorFragment = new ScreenPlayEditorFragment();
        screenPlayEditorFragment.setArguments(args);
        return screenPlayEditorFragment;
    }

    public static ScreenPlayEditorFragment newInstance(Bundle args, Script script) {
        ScreenPlayEditorFragment screenPlayEditorFragment = new ScreenPlayEditorFragment();
        args.putSerializable(SCRIPT, script);
        screenPlayEditorFragment.setArguments(args);
        screenPlayEditorFragment.setArguments(args);
        return screenPlayEditorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenPlayEditorManager = new ScreenPlayEditorManager(getActivity(), patientManager, mDBHelper);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_screenplay_editor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        slidePicture = (ImageView) view.findViewById(R.id.screenplay_slide_image);
        final EditText slideDescription = (EditText) view.findViewById(R.id.editor_slide_text);
        RecyclerView slidesListView = (RecyclerView) view.findViewById(R.id.screenplay_slide_list);
        slidesListView.setHasFixedSize(true);
        slidesListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SlideSelectorRecyclerAdapter slideSelectorRecyclerAdapter = new SlideSelectorRecyclerAdapter(screenPlayEditorManager);
        slidesListView.setAdapter(slideSelectorRecyclerAdapter);
        ImageView imgAddImage = (ImageView) view.findViewById(R.id.slide_only_image);
        imgAddImage.setImageResource(R.drawable.agregar_foto);
        imgAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSlideInAdapter(slideDescription, true);
                slidePicture.setImageResource(android.R.color.transparent);
                slideDescription.setText("");

            }
        });

        view.findViewById(R.id.erase_gallery).setOnClickListener(new View.OnClickListener() {
            /**
             * Remove the selected slide previously setup in the main slide imageView.
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Slide slide = patientManager.getSelectedSlide();
                screenPlayEditorManager.deleteSlide(slide);
                int value = screenPlayEditorManager.removeSlide(slide);

                // TODO remove image and text from main selection frame
                slidePicture.setImageResource(android.R.color.transparent);
                slideDescription.setText("");
            }
        });

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

        // TODO si viene de haber seleccionado la edición

        List<Script> scriptList = patientManager.getSelectedPatient().getScriptList();
        if (!scriptList.isEmpty() && scriptList.size() == 1) {
            mDBHelper.createPatient(patientManager.getSelectedPatient());
            patientManager.setSelectedScript(scriptList.get(0));
        }

        slideSelectorRecyclerAdapter.setOnSlideSelectorItemClickListener(new SlideSelectorRecyclerAdapter.OnSlideSelectorItemClickListener() {
            /**
             * Set the selected slide to the main slide imageView & text.
             *
             * @param adapter
             * @param view
             * @param position
             */
            @Override
            public void onSlideSelectorItemClick(RecyclerView.Adapter adapter, View view, int position) {
                Slide slide = screenPlayEditorManager.getSlide(position);
                patientManager.setSelectedSlide(slide);

                if (slide.isResourceImage()) {
                    Picasso.with(getActivity())
                            .load(slide.getResImage())
                            .into(slidePicture);
                } else {
                    Picasso.with(getActivity())
                            .load(new File(slide.getUrlImage()))
                            .into(slidePicture);
                }

                slideDescription.setText(slide.getText());
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

    private void addSlideInAdapter(EditText slideDescription, boolean save) {
        boolean slideAdded;
        Slide slide = null;

        if (!TextUtils.isEmpty(imageGalleryUrl) && !TextUtils.isEmpty(slideDescription.getText().toString())) {
            slide = screenPlayEditorManager.createSlide(0, imageGalleryUrl, slideDescription.getText().toString(), Slide.IMAGE_TEXT);
            screenPlayEditorManager.addSlide(slide);

            slideAdded = true;
        } else if (!TextUtils.isEmpty(imageGalleryUrl) && TextUtils.isEmpty(slideDescription.getText().toString())) {
            slide = screenPlayEditorManager.createSlide(0, imageGalleryUrl, slideDescription.getText().toString(), Slide.ONLY_IMAGE);
            screenPlayEditorManager.addSlide(slide);

            slideAdded = true;
        } else if (!TextUtils.isEmpty(slideDescription.getText().toString())) {
            slide = screenPlayEditorManager.createSlide(0, imageGalleryUrl, slideDescription.getText().toString(), Slide.ONLY_TEXT);
            screenPlayEditorManager.addSlide(slide);

            slideAdded = true;
        } else {
            slideAdded = false;
        }

        if (save && slideAdded && slide != null) {
            screenPlayEditorManager.saveSlide(slide);
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
