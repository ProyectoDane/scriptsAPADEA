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
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.ui.adapters.ScriptsSelectorGridRecycleAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nicola.quartieri
 */
public class ScriptsSelectorFragment extends BaseFragment {

    private List<Script> scritpList = new LinkedList<Script>();

    private RecyclerView mGridView;
    private ScriptsSelectorGridRecycleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scripts_selector, container, false);

        mGridView = (RecyclerView) view.findViewById(R.id.grid_scripts);
        mGridView.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 3);
        mGridView.setLayoutManager(glm);

        adapter = new ScriptsSelectorGridRecycleAdapter(scritpList, getActivity());
        mGridView.setAdapter(adapter);

        mGridView = (RecyclerView) view.findViewById(R.id.grid_scripts);
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

        scritpList.clear();
        // TODO create injectable id of pacient
        scritpList = Script.fetchAllScripts("0");

        if (scritpList != null && !scritpList.isEmpty()) {
            adapter = new ScriptsSelectorGridRecycleAdapter(scritpList, getActivity());
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
