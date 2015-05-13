package com.globant.scriptsapadea;

import android.content.Context;

import com.globant.scriptsapadea.settings.Settings;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;

import javax.inject.Singleton;

/**
 * Created by nicolas.quartieri
 */
public class scriptsAPADEAModule implements Module {

    @Override
    public void configure(Binder binder) {
        // Not used, but required.
    }

    @Provides
    @Singleton
    Settings provideSettings(Context context) {
        return new Settings(context);
    }
}
