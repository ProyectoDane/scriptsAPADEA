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
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.models.Slide;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.globant.scriptsapadea.ui.adapters.PatientSelectorGridRecycleAdapter;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nicolas.quartieri
 */
public class PatientListFragment extends BaseFragment {

    private List<Patient> patientList = new LinkedList<>();

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

        mDBHelper.deleteDataBase();

        //loadFirstExample();

        // Test DB
        /*
        Patient patientJuan = new Patient("0", "Juan", R.drawable.avatar_placeholder);
        mDBHelper = new SQLiteHelper(getActivity());
        mDBHelper.createPatient(patientJuan);
        List<Patient> pacientes =  mDBHelper.getAllPatients();
        if (pacientes.isEmpty()) {
            Log.i("INFO", "Lista vacia");
        } else {
            Log.i("INFO", "Lista llena");

            int size = pacientes.size();
            for (int i = 0 ; i < size ; i++) {
                Patient paciente = pacientes.get(i);
                Log.i("INFO", paciente.getId() + " " + paciente.getName() + " " + paciente.getAvatar());
            }
        }
        */

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
        //patientList = Patient.fetchAllPatients(getActivity().getContentResolver(), null);
        patientList = mDBHelper.getAllPatients();

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
        void deletePatient(Patient patient);
    }

    private void loadFirstExample() {
        List<Patient> listScript = new LinkedList<Patient>();
/*
        Patient patientJuan = new Patient(0, "Juan", getRealPathFromResId(getActivity(), R.drawable.avatar_placeholder));
        Script scriptJuan = new Script(0, "Lavar los platos", getRealPathFromResId(getActivity(), R.drawable.ic_launcher));
        scriptJuan.getSlides().add(new Slide(0, "Primero....", getRealPathFromResId(getActivity(), R.drawable.cepillo)));
        scriptJuan.getSlides().add(new Slide(1, "Segundo....", getRealPathFromResId(getActivity(), R.drawable.cepillo)));
        scriptJuan.getSlides().add(new Slide(2, "Tercero....", getRealPathFromResId(getActivity(), R.drawable.cepillo)));
*/
        Patient patientApadea = new Patient(0, "APADEA", R.drawable.teayudo_usuario);

        Script script = new Script(0, "Lavar los platos", R.drawable.apadea_dientes);
        script.getSlides().add(new Slide(0, "Primero....", R.drawable.cepillo));
        script.getSlides().add(new Slide(1, "Segundo....", R.drawable.cepillo));
        script.getSlides().add(new Slide(2, "Tercero....", R.drawable.cepillo));
        patientApadea.getScriptList().add(script);

        Script scriptPepe = new Script(0, "Lavar los platos", R.drawable.apadea_dientes);
        scriptPepe.getSlides().add(new Slide(0, "Primero....", R.drawable.cepillo));
        scriptPepe.getSlides().add(new Slide(1, "Segundo....", R.drawable.cepillo));
        scriptPepe.getSlides().add(new Slide(2, "Tercero....", R.drawable.cepillo));
        patientApadea.getScriptList().add(scriptPepe);

        mDBHelper.createPatient(patientApadea);
    }
}
