package com.globant.scriptsapadea.navigator;


import android.app.Fragment;

/**
 * Created by nicolas.quartieri.
 */
public class FragmentNavigator extends NavigatorEntry<Fragment> {

    private final Fragment target;
    private final Navigator navigator;
    private boolean noPush;

    public FragmentNavigator(Navigator navigator, Fragment target) {
        super(navigator);

        this.navigator = navigator;
        this.target = target;
    }

    public Fragment getTarget() {
        return target;
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
