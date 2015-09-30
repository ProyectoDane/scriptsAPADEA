package com.globant.scriptsapadea.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.widget.CropCircleTransformation;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Scripts Grid Adapter used in Scripts selector screen.
 *
 * @author nicolas.quartieri
 */

public class ScriptsSelectorGridAdapter extends ArrayAdapter<Script> {

    private List<Script> items;
    private Context context;
    private View userView;

    public ScriptsSelectorGridAdapter(Context context, List<Script> scripts) {
        super(context, R.layout.script_item, scripts);
        this.context = context;
        this.items = scripts;
        this.userView = null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ScriptsHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.script_item, parent, false);

            holder = new ScriptsHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.img_avatar_item);
            holder.name = (TextView) row.findViewById(R.id.txt_avatar_name_item);
            row.setTag(holder);
        } else {
            holder = (ScriptsHolder) row.getTag();
        }

        Script script = getItem(position);

        if (script.getId() == 0) {
            holder.name.setText(R.string.default_script_name);
            CropCircleTransformation circleTransformation = new CropCircleTransformation();
            Picasso.with(this.context).load(R.drawable.ic_launcher).transform(circleTransformation).into(holder.imageView);
        } else {
            holder.name.setText(script.getName());
            CropCircleTransformation circleTransformation = new CropCircleTransformation();
            if (script.isResourceImage()) {
                Picasso.with(this.context).load(script.getResImage()).transform(circleTransformation)
                        .placeholder(R.drawable.ic_launcher).into(holder.imageView);
            } else {
                Picasso.with(this.context).load(new File(script.getImageScripts())).transform(circleTransformation)
                        .placeholder(R.drawable.ic_launcher).into(holder.imageView);
            }
        }

        return row;
    }

    private static class ScriptsHolder {
        ImageView imageView;
        TextView name;
    }
}
