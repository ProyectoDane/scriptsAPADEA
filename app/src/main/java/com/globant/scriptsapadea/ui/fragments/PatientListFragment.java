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
                    .translationY(180)
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
        void onNavigateToEditPatient(Patient patient);
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

        Script scriptComedor = new Script(0, "Regar las plantas del comedor", R.drawable.teayudo_usuario, false);
        scriptComedor.getSlides().add(new Slide(0, R.drawable.comerdor_1, "Buscar la regadera en el lavadero.", Slide.IMAGE_TEXT));
        scriptComedor.getSlides().add(new Slide(0, R.drawable.comerdor_2, "Cargarla con agua.", Slide.IMAGE_TEXT));
        scriptComedor.getSlides().add(new Slide(0, R.drawable.comerdor_3, "Ir al comedor", Slide.IMAGE_TEXT));
        scriptComedor.getSlides().add(new Slide(0, R.drawable.comerdor_4, "Poner un poco de agua en cada maceta.", Slide.IMAGE_TEXT));
        scriptComedor.getSlides().add(new Slide(0, R.drawable.comerdor_5, "Ir al lavadero.", Slide.IMAGE_TEXT));
        scriptComedor.getSlides().add(new Slide(0, R.drawable.comerdor_6, "Vaciar la regadera.", Slide.IMAGE_TEXT));
        scriptComedor.getSlides().add(new Slide(0, R.drawable.comerdor_7, "Guardar la regadera.", Slide.IMAGE_TEXT));
        patientApadea.getScriptList().add(scriptComedor);

        Script scriptManos = new Script(0, "Lavarse las manos", R.drawable.teayudo_usuario, false);
        scriptManos.getSlides().add(new Slide(0, R.drawable.manos_1, "Abro la canilla.", Slide.IMAGE_TEXT));
        scriptManos.getSlides().add(new Slide(0, R.drawable.manos_2, "Pongo las manos bajo el chorro de agua.", Slide.IMAGE_TEXT));
        scriptManos.getSlides().add(new Slide(0, R.drawable.manos_3, "Enjabono las dos manos.", Slide.IMAGE_TEXT));
        scriptManos.getSlides().add(new Slide(0, R.drawable.manos_4, "Froto las manos bajo el chorro de agua, cuento hasta 10 en silencio.", Slide.IMAGE_TEXT));
        scriptManos.getSlides().add(new Slide(0, R.drawable.manos_5, "Cierro la canilla.", Slide.IMAGE_TEXT));
        patientApadea.getScriptList().add(scriptManos);

        Script scriptDientes = new Script(0, "Cepillarse los dientes", R.drawable.teayudo_usuario, false);
        scriptDientes.getSlides().add(new Slide(0, R.drawable.dientes_1, "Pongo pasta sobre el cepillo.", Slide.IMAGE_TEXT));
        scriptDientes.getSlides().add(new Slide(0, R.drawable.dientes_2, "Cepillo  los dientes de arriba.", Slide.IMAGE_TEXT));
        scriptDientes.getSlides().add(new Slide(0, R.drawable.dientes_3, "Apoyo cepillo en  lavatorio.", Slide.IMAGE_TEXT));
        scriptDientes.getSlides().add(new Slide(0, R.drawable.dientes_4, "Cargo el vaso con agua.", Slide.IMAGE_TEXT));
        scriptDientes.getSlides().add(new Slide(0, R.drawable.dientes_5, "Foto Enjuago la boca .", Slide.IMAGE_TEXT));
        patientApadea.getScriptList().add(scriptDientes);

        mDBHelper.createPatient(patientApadea);
    }
}
