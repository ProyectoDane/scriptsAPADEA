package com.globant.scriptsapadea.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.models.Patient;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.ui.adapters.ScriptsSelectorGridRecycleAdapter;

/**
 * PopupMenu created to display the 3 options action on Script
 *
 * @author nicolas.quartieri.
 */
public class SSPopupMenuWindow extends PopupWindow {

    private static TextView txtEditOption;
    private static TextView txtRemoveOption;
    private static TextView txtCopyOption;
    private Script script;

    static public SSPopupMenuWindow createPopupWindow(final Context context, boolean focusable, final ScriptsSelectorGridRecycleAdapter adapter) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_layout, null);

		final SSPopupMenuWindow popupWindow = new SSPopupMenuWindow(popupView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, focusable);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        txtEditOption = (TextView) popupView.findViewById(R.id.txt_edit_option);
        txtRemoveOption = (TextView) popupView.findViewById(R.id.txt_remove_option);
        txtCopyOption = (TextView) popupView.findViewById(R.id.txt_copy_option);

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
                final Script selectedScript = popupWindow.getScript();
                if (selectedScript.isEditable()) {
                    new AlertDialog.Builder(context)
                            .setMessage(R.string.remove_script)
                            .setTitle(R.string.remove_title)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    adapter.deleteScript(selectedScript);
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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
                popupWindow.dismiss();
            }
        });
        txtCopyOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.copyScript(popupWindow.getScript());
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

    public void notifyPopUpApadea(boolean isApadeaPatient) {
        if (isApadeaPatient) {
            txtEditOption.setVisibility(View.GONE);
            txtRemoveOption.setVisibility(View.GONE);
        } else {
            txtEditOption.setVisibility(View.VISIBLE);
            txtRemoveOption.setVisibility(View.VISIBLE);
        }
    }

    public interface SSPopupMenuWindowListener {
        void deleteScript(Script script);
        void editScript(Script script);
        void copyScript(Script script);
    }
}
