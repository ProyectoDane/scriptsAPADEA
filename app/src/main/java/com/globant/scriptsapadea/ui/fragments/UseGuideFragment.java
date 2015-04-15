package com.globant.scriptsapadea.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;

import roboguice.inject.InjectView;

/**
 * Created by nicola.quartieri
 */
public class UseGuideFragment extends BaseFragment {

    @InjectView(R.id.btnNextTitle)
    private Button mNextButtonTitle;
    @InjectView(R.id.btnNextPrueba)
    private Button mNextButtonPrueba;
    @InjectView(R.id.btn_entendido)
    private Button mNextEntendido;

    @InjectView(R.id.txtTitle)
    private TextView txtTitle;
    @InjectView(R.id.txtLeyenda)
    private TextView txtLeyenda;

    @InjectView(R.id.imageView)
    private ImageView imageView;
    @InjectView(R.id.txtScriptsPrueba)
    private TextView txtScriptsPrueba;

    @InjectView(R.id.img_crear_script)
    private ImageView imgCrearScript;
    @InjectView(R.id.txt_nuevo_script)
    private TextView txtNuevoScript;

    private UseGuideFragment.Listener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (Listener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_use_guide_layout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNextButtonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNextButtonTitle.setVisibility(View.INVISIBLE);
                txtTitle.setVisibility(View.INVISIBLE);
                txtLeyenda.setVisibility(View.INVISIBLE);

                imageView.setVisibility(View.VISIBLE);
                txtScriptsPrueba.setVisibility(View.VISIBLE);
                mNextButtonPrueba.setVisibility(View.VISIBLE);
            }
        });

        mNextButtonPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.INVISIBLE);
                txtScriptsPrueba.setVisibility(View.INVISIBLE);
                mNextButtonPrueba.setVisibility(View.INVISIBLE);

                imgCrearScript.setVisibility(View.VISIBLE);
                txtNuevoScript.setVisibility(View.VISIBLE);
                mNextEntendido.setVisibility(View.VISIBLE);
            }
        });

        mNextEntendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.navigateToScriptListView();
            }
        });
    }

    public interface Listener {
        public void navigateToScriptListView();
    }
}
