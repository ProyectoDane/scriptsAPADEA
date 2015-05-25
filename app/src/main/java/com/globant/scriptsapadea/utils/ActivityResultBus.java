package com.globant.scriptsapadea.utils;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;


/**
 * Created by leonel.mendez on 5/25/2015.
 */
public class ActivityResultBus extends Bus {

    private static ActivityResultBus instance;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static ActivityResultBus getInstance(){
        if(instance == null)
            instance = new ActivityResultBus();
        return instance;
    }


    public void postEventForQueue(final Object event){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ActivityResultBus.getInstance().post(event);
            }
        });
    }
}
