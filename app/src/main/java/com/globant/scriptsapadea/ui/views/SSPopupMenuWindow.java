package com.globant.scriptsapadea.ui.views;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.globant.scriptsapadea.R;

/**
 * Created by nicolas.quartieri.
 */
public class SSPopupMenuWindow extends PopupWindow {

    static public PopupWindow createPopupWindow(Context context, boolean focusable, String scriptId) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_layout, null);

        final PopupWindow popupWindow = new SSPopupMenuWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, focusable);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        final TextView txtEditOption = (TextView) popupView.findViewById(R.id.txt_edit_option);
        final TextView txtRemoveOption = (TextView) popupView.findViewById(R.id.txt_remove_option);
        final TextView txtCopyOption = (TextView) popupView.findViewById(R.id.txt_copy_option);
        txtEditOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                popupWindow.dismiss();
            }
        });
        txtRemoveOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                popupWindow.dismiss();
            }
        });
        txtCopyOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                popupWindow.dismiss();
            }
        });

        return popupWindow;
    }

    public SSPopupMenuWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }
}
