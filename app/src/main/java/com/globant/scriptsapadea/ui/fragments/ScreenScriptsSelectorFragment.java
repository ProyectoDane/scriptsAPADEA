package com.globant.scriptsapadea.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.ui.adapters.ScriptsSelectorGridRecycleAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nicolas.quartieri.
 */
public class ScreenScriptsSelectorFragment extends BaseFragment {

    private List<Script> scriptList = new LinkedList<Script>();

    private RecyclerView mGridView;
    private ScriptsSelectorGridRecycleAdapter adapter;
    private Patient patient;

    public static ScreenScriptsSelectorFragment newInstance(Patient patient) {
        ScreenScriptsSelectorFragment fragment = new ScreenScriptsSelectorFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Patient.class.getName(), patient);
        fragment.setArguments(bundle);

        return fragment;
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

    // TODO
    private void updateScriptsView() {
        showProgress();

        scriptList.clear();
        // TODO create injectable id or pacient
        scriptList = Script.fetchAllScripts("0");

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
}
