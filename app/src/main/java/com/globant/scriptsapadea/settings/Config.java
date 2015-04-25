package com.globant.scriptsapadea.settings;

/**
 * @author nicolas.quartieri
 *
 * This class represents the configuration
 */
public class Config {

    /**
     * Current environment name.
     */
    private String selectedEnvironment;

    private Log log;

    public Config() {
    }

    /**
     * Clones the given config.
     * @param config the config to clone.
     */
    public Config(Config config) {
        selectedEnvironment = config.selectedEnvironment;
        log = config.log;
    }

    public String getSelectedEnvironment() {
        return selectedEnvironment;
    }

    public void setSelectedEnvironment(String selectedEnvironment) {
        this.selectedEnvironment = selectedEnvironment;
    }

    /**
     * Gets current log level. Will return default value if none defined?
     */
    public int getLoggingLevel() {
        if (log != null) {
            return log.level;
        }
        return 0; //Default level???
    }

    /**
     * Sets current Logging Level.
     */
    public void setLoggingLevel(int newLevel) {
        if (log == null) {
            log = new Log();
        }
        log.level = newLevel;
    }

    private class Log {
        int level;
    }
}
