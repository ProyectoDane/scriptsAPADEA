package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.ui.adapters.PatientSelectorGridRecycleAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nicolas.quartieri
 */
public class PatientListFragment extends BaseFragment {

    private List<Patient> patientList = new LinkedList<>();

    private RecyclerView mPatientView;
    private PatientSelectorGridRecycleAdapter adapter;

    private PatientListFragmentListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (PatientListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PatientListFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal_view, container, false);

        setHasOptionsMenu(true);

        View cardCreatePatient = view.findViewById(R.id.crd_create_patient);
        cardCreatePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNavigateToCreateNewPatient();
            }
        });

        mPatientView = (RecyclerView) view.findViewById(R.id.grid_patient);
        mPatientView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mPatientView.setLayoutManager(llm);

        adapter = new PatientSelectorGridRecycleAdapter(patientList, getActivity());
        mPatientView.setAdapter(adapter);

        if (savedInstanceState != null) {
            adapter.updateItems(false);
        }

        // TODO this must became from a XML animation file
        View welcomePanel = view.findViewById(R.id.fragment_welcome);
        welcomePanel.setVisibility(View.VISIBLE);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        welcomePanel.setTranslationY(size.y);
        welcomePanel.animate().setStartDelay(500)
                .translationY(200)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(1000)
                .start();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePrincipalView();
    }

    private void startAnimation() {
        adapter.updateItems(true);
    }

    private void updatePrincipalView() {
        // TODO
        //showProgress();

        patientList.clear();
        // TODO create injectable id or pacient
        patientList = Patient.fetchAllPatients(null);

        if (patientList != null && !patientList.isEmpty()) {
            adapter = new PatientSelectorGridRecycleAdapter(patientList, getActivity());
            mPatientView.setAdapter(adapter);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPatientView.smoothScrollToPosition(0);
                adapter.showLoadingView();
            }
        }, 500);

        hideProgress();
    }

    public interface PatientListFragmentListener {
        void onNavigateToCreateNewPatient();
        void onNavigateToPatient(Patient patient);
    }
}
