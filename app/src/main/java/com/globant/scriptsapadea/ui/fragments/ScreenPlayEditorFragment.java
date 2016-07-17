package com.globant.scriptsapadea.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.manager.ActivityResultEvent;
import com.globant.scriptsapadea.manager.PatientManager;
import com.globant.scriptsapadea.manager.ScreenPlayEditorManager;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.models.Slide;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.globant.scriptsapadea.ui.adapters.SlideSelectorRecyclerAdapter;
import com.globant.scriptsapadea.utils.PictureUtils;
import com.globant.scriptsapadea.utils.TEAlertDialog;
import com.globant.scriptsapadea.widget.CropCircleTransformation;
import com.software.shell.fab.ActionButton;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

/**
 * This class provides the screen to edit every one of the Slide's contained in the Script.
 *
 * @author leonel.mendez
 */
public class ScreenPlayEditorFragment extends BaseFragment {

    private static final int REQUEST_CODE_GALLERY = 0x100;
    private static final int REQUEST_CODE_CAMERA = 0x010;
    private static final int INITIAL_POSITION = 0;
    private static final String EDIT_MODE = "editmode";
    private boolean isEditMode = false;

    @Inject
    private PatientManager patientManager;

    @Inject
    private SQLiteHelper mDBHelper;

    private ScreenPlayEditorManager screenPlayEditorManager;
    private ImageView slidePicture;
    private String imageGalleryUrl;
    private List<Slide> listSlides;
    private File photoFile;
    private SlideSelectorRecyclerAdapter slideSelectorRecyclerAdapter;
    private EditText slideDescription;

    private CreateScriptFragment.OnTakeScriptPictureFragmentListener onTakeScriptPictureFragmentListener;

    public static ScreenPlayEditorFragment newInstance(Bundle args, boolean isEditMode) {
        ScreenPlayEditorFragment screenPlayEditorFragment = new ScreenPlayEditorFragment();
        args.putSerializable(EDIT_MODE, isEditMode);
        screenPlayEditorFragment.setArguments(args);
        return screenPlayEditorFragment;
    }

    public static ScreenPlayEditorFragment newInstance(Bundle args, Script script, boolean isEditMode) {
        ScreenPlayEditorFragment screenPlayEditorFragment = new ScreenPlayEditorFragment();
        args.putBoolean(EDIT_MODE, isEditMode);
        args.putSerializable(Script.SCRIPT, script);
        screenPlayEditorFragment.setArguments(args);
        return screenPlayEditorFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onTakeScriptPictureFragmentListener = (CreateScriptFragment.OnTakeScriptPictureFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + " must be implements OnTakeScriptPictureFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isEditMode = getArguments().getBoolean(EDIT_MODE);

        Script script = (Script) getArguments().getSerializable(Script.SCRIPT);
        if (script != null) {
            patientManager.setSelectedScript(script);
            listSlides = script.getSlides();
        }

        screenPlayEditorManager = new ScreenPlayEditorManager(getActivity(), patientManager, mDBHelper, listSlides);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_screenplay_editor, container, false);
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        //view.findViewById(R.id.img_pulldown_button).setVisibility(View.GONE);

        slidePicture = (ImageView) rootView.findViewById(R.id.screenplay_slide_image);
        slideDescription = (EditText) rootView.findViewById(R.id.editor_slide_text);
        RecyclerView slidesListView = (RecyclerView) rootView.findViewById(R.id.screenplay_slide_list);
        slidesListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        if (listSlides != null && !listSlides.isEmpty()) {
            slideSelectorRecyclerAdapter = new SlideSelectorRecyclerAdapter(screenPlayEditorManager, listSlides);
        } else {
            slideSelectorRecyclerAdapter = new SlideSelectorRecyclerAdapter(screenPlayEditorManager);
        }
        slidesListView.setAdapter(slideSelectorRecyclerAdapter);

        View imaEditProfile = rootView.findViewById(R.id.img_profile_edit);
        imaEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTakeScriptPictureFragmentListener.onEditScriptProfile(CreateScriptFragment.newInstance(patientManager.getSelectedScript()));
            }
        });

        ActionButton fabNext = (ActionButton) rootView.findViewById(R.id.fab_add);
        fabNext.setImageResource(R.drawable.crox_icon );
        fabNext.setShadowResponsiveEffectEnabled(true);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSlide(slideDescription);
            }
        });

        View buttonSave = rootView.findViewById(R.id.btn_save_script);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placebo - Save is done in every action taken.
                // TODO Refactoring (use Timer and TimeTask)
                final Handler handler  = new Handler();
                final AlertDialog alert = new AlertDialog.Builder(getActivity()).setTitle("GUARDADO!")
                        .setIcon(R.drawable.teayudo_usuario)
                        .show();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (alert.isShowing()) {
                            alert.dismiss();
                        }
                    }
                };
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        handler.removeCallbacks(runnable);
                    }
                });

                handler.postDelayed(runnable, 2000);

                //saveSlide(slideDescription);
            }
        });

        Script selectedScript = patientManager.getSelectedScript();
        TextView txtScriptName = (TextView) rootView.findViewById(R.id.txt_script_name);
        txtScriptName.setText(selectedScript.getName());

        ImageView imgScript = (ImageView) rootView.findViewById(R.id.img_script);
        if (selectedScript.isResourceImage()) {
            Picasso.with(getActivity()).load(selectedScript.getResImage()).error(R.drawable.ic_launcher)
                    .transform(new CropCircleTransformation())
                    .into(imgScript);
        } else {
            Picasso.with(getActivity()).load(new File(selectedScript.getImageScripts())).error(R.drawable.ic_launcher)
                    .transform(new CropCircleTransformation())
                    .into(imgScript);
        }

        rootView.findViewById(R.id.erase_gallery).setOnClickListener(new View.OnClickListener() {
            /**
             * Remove the selected slide previously setup in the main slide imageView.
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                cleanSlideSelector();
            }
        });

        rootView.findViewById(R.id.editor_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtils.pickPhotoFromGallery(ScreenPlayEditorFragment.this, REQUEST_CODE_GALLERY);
            }
        });

        rootView.findViewById(R.id.editor_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoFile = PictureUtils.createFilePhotoForCamera();
                PictureUtils.takePhotoFromCamera(ScreenPlayEditorFragment.this, REQUEST_CODE_CAMERA, photoFile);
            }
        });

        List<Script> scriptList = patientManager.getSelectedPatient().getScriptList();
        if (!scriptList.isEmpty()) {
            if (scriptList.size() == 1 && !isEditMode) {
                mDBHelper.createPatient(patientManager.getSelectedPatient());
                patientManager.setSelectedScript(scriptList.get(0));
            } else if (scriptList.size() > 1 && !isEditMode) { // TODO Refactor this!
                mDBHelper.createScript(patientManager.getSelectedScript(),
                        patientManager.getSelectedPatient().getId());
            }
        }

        slideSelectorRecyclerAdapter.setOnSlideSelectorItemClickListener(new SlideSelectorRecyclerAdapter.OnSlideSelectorItemClickListener() {
            /**
             * Set the selected slide to the main slide imageView & text.
             *
             * @param view
             * @param position
             */
            @Override
            public void onSlideSelectorItemClick(View view, int position) {
                Slide slide = screenPlayEditorManager.getSlide(position);
                patientManager.setSelectedSlide(slide);

                if (slide.isResourceImage()) {
                    Picasso.with(getActivity())
                            .load(slide.getResImage())
                            .into(slidePicture);
                } else if (slide.isUrlImage()) {
                    Picasso.with(getActivity())
                            .load(new File(slide.getUrlImage()))
                            .into(slidePicture);
                }

                slideDescription.setText(slide.getText().toUpperCase());
            }

            /**
             * Erase the selected Slide with the Trash icon.
             */
            @Override
            public void eraseSlideSelectorItemClick(int position) {
                Slide slide = screenPlayEditorManager.getSlide(position);
                ScreenPlayEditorFragment.this.eraseSlide(slide);
            }
        });
    }

    /**
     * Clean the Image Selector & the Text Description display below.
     */
    private void cleanSlideSelector() {
        slidePicture.setImageResource(android.R.color.transparent);
        slideDescription.setText("");
    }

    /**
     * Remove the {@link Slide} from the {@link ScreenPlayEditorFragment}
     *
     * @param slide the {@link Slide} to be remove.
     */
    public void eraseSlide(Slide slide) {
        screenPlayEditorManager.deleteSlide(slide);
        // TODO check this value.
        int value = screenPlayEditorManager.removeSlide(slide);
    }

    /**
     * Add the {@link Slide} into the {@link ScreenPlayEditorFragment}
     *
     * @param slideDescription the {@link EditText} to be reset.
     */
    private void saveSlide(EditText slideDescription) {
        addSlideInAdapter(slideDescription, true);

        slidePicture.setImageResource(android.R.color.transparent);
        slideDescription.setText("");
    }

    @Subscribe
    public void onActivityResultReceived(ActivityResultEvent event) {
        onActivityResult(event.getRequestCode(), event.getResultCode(), event.getData());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showImage(data, requestCode);

        // Update the image for the selected slide.
        Slide selectedSlide = patientManager.getSelectedSlide();
        if (selectedSlide != null) {
            selectedSlide.updateImage(imageGalleryUrl);
            screenPlayEditorManager.updateSlide(selectedSlide);
            slideSelectorRecyclerAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Take the selected image an display in the selected slide.
     *
     * @param data
     * @param requestCode
     */
    private void showImage(Intent data, int requestCode) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (data != null && data.getData() != null) {
                imageGalleryUrl = PictureUtils.getImagePath(getActivity(), data.getData(), true);
                if (imageGalleryUrl != null) {
                    Picasso.with(getActivity())
                            .load(new File(imageGalleryUrl))
                            .into(slidePicture);
                } else {
                    TEAlertDialog alert = new TEAlertDialog(getContext());
                    alert.setTitle(R.string.error_image).show();
                }
            }
        } else {
            if (photoFile != null && photoFile.exists()) {
                Uri uri = Uri.fromFile(photoFile);
                slidePicture.setImageURI(uri);

                imageGalleryUrl = photoFile.getAbsolutePath();
            }
        }
    }

    /**
     * The new {@link Slide} to be added.
     *
     * @param slideDescription {@link EditText} the description user written.
     * @param save             true if we must save the slide to the {@link ScreenPlayEditorManager}
     */
    private void addSlideInAdapter(EditText slideDescription, boolean save) {
        boolean slideAdded = false;
        Slide slide = null;

        if (!TextUtils.isEmpty(imageGalleryUrl) && !TextUtils.isEmpty(slideDescription.getText().toString())) {
            slide = screenPlayEditorManager.createSlide(0, imageGalleryUrl, slideDescription.getText().toString().toUpperCase(), Slide.IMAGE_TEXT);
            screenPlayEditorManager.addSlide(slide);

            slideAdded = true;
        } else if (!TextUtils.isEmpty(imageGalleryUrl) && TextUtils.isEmpty(slideDescription.getText().toString())) {
            slide = screenPlayEditorManager.createSlide(0, imageGalleryUrl, slideDescription.getText().toString(), Slide.ONLY_IMAGE);
            screenPlayEditorManager.addSlide(slide);

            slideAdded = true;
        } else if (!TextUtils.isEmpty(slideDescription.getText().toString())) {
            slide = screenPlayEditorManager.createSlide(0, imageGalleryUrl, slideDescription.getText().toString().toUpperCase(), Slide.ONLY_TEXT);
            screenPlayEditorManager.addSlide(slide);

            slideAdded = true;
        } else {
            TEAlertDialog alert = new TEAlertDialog(getContext());
            alert.setTitle(R.string.error_empty_image).show();
        }

        if (save && slideAdded && slide != null) {
            screenPlayEditorManager.saveSlide(slide);
        }

        imageGalleryUrl = new String();
    }

    /**
     * In place the slide to the center ready to edit.
     *
     * @param position   Where is in place the slide.
     * @param slideDesc  the {@link EditText} ready to set new text.
     * @param slideImage the {@link ImageView} ready to load the image.
     */
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Change the image & text from every slide requires clean up.
        patientManager.setSelectedSlide(null);
    }
}
