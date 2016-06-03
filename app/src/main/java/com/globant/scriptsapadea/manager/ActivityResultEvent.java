package com.globant.scriptsapadea.manager;

import android.content.Intent;

/**
 * Created by leonel.mendez on 5/25/2015.
 * <p/>
 * Class to manage of picture capture from camera and gallery
 */
public class ActivityResultEvent {

    private int requestCode;
    private int resultCode;
    private Intent data;

    public ActivityResultEvent(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getRequestCode() {
        return requestCode;
    }


    public int getResultCode() {
        return resultCode;
    }


    public Intent getData() {
        return data;
    }


}
