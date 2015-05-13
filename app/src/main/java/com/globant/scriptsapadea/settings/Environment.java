package com.globant.scriptsapadea.settings;

/**
 * @author nicolas.quartieri
 *
 * This class represents a environment as defined in res/raw/environments.json
 */
public class Environment {

    private String languageCode;
    private String templateId;

    public Environment() {
    }

    public Environment(Environment env) {
        languageCode = env.getLanguageCode();
        templateId = env.getTemplateId();
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getTemplateId() {
        return templateId;
    }
}
