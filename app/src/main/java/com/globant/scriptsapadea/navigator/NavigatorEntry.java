package com.globant.scriptsapadea.navigator;

/**
 * Created by nicolas.quartieri.
 */
public abstract class NavigatorEntry<T> {

    protected final Navigator navigator;
    private Navigator.CustomAnimations animation;

    public NavigatorEntry(Navigator navigator) {
        this.navigator = navigator;
    }

    public abstract void navigate();

    public NavigatorEntry withAnimations(Navigator.CustomAnimations animation) {
        this.animation = animation;
        return this;
    }

    public Navigator.CustomAnimations getAnimations() {
        return animation;
    }
}
