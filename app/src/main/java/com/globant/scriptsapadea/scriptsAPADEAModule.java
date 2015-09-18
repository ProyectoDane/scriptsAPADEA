package com.globant.scriptsapadea;

import android.content.Context;

import com.globant.scriptsapadea.manager.PatientManager;
import com.globant.scriptsapadea.settings.Settings;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.squareup.otto.Bus;

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
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    Settings provideSettings(Context context) {
        return new Settings(context);
    }

    @Provides
    @Singleton
    PatientManager providePacientManager(Context context) {
        return new PatientManager(context);
    }

    @Provides
    @Singleton
    SQLiteHelper provideSQLiteHelper(Context context) {
        return new SQLiteHelper(context);
    }
}
