package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.globant.scriptsapadea.ui.adapters.ScriptCopyRecycleAdapter;
import com.globant.scriptsapadea.ui.listener.CommunicateListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author nicolas.quartieri
 */
public class ScriptCopyFragment extends BaseDialogFragment {

    private List<Patient> patientList = new LinkedList<>();
    private ScriptCopyRecycleAdapter adapter;

    @Inject
    private SQLiteHelper mDBHelper;
    private Script script;
    private Patient patient;
    private CommunicateListener mListener;

    public static ScriptCopyFragment newInstance(Bundle args) {
        ScriptCopyFragment fragment = new ScriptCopyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (CommunicateListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ScreenScriptSelectorListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            script = (Script) getArguments().getSerializable(Script.SCRIPT);
            patient = (Patient) getArguments().getSerializable(Patient.PATIENT);
        }

        patientList = mDBHelper.getAllPatients();
        if (patientList.size() > 0) // Remove APADEA not want to copy in here.
            patientList.remove(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.script_copy_layout, container, false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtScriptName = (TextView) view.findViewById(R.id.txt_script_name);
        ImageView imgAvatarScript = (ImageView) view.findViewById(R.id.img_avatar_script);

        txtScriptName.setText(script.getName());
        if (script.isResourceImage()) {
            Picasso.with(getActivity()).load(script.getResImage()).error(R.drawable.teayudo_usuario)
                    .into(imgAvatarScript);
        } else {
            Picasso.with(getActivity()).load(new File(script.getImageScripts())).error(R.drawable.teayudo_usuario)
                    .into(imgAvatarScript);
        }

        View btnCopy = view.findViewById(R.id.btn_copy);
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = adapter.getItemCount();
                for (int i = 0; i < total; i++) {
                    if (adapter.isChecked(i)) {
                        mDBHelper.createScript(script, adapter.getParentId(i));
                    }
                }
                mListener.doAction();
                dismiss();
            }
        });

        RecyclerView lstScriptsView = (RecyclerView) view.findViewById(R.id.lst_scripts);
        lstScriptsView.setHasFixedSize(true);
        LinearLayoutManager scriptLayoutManager = new LinearLayoutManager(getActivity());
        lstScriptsView.setLayoutManager(scriptLayoutManager);
        adapter = new ScriptCopyRecycleAdapter(patientList, this);
        lstScriptsView.setAdapter(adapter);

        return view;
    }

    public interface ScriptCopyListener {
        void onTakeToScriptCopyScreen();
    }
}
