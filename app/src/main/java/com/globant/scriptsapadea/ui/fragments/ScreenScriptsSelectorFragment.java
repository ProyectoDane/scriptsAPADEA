package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.ui.activities.AboutActivity;
import com.globant.scriptsapadea.ui.adapters.ScriptsSelectorGridRecycleAdapter;
import com.globant.scriptsapadea.widget.CropCircleTransformation;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment created to hold script image gallery (an context-menu) inside the script grid.
 *
 * @author nicolas.quartieri.
 */
public class ScreenScriptsSelectorFragment extends BaseFragment {

    private List<Script> scriptList = new ArrayList<>();
    private RecyclerView mGridView;
    private ScriptsSelectorGridRecycleAdapter adapter;
    private Patient patient;
    private ScreenScriptSelectorListener mListener;
    private CreateScriptFragment.OnTakeScriptPictureFragmentListener listener;
    private AboutActivity.AboutListener listenerAboutScreen;

    public static ScreenScriptsSelectorFragment newInstance(Patient patient) {
        ScreenScriptsSelectorFragment fragment = new ScreenScriptsSelectorFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Patient.class.getName(), patient);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (ScreenScriptSelectorListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ScreenScriptSelectorListener");
        }

        try {
            listener = (CreateScriptFragment.OnTakeScriptPictureFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ScreenScriptSelectorListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patient = (Patient)getArguments().getSerializable(Patient.class.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scripts_selector, container, false);

        View btnNewScript = view.findViewById(R.id.btn_new_script);
        btnNewScript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                listener.onTakeScriptPictureFragment(CreateScriptFragment.newInstance(args));
            }
        });

        TextView txtPatientName = (TextView) view.findViewById(R.id.txt_patient_name);
        txtPatientName.setText(patient.getName());

        ImageView imageProfile = (ImageView) view.findViewById(R.id.img_profile);
        if (patient.isResourceAvatar()) {
            Picasso.with(getActivity()).load(patient.getResAvatar()).error(R.drawable.avatar_placeholder)
                    .transform(new CropCircleTransformation())
                    .into(imageProfile);
        } else {
            Picasso.with(getActivity()).load(new File(patient.getAvatar())).error(R.drawable.avatar_placeholder)
                    .transform(new CropCircleTransformation())
                    .into(imageProfile);
        }

        if (patient.getName().equalsIgnoreCase(getString(R.string.app_owner_name))) {
            btnNewScript.setVisibility(View.INVISIBLE);

            View btnAbout = view.findViewById(R.id.btn_about);
            btnAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listenerAboutScreen.onTakeToAboutScreen();
                }
            });
            btnAbout.setVisibility(View.VISIBLE);
        }

        mGridView = (RecyclerView) view.findViewById(R.id.grid_scripts);
        mGridView.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 3);
        mGridView.setLayoutManager(glm);

        adapter = new ScriptsSelectorGridRecycleAdapter(scriptList, getActivity());
        mGridView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateScriptsView();
    }

    private void updateScriptsView() {
        showProgress();

        // TODO create injectable id or pacient
        scriptList = patient.getScriptList();

        if (scriptList != null && !scriptList.isEmpty()) {
            adapter = new ScriptsSelectorGridRecycleAdapter(scriptList, getActivity());
            mGridView.setAdapter(adapter);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mGridView.smoothScrollToPosition(0);
                adapter.showLoadingView();
            }
        }, 500);

        hideProgress();
    }

    public interface ScreenScriptSelectorListener {
        void onNavigateToScriptSlider(Script script);
        void onNavigateToSlideEditor(ScreenPlayEditorFragment fragment);
    }
}
