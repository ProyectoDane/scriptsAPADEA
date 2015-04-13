package com.globant.scriptsapadea.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import roboguice.fragment.RoboFragment;

/**
 * Base Fragment for profile. Registers the fragment on the event bus.
 */
public abstract class BaseFragment extends RoboFragment {

    @Inject
    private Bus bus;

    private ProgressDialog progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
        progressBar = new ProgressDialog(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
        progressBar.dismiss();
    }

    protected void showProgress() {
        progressBar.setIndeterminate(true);
        progressBar.show();
    }

    protected void hideProgress() {
        progressBar.hide();
    }
}
