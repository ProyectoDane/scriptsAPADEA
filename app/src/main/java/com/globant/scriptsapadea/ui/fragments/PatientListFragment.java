package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
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
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.models.Slide;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.globant.scriptsapadea.ui.adapters.PatientSelectorGridRecycleAdapter;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * This class provides the patient list to be shown.
 *
 * @author nicolas.quartieri
 */
public class PatientListFragment extends BaseFragment {

    private List<Patient> patientList = new LinkedList<>();

    private View welcomePanel;
    private RecyclerView mPatientView;
    private PatientSelectorGridRecycleAdapter adapter;

    private PatientListFragmentListener mListener;

    @Inject
    private SQLiteHelper mDBHelper;

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

        //mDBHelper.deleteDataBase();

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
        LinearLayoutManager patientLayoutManager = new LinearLayoutManager(getActivity());
        mPatientView.setLayoutManager(patientLayoutManager);

        adapter = new PatientSelectorGridRecycleAdapter(patientList, this);
        mPatientView.setAdapter(adapter);

        if (savedInstanceState != null) {
            adapter.updateItems(false);
        }

        welcomePanel = view.findViewById(R.id.fragment_welcome);

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
        //patientList = Patient.fetchAllPatients(getActivity().getContentResolver(), null);
        patientList = mDBHelper.getAllPatients();

        if (patientList.size() == 0) {
            loadFirstExample();
            patientList = mDBHelper.getAllPatients();
        }

        // TODO this must became from a XML animation file
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (patientList.size() <= 1) {
            welcomePanel.setVisibility(View.VISIBLE);
            welcomePanel.setTranslationY(size.y);
            welcomePanel.animate().setStartDelay(500)
                    .translationY(200)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(1000)
                    .start();
        } else {
            welcomePanel.setVisibility(View.GONE);
        }

        if (patientList != null && !patientList.isEmpty()) {
            adapter = new PatientSelectorGridRecycleAdapter(patientList, this);
            mPatientView.setAdapter(adapter);
        }

        hideProgress();
    }

    public interface PatientListFragmentListener {
        void onNavigateToCreateNewPatient();
        void onNavigateToPatient(Patient patient);
        void deletePatient(Patient patient);
    }

    private void loadFirstExample() {
        List<Patient> listScript = new LinkedList<>();
/*
        Patient patientJuan = new Patient(0, "Juan", getRealPathFromResId(getActivity(), R.drawable.avatar_placeholder));
        Script scriptJuan = new Script(0, "Lavar los platos", getRealPathFromResId(getActivity(), R.drawable.ic_launcher));
        scriptJuan.getSlides().add(new Slide(0, "Primero....", getRealPathFromResId(getActivity(), R.drawable.cepillo)));
        scriptJuan.getSlides().add(new Slide(1, "Segundo....", getRealPathFromResId(getActivity(), R.drawable.cepillo)));
        scriptJuan.getSlides().add(new Slide(2, "Tercero....", getRealPathFromResId(getActivity(), R.drawable.cepillo)));
*/
        Patient patientApadea = new Patient(0, getString(R.string.app_owner_name), R.drawable.teayudo_usuario, false);

        Script script = new Script(0, "Lavar los platos", R.drawable.apadea_dientes, false);
        script.getSlides().add(new Slide(0, R.drawable.cepillo, "Primero....", Slide.IMAGE_TEXT));
        script.getSlides().add(new Slide(0, R.drawable.cepillo, "Segundo....", Slide.IMAGE_TEXT));
        script.getSlides().add(new Slide(0, R.drawable.cepillo, "Tercero....", Slide.IMAGE_TEXT));
        patientApadea.getScriptList().add(script);

        Script scriptPepe = new Script(0, "Lavar los dientes", R.drawable.apadea_dientes, false);
        scriptPepe.getSlides().add(new Slide(0, R.drawable.cepillo, "Primero....", Slide.IMAGE_TEXT));
        scriptPepe.getSlides().add(new Slide(0, R.drawable.cepillo, "Segundo....", Slide.IMAGE_TEXT));
        scriptPepe.getSlides().add(new Slide(0, R.drawable.cepillo, "Tercero....", Slide.IMAGE_TEXT));
        patientApadea.getScriptList().add(scriptPepe);

        mDBHelper.createPatient(patientApadea);
    }
}
