package com.globant.scriptsapadea.navigator;

/**
 * Created by nicolas.quartieri.
 */
public abstract class NavigatorEntry<T> {

    protected final Navigator navigator;

    public NavigatorEntry(Navigator navigator) {
        this.navigator = navigator;
    }

    public abstract void navigate();
}
