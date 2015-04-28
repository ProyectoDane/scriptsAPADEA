package com.globant.scriptsapadea.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;

import java.util.List;

/**
 * Created by nicola.quartieri.
 */
public class PatientSelectorGridRecycleAdapter extends RecyclerView.Adapter<PatientSelectorGridRecycleAdapter.PatientViewHolder> {

    private final Context context;

    private static List<Patient> patientList;
    private boolean showLoadingView;

    private int lastPosition = -1;

    private static final int ANIMATED_ITEMS_COUNT = 2;

    private int itemsCount = 0;
    private boolean animateItems = true;
    private static int screenHeight = 0;

    public PatientSelectorGridRecycleAdapter(List<Patient> patientList, Activity context) {
        this.patientList = patientList;
        this.context = context;
    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_card_layout, parent, false);

        return new PatientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PatientViewHolder patientViewHolder, int position) {
        runAnimation(patientViewHolder.itemView, position);

        // TODO
        //patientViewHolder.vImageAvatar.setImageResource((patientList.get(position).getAvatar()));

        patientViewHolder.vNamePatient.setText(patientList.get(position).getName());
        patientViewHolder.vTextLeyend.setText(R.string.default_script_example);
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    private void runAnimation(View viewToAnimate, int position) {
        if (!animateItems || position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastPosition) {
            lastPosition = position;

            viewToAnimate.setTranslationY(getScreenHeight(context));
            viewToAnimate.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    private float getScreenHeight(Context context) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public void showLoadingView() {
        showLoadingView = true;
        notifyItemChanged(0);
    }

    public void updateItems(boolean animated) {
        itemsCount = patientList.size();
        animateItems = animated;
        notifyDataSetChanged();
    }


    public class PatientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView vTextLeyend;
        protected ImageView vImageAvatar;
        protected TextView vNamePatient;
        protected CardView vCardView;

        public PatientViewHolder(View v) {
            super(v);
            vCardView = (CardView) v.findViewById(R.id.card_view);
            vNamePatient =  (TextView) v.findViewById(R.id.txt_patient_name_item);
            vImageAvatar =  (ImageView) v.findViewById(R.id.img_avatar_item);
            vTextLeyend =  (TextView) v.findViewById(R.id.txt_patient_leyend_item);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Click Text, Position:" + getPosition() + " " + patientList.get(getPosition()).getName(), Toast.LENGTH_LONG).show();
        }
    }
}
