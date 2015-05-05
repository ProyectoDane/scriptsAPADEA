package com.globant.scriptsapadea.navigator;


import android.app.Fragment;

/**
 * Created by nicolas.quartieri.
 */
public class FragmentNavigator extends NavigatorEntry<Fragment> {

    private final Fragment target;

    public FragmentNavigator(Navigator navigator, Fragment target) {
        super(navigator);

        this.target = target;
    }

    @Override
    public void navigate() {
        navigator.navigateTo(target);
    }
}
