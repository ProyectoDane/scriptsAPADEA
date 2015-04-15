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

public class ScriptsSelectorGridRecycleAdapter extends RecyclerView.Adapter<ScriptsSelectorGridRecycleAdapter.ContactViewHolder> {

    private final Context context;

    private static List<Script> scriptList;
    private boolean showLoadingView;

    private int lastPosition = -1;

    private static final int VIEW_TYPE_DEFAULT = 1;
    private static final int VIEW_TYPE_LOADER = 2;

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
        setAnimation(contactViewHolder.itemView, i);

        contactViewHolder.vNameAvatar.setText(scriptList.get(i).getName());
        // TODO asign images to viewHolder
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            viewToAnimate.setTranslationY(size.y);
            viewToAnimate.animate().translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(1700)
                    .start();
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public void showLoadingView() {
        showLoadingView = true;
        notifyItemChanged(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (showLoadingView && position == 0) {
            return VIEW_TYPE_LOADER;
        } else {
            return VIEW_TYPE_DEFAULT;
        }
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView vImageRemove;
        private final ImageView vImageView;
        private final ImageView vImageEdit;
        private ImageView vImageAvatar;
        protected TextView vNameAvatar;
        protected CardView vCardView;

        public ContactViewHolder(View v) {
            super(v);
            vCardView = (CardView) v.findViewById(R.id.card_view);
            vNameAvatar =  (TextView) v.findViewById(R.id.txt_avatar_name_item);
            vImageAvatar =  (ImageView) v.findViewById(R.id.img_avatar_item);
            vImageRemove =  (ImageView) v.findViewById(R.id.img_remove);
            vImageView =  (ImageView) v.findViewById(R.id.img_view);
            vImageEdit =  (ImageView) v.findViewById(R.id.img_edit);

            vNameAvatar.setOnClickListener(this);
            vImageAvatar.setOnClickListener(this);
            vImageRemove.setOnClickListener(this);
            vImageView.setOnClickListener(this);
            vImageEdit.setOnClickListener(this);
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
                case R.id.img_remove:
                    Toast.makeText(context, "Click Remove, Position:" + getPosition() + " " + scriptList.get(getPosition()).getName(), Toast.LENGTH_LONG).show();
                    break;
                case R.id.img_view:
                    Toast.makeText(context, "Click View, Position:" + getPosition() + " " + scriptList.get(getPosition()).getName(), Toast.LENGTH_LONG).show();
                    break;
                case R.id.img_edit:
                    Toast.makeText(context, "Click Edit, Position:" + getPosition() + " " + scriptList.get(getPosition()).getName(), Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(context, "Click Other, Position:" + getPosition() + " " + scriptList.get(getPosition()).getName(), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}