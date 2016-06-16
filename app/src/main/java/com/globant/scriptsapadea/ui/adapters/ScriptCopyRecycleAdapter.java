package com.globant.scriptsapadea.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.ui.fragments.ScriptCopyFragment;
import com.globant.scriptsapadea.widget.CropCircleTransformation;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * This class is used to control the data flow of the Copy Script Screen.
 *
 * @author nicolas.quartieri
 */
public class ScriptCopyRecycleAdapter extends RecyclerView.Adapter<ScriptCopyRecycleAdapter.PatientSimpleViewHolder> {

    private static List<Patient> patientList;
    private final ScriptCopyFragment context;
    private PatientSimpleViewHolder[] checkedList;

    public ScriptCopyRecycleAdapter(List<Patient> patientList, ScriptCopyFragment context) {
        this.patientList = patientList;
        this.context = context;
        checkedList = new PatientSimpleViewHolder[patientList.size()];
    }

    @Override
    public PatientSimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.script_copy_item_layout, parent, false);
        return new PatientSimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PatientSimpleViewHolder patientViewHolder, int position) {
        Patient patient = patientList.get(position);
        if (patient.isResourceAvatar()) {
            Picasso.with(context.getActivity()).load(patient.getResAvatar()).error(R.drawable.teayudo_usuario)
                    .transform(new CropCircleTransformation())
                    .into(patientViewHolder.vImageAvatar);
        } else {
            Picasso.with(context.getActivity()).load(new File(patient.getAvatar())).error(R.drawable.teayudo_usuario)
                    .transform(new CropCircleTransformation())
                    .into(patientViewHolder.vImageAvatar);
        }

        patient = patientList.get(position);
        patientViewHolder.vNamePatient.setText(patient.getName());
        patientViewHolder.patientId = patient.getId();
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    /**
     * The state of the patient if is selected at the position passed.
     *
     * @param position - Adapter position
     * @return
     */
    public boolean isChecked(int position) {
        return (checkedList[position] != null) && checkedList[position].isChecked();
    }

    /**
     * The ID of the patient.
     * This patient is the one selected in the list.
     *
     * @param position - Adapter position
     * @return           Get the ParentID as long primitive.
     */
    public long getParentId(int position) {
        return checkedList[position].getPatientId();
    }

    public class PatientSimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView vImageAvatar;
        private TextView vNamePatient;
        private CheckBox vCheckItem;
        private boolean checked = false;
        private long patientId;

        public PatientSimpleViewHolder(View itemView) {
            super(itemView);

            vImageAvatar = (ImageView) itemView.findViewById(R.id.img_avatar_item);
            vNamePatient = (TextView) itemView.findViewById(R.id.txt_patient_name);
            vCheckItem = (CheckBox) itemView.findViewById(R.id.chk_click_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            checked = !checked;
            if (checked) {
                vCheckItem.setVisibility(View.VISIBLE);
            } else {
                vCheckItem.setVisibility(View.INVISIBLE);
            }

            int position = getAdapterPosition();
            checkedList[position] = this;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean value) {
            checked = value;
        }

        public long getPatientId() {
            return patientId;
        }

        public void setPatientId(long patientId) {
            this.patientId = patientId;
        }
    }
}
