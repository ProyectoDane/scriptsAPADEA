package com.globant.scriptsapadea.navigator;


import android.support.v4.app.Fragment;

/**
 * Created by nicolas.quartieri.
 */
public class FragmentNavigator extends NavigatorEntry<Fragment> {

    private final Fragment target;
    private final Navigator navigator;
    private boolean noPush;
    // TODO Must be final an setup with the builder
    private String tag;

    public FragmentNavigator(Navigator navigator, Fragment target) {
        super(navigator);

        this.navigator = navigator;
        this.target = target;
    }

    public Fragment getTarget() {
        return target;
    }

    public String getTag() {
        return tag == null ? getTarget().getClass().getSimpleName() : tag;
    }

    @Override
    public void navigate() {
        navigator.navigateTo(this);
    }

    public NavigatorEntry noPush() {
        this.noPush = true;
        return this;
    }

    public boolean isNoPush() {
        return noPush;
    }
}
