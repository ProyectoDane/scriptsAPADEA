package com.globant.scriptsapadea.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.ui.adapters.ScriptsSelectorGridAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nicola.quartieri.
 */
public class ScriptsSelectorFragment extends BaseFragment {

    private GridView mGridView;

    private View currentScriptView;

    private ScriptsSelectorGridAdapter mScriptAdapter;

    private List<Script> scritpList = new LinkedList<Script>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scripts_selector, container, false);

        mGridView = (GridView) view.findViewById(R.id.grid_scripts);
        mGridView.setOnItemClickListener(createOnItemClick());

        return view;
    }

    private AdapterView.OnItemClickListener createOnItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long itemId) {
                currentScriptView = view;
                mGridView.setItemChecked(position, true);
                if (mScriptAdapter.getItem(position) == null) {
                    Toast.makeText(getActivity(), "Please select a valid script", Toast.LENGTH_LONG).show();
                } else {
                    // TODO Go to the Selected Script
                    //showProgress();
                    //profileManager.updateAvatar(authenticationManager.getUserSwid(),mScriptAdapter.getItem(position).getId());
                }
            }
        };
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
        // TODO create injectable id or pacient
        scritpList = Script.fetchAllScripts("0");

        if (scritpList != null && !scritpList.isEmpty()) {
            mScriptAdapter = new ScriptsSelectorGridAdapter(getActivity(), scritpList);
            mGridView.setAdapter(mScriptAdapter);
        }

        hideProgress();
    }
}
