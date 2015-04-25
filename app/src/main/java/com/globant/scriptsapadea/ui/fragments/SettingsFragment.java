package com.globant.scriptsapadea.ui.fragments;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.settings.Settings;
import com.google.common.collect.Lists;

import java.util.Set;

import javax.inject.Inject;

import roboguice.fragment.provided.RoboPreferenceFragment;

/**
 * @author nicolas.quartieri
 */
public class SettingsFragment extends RoboPreferenceFragment {

    //FIXME get log levels from log lib.
    private static final String[] LOG_LEVELS = new String[]{"VERBOSE", "DEBUG", "INFO", "WARN", "ERROR", "ASSERT"};

    @Inject
    Settings settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        final ListPreference envPreference = (ListPreference) findPreference("target_environment");
        final String[] envs = availableEnvironments();
        envPreference.setEntries(envs);
        envPreference.setEntryValues(envs);
        envPreference.setValueIndex(Lists.newArrayList(envs).indexOf(settings.getEnvironmentName()));
        envPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String value = (String) newValue;
                settings.changeEnvironment(getActivity(), value);
                return true;
            }
        });

        final ListPreference logPreference = (ListPreference) findPreference("debug_loglevel");
        logPreference.setEntries(LOG_LEVELS);
        logPreference.setEntryValues(LOG_LEVELS);
        logPreference.setValueIndex(settings.getConfig().getLoggingLevel());
        logPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int selected = Lists.newArrayList(LOG_LEVELS).indexOf(newValue);
                settings.changeLogLevel(getActivity(), selected);
                return true;
            }
        });
    }

    private String[] availableEnvironments() {
        Set<String> envs = settings.getAvailableEnvironments();
        return envs.toArray(new String[envs.size()]);
    }
}
