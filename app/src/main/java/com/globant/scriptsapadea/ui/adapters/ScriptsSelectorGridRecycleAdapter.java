package com.globant.scriptsapadea.ui.adapters;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
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

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.ui.activities.ScriptSelectorActivity;
import com.globant.scriptsapadea.ui.fragments.ScreenPlayEditorFragment;
import com.globant.scriptsapadea.ui.fragments.ScreenScriptsSelectorFragment;
import com.globant.scriptsapadea.ui.views.SSPopupMenuWindow;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Adapter in charge of manage the screen responsable of display all script related to the selected patient.
 *
 * @author nicolas.quartieri.
 */
public class ScriptsSelectorGridRecycleAdapter extends RecyclerView.Adapter<ScriptsSelectorGridRecycleAdapter.ContactViewHolder>
        implements SSPopupMenuWindow.SSPopupMenuWindowListener {

    private static final long ANIMATION_TIME = 700;
    private static final int ANIMATED_ITEMS_COUNT = 2;

    private final Context context;
    private final ScreenScriptsSelectorFragment.ScreenScriptSelectorListener mScriptSelectorListener;
    private static List<Script> scriptList;

    private boolean showLoadingView;
    private int lastPosition = -1;
    private int itemsCount = 0;
    private boolean animateItems = true;
    private static int screenHeight = 0;

    public ScriptsSelectorGridRecycleAdapter(List<Script> scriptList, Context context) {
        this.scriptList = scriptList;
        this.context = context;
        this.mScriptSelectorListener = (ScreenScriptsSelectorFragment.ScreenScriptSelectorListener) context;
    }

    @Override
    public int getItemCount() {
        return scriptList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int position) {
        // FIXME: After 6 elements the animation goes over the older elements.
        //runAnimation(contactViewHolder.itemView, position);

        Script script = scriptList.get(position);
        if (script.isResourceImage()) {
            Picasso.with(context).load(script.getResImage()).error(R.drawable.teayudo_usuario)
                    .into(contactViewHolder.vImageAvatar);
        } else {
            Picasso.with(context).load(new File(script.getImageScripts())).error(R.drawable.teayudo_usuario)
                    .into(contactViewHolder.vImageAvatar);
        }

        contactViewHolder.vNameAvatar.setText(script.getName());
        contactViewHolder.setScript(script);
    }

    /**
     * Run the animation over the selected view.
     *
     * @param viewToAnimate - the {@link View} to animate.
     * @param position      - the actual position.
     */
    private void runAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            lastPosition = position;

            viewToAnimate.setTranslationY(getScreenHeight(context));
            viewToAnimate.animate().translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(ANIMATION_TIME)
                    .start();
        }
    }

    /**
     * Get the height of the screen of the device.
     *
     * @param context   the {@link Context}
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

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.script_card_layout, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public void showLoadingView() {
        showLoadingView = true;
        notifyItemChanged(0);
    }

    public void updateItems(boolean animated) {
        itemsCount = scriptList.size();
        animateItems = animated;
        notifyDataSetChanged();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SSPopupMenuWindow popupWindow;
        private Script script;

        private ImageView vImageAvatar;
        protected TextView vNameAvatar;
        protected CardView vCardView;
        private TextView vEditar;

        public ContactViewHolder(View v) {
            super(v);

            vCardView = (CardView) v.findViewById(R.id.card_view);
            vNameAvatar =  (TextView) v.findViewById(R.id.txt_avatar_name_item);
            vImageAvatar =  (ImageView) v.findViewById(R.id.img_avatar_item);
            vEditar =  (TextView) v.findViewById(R.id.txt_editar);

            vNameAvatar.setOnClickListener(this);
            vImageAvatar.setOnClickListener(this);

            popupWindow = SSPopupMenuWindow.createPopupWindow(context, false, ScriptsSelectorGridRecycleAdapter.this);
            vEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.showAsDropDown(vEditar, context.getResources().getInteger(R.integer.popup_xoff),
                            context.getResources().getInteger(R.integer.popup_yoff));
                }
            });
        }

        @Override
        public void onClick(View view) {
            mScriptSelectorListener.onNavigateToScriptSlider(this.script);
        }

        public void setScript(Script script) {
            this.script = script;
            popupWindow.setScript(script);
        }
    }

    @Override
    public void deleteScript(Script script) {
        scriptList.remove(script);
        ((ScriptSelectorActivity) context).deleteDBScript(script);
        notifyDataSetChanged();
    }

    @Override
    public void editScript(Script script) {
        Bundle imageArguments = new Bundle();
        mScriptSelectorListener.onNavigateToSlideEditor(ScreenPlayEditorFragment.newInstance(imageArguments, script, true));
    }

    @Override
    public void copyScript(Script script) {
        mScriptSelectorListener.onNavigateToScriptCopyScreen(script);
    }
}