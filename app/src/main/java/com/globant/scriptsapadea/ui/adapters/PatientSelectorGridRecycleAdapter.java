package com.globant.scriptsapadea.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.ui.fragments.PatientListFragment;
import com.globant.scriptsapadea.widget.CropCircleTransformation;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Adapter in charge of manage the screen of all available patient's
 *
 * @author nicolas.quartieri.
 */
public class PatientSelectorGridRecycleAdapter extends RecyclerView.Adapter<PatientSelectorGridRecycleAdapter.PatientViewHolder> {

    private static final int TOP_POSITION = 300;

    private final PatientListFragment context;
    private PatientListFragment.PatientListFragmentListener mListener;

    private static List<Patient> patientList;
    private boolean showLoadingView;

    private int lastPosition = -1;

    private static final int ANIMATED_ITEMS_COUNT = 2;

    private int itemsCount = 0;
    private boolean animateItems = true;
    private static int screenHeight = 0;

    public PatientSelectorGridRecycleAdapter(List<Patient> patientList, PatientListFragment context) {
        this.patientList = patientList;
        this.context = context;
        this.mListener = (PatientListFragment.PatientListFragmentListener) context.getActivity();
    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_card_layout, parent, false);

        return new PatientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PatientViewHolder patientViewHolder, int position) {
        runAnimation(patientViewHolder.itemView, position);

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

        patientViewHolder.vNamePatient.setText(patientList.get(position).getName());

        if (patient.getName().equalsIgnoreCase(context.getString(R.string.app_owner_name))) {
            patientViewHolder.vTextLegend.setText(R.string.default_script_example);
        } else {
            patientViewHolder.vTextLegend.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    /**
     * Run the animation over the selected view.
     *
     * @param viewToAnimate - the {@link View} to animate.
     * @param position      - the actual position.
     */
    private void runAnimation(View viewToAnimate, int position) {
        if (!animateItems || position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastPosition) {
            lastPosition = position;

            int yPosition = 0;
            if (patientList.size() == 1) {
                yPosition = TOP_POSITION;
            }
            viewToAnimate.setTranslationY(getScreenHeight(context.getActivity()));
            viewToAnimate.animate()
                    .translationY(yPosition)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    /**
     * Get the height of the screen of the device.
     *
     * @param context
     * @return          the size as float primitive.
     */
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
        protected TextView vTextLegend;
        protected ImageView vImageAvatar;
        protected TextView vNamePatient;
        protected TextView vEditCardButton;
        protected TextView vEditButtonAction;
        protected TextView vRemoveButtonAction;
        protected CardView vCardView;

        private boolean editButtonStateOpen = false;

        public PatientViewHolder(View v) {
            super(v);
            vCardView = (CardView) v.findViewById(R.id.card_view);
            vNamePatient =  (TextView) v.findViewById(R.id.txt_patient_name_item);
            vImageAvatar =  (ImageView) v.findViewById(R.id.img_avatar_item);
            vTextLegend =  (TextView) v.findViewById(R.id.txt_patient_leyend_item);
            vEditCardButton = (TextView) v.findViewById(R.id.btn_editar);
            vEditButtonAction = (TextView) v.findViewById(R.id.btn_edit_action);
            vRemoveButtonAction = (TextView) v.findViewById(R.id.btn_remove_action);

            //final Patient selectedPatient = patientList.get(getPosition());

            vEditCardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Patient selectedPatient = patientList.get(getPosition());
                    if (selectedPatient.isEditable()) {
                        if (!editButtonStateOpen) {

                            //Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.clockwise);
                            //vCardView.startAnimation(animation1);

                            ViewCompat.animate(vCardView).setListener(new ViewPropertyAnimatorListener() {
                                @Override
                                public void onAnimationStart(View view) {
                                    //Animation fadeOutAnimation = AnimationUtils.loadAnimation(context.getActivity(), android.R.anim.fade_out);
                                    //vEditCardButton.startAnimation(fadeOutAnimation);
                                }

                                @Override
                                public void onAnimationEnd(View view) {
                                    // Do nothing.
                                }

                                @Override
                                public void onAnimationCancel(View view) {
                                    // Do nothing.
                                }
                            }).translationX(-vCardView.getWidth() * 0.37f)
                                    .setDuration(500)
                                    .start();
                        } else {
                            ViewCompat.animate(vCardView).setListener(new ViewPropertyAnimatorListener() {
                                @Override
                                public void onAnimationStart(View view) {
                                    // Do nothing.
                                }

                                @Override
                                public void onAnimationEnd(View view) {
                                    //Animation fadeInAnimation = AnimationUtils.loadAnimation(context.getActivity(), android.R.anim.fade_in);
                                    //vEditCardButton.startAnimation(fadeInAnimation);
                                }

                                @Override
                                public void onAnimationCancel(View view) {
                                    // Do nothing.
                                }
                            }).translationX(0)
                                    .setDuration(500)
                                    .start();
                        }

                        editButtonStateOpen = !editButtonStateOpen;
                    }
                }
            });

            vEditButtonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                // TODO Navigate to Edit View
                }
            });

            // TODO Refactoring
            vRemoveButtonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context.getActivity())
                            .setMessage("Confirma eliminar esta persona ?")
                            .setTitle("Eliminar")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Patient selectedPatient = patientList.get(getPosition());
                                    if (selectedPatient != null) {
                                        removeAt(getPosition());
                                    } else {
                                        Log.e("ERROR", "Selected patient for delete was null.");
                                    }
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            });

            //v.setTag(patientList.get(getPosition()));

            // TODO Find a way to decouple this
            v.setOnClickListener(this);
        }

        public void removeAt(int position) {
            Patient selectedPatient = patientList.get(position);
            if (!selectedPatient.getName().equalsIgnoreCase(context.getString(R.string.app_owner_name))) {
                mListener.deletePatient(patientList.get(position));
                patientList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(getPosition(), patientList.size());
            } else {
                // TODO notification "This profile can not be erase"
            }
        }

        @Override
        public void onClick(View view) {
            mListener.onNavigateToPatient(patientList.get(getPosition()));
        }
    }
}
