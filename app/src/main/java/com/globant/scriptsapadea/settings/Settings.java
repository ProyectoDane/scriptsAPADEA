package com.globant.scriptsapadea.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.globant.scriptsapadea.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author nicolas.quartieri
 */
public class Settings {

    private static final String CURRENT_ENV = "current_env";
    private static final String PREFS_NAME = "settings";

    /**
     * Available environments, the key is the name of the environment.
     */
    private Map<String, Environment> availableEnvironments;

    /**
     * Config that is in use.
     */
    private Config config;

    public Settings(Context context) {
        init(context);
    }

    /**
     * Loads configuration from preferences if available or from default Raw files if not.
     *
     * @param context used to access resources.
     */
    private void init(Context context) {
        final Gson gson = new Gson();
        Type mapType = new TypeToken<HashMap<String, Environment>>() {
        }.getType();
        availableEnvironments = gson.fromJson(readFile(context, R.raw.environments), mapType);

        config = readConfigFromPreferences(context);
        if (config == null) {
            config = gson.fromJson(readFile(context, R.raw.default_config), Config.class);
            if (!availableEnvironments.containsKey(config.getSelectedEnvironment())) {
                throw new IllegalArgumentException("The selected default environment is not available.");
            }
            saveConfigToPreferences(context);
        }
        // TODO Create Logs Module
        //DLog.setLoggingLevel(config.getLoggingLevel());

    }

    /**
     * Gets the available environments.
     */
    public Set<String> getAvailableEnvironments() {
        return availableEnvironments.keySet();
    }

    /**
     * Changes the environment to the selected one.
     */
    public void changeEnvironment(Context context, String newEnvironment) {
        if (availableEnvironments.containsKey(newEnvironment)) {
            this.config.setSelectedEnvironment(newEnvironment);
            saveConfigToPreferences(context);
        } else {
            throw new IllegalArgumentException("Selected environment does not exist");
        }
    }

    /**
     * Changes log level.
     */
    public void changeLogLevel(Context context, int level) {
        this.config.setLoggingLevel(level);
        // TODO
        //DLog.setLoggingLevel(level);
        saveConfigToPreferences(context);
    }

    public String getEnvironmentName() {
        return config.getSelectedEnvironment();
    }

    /**
     * @return A copy of the Config in use.
     */
    public Config getConfig() {
        return new Config(config);
    }

    /**
     * @return A copy of the environment in use.
     */
    public Environment getEnvironment() {
        return new Environment(availableEnvironments.get(config.getSelectedEnvironment()));
    }

    private Reader readFile(Context context, int resId) {
        InputStream inputStream = context.getResources().openRawResource(resId);
        return new InputStreamReader(inputStream, Charset.forName("UTF-8"));
    }

    private void saveConfigToPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(CURRENT_ENV, new Gson().toJson(config)).apply();
        // TODO
        //DLog.d("Config saved to preferences", config);
    }

    private Config readConfigFromPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (preferences.contains(CURRENT_ENV)) {
            return new Gson().fromJson(preferences.getString(CURRENT_ENV, ""), Config.class);
        }
        return null;
    }
}
