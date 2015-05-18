package com.globant.scriptsapadea.ui.adapters;

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
import com.globant.scriptsapadea.models.Script;

import java.util.List;

/**
 * Created by nicola.quartieri.
 */
public class ScriptsSelectorGridRecycleAdapter extends RecyclerView.Adapter<ScriptsSelectorGridRecycleAdapter.ContactViewHolder> {

    private final Context context;

    private static List<Script> scriptList;
    private boolean showLoadingView;

    private int lastPosition = -1;

    private static final int ANIMATED_ITEMS_COUNT = 2;

    private int itemsCount = 0;
    private boolean animateItems = true;
    private static int screenHeight = 0;

    public ScriptsSelectorGridRecycleAdapter(List<Script> scriptList, Context context) {
        this.scriptList = scriptList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return scriptList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        runAnimation(contactViewHolder.itemView, i);

        contactViewHolder.vImageAvatar.setImageResource(R.drawable.canilla);
        contactViewHolder.vNameAvatar.setText(scriptList.get(i).getName());
        // TODO asign images to viewHolder
    }

    private void runAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            lastPosition = position;

            viewToAnimate.setTranslationY(getScreenHeight(context));
            viewToAnimate.animate().translationY(0)
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

        private ImageView vImageAvatar;
        protected TextView vNameAvatar;
        protected CardView vCardView;

        public ContactViewHolder(View v) {
            super(v);

            vCardView = (CardView) v.findViewById(R.id.card_view);
            vNameAvatar =  (TextView) v.findViewById(R.id.txt_avatar_name_item);
            vImageAvatar =  (ImageView) v.findViewById(R.id.img_avatar_item);

            vNameAvatar.setOnClickListener(this);
            vImageAvatar.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // TODO Go to the Selected Script
            //showProgress();
            //profileManager.updateAvatar(authenticationManager.getUserSwid(),mScriptAdapter.getItem(position).getId());

            switch (view.getId()) {
                case R.id.txt_avatar_name_item:
                    Toast.makeText(context, "Click Text, Position:" + getPosition() + " " + scriptList.get(getPosition()).getName(), Toast.LENGTH_LONG).show();
                    break;
                case R.id.img_avatar_item:
                    Toast.makeText(context, "Click Avatar, Position:" + getPosition() + " " + scriptList.get(getPosition()).getName(), Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(context, "Click Other, Position:" + getPosition() + " " + scriptList.get(getPosition()).getName(), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}