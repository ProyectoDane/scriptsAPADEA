package com.globant.scriptsapadea.ui.views;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.ui.adapters.ScriptsSelectorGridRecycleAdapter;

/**
 * PopupMenu created to display the 3 options action on Script
 *
 * @author nicolas.quartieri.
 */
public class SSPopupMenuWindow extends PopupWindow {

    private Script script;

    static public SSPopupMenuWindow createPopupWindow(final Context context, boolean focusable, final ScriptsSelectorGridRecycleAdapter adapter) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_layout, null);

        final SSPopupMenuWindow popupWindow = new SSPopupMenuWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, focusable);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        final TextView txtEditOption = (TextView) popupView.findViewById(R.id.txt_edit_option);
        final TextView txtRemoveOption = (TextView) popupView.findViewById(R.id.txt_remove_option);
        final TextView txtCopyOption = (TextView) popupView.findViewById(R.id.txt_copy_option);

        txtEditOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Script selectedScript = popupWindow.getScript();
                if (selectedScript.isEditable()) {
                    adapter.editScript(selectedScript);
                }
                popupWindow.dismiss();
            }
        });
        txtRemoveOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Script selectedScript = popupWindow.getScript();
                if (selectedScript.isEditable()) {
                    adapter.deleteScript(selectedScript);
                }
                popupWindow.dismiss();
            }
        });
        txtCopyOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Script selectedScript = popupWindow.getScript();
                if (selectedScript.isEditable()) {
                    adapter.copyScript(popupWindow.getScript());
                }
                popupWindow.dismiss();
            }
        });

        return popupWindow;
    }

    public SSPopupMenuWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public Script getScript() {
        return script;
    }

    public interface SSPopupMenuWindowListener {
        void deleteScript(Script script);
        void editScript(Script script);
        void copyScript(Script script);
    }
}
