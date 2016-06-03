package com.globant.scriptsapadea.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import com.globant.scriptsapadea.R;
import com.google.common.base.Preconditions;

/**
 * Alert Dialog encapsulation with time task handle.
 *
 * @author nicolas.quartieri
 */
public class TEAlertDialog {

    private static final String ALERT_TITLE = "TEAyudo";
    private static final long DELAY_TIME = 3000;

    private final Context context;
    private String message;

    public TEAlertDialog(Context context) {
        this.context = context;
    }

    public TEAlertDialog setTitle(String message) {
        Preconditions.checkNotNull(message, "The payload cannot be null.");
        this.message = message;
        return this;
    }

    public TEAlertDialog setTitle(int message) {
        String stringMessage = context.getResources().getString(message);
        Preconditions.checkNotNull(stringMessage, "The payload cannot be null.");
        this.message = stringMessage;
        return this;
    }

    public void show() {
        final Handler handler  = new Handler();
        final AlertDialog alert = new AlertDialog.Builder(context)
                .setTitle(ALERT_TITLE)
                .setMessage(message)
                .setIcon(R.drawable.teayudo_usuario)
                .show();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, DELAY_TIME);
    }
}
