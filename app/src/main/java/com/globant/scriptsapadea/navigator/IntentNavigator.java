package com.globant.scriptsapadea.navigator;

import android.content.Intent;

/**
 * Created by nicolas.quartieri.
 */
public class IntentNavigator extends NavigatorEntry<Intent> {

    private final Intent target;
    private final Navigator navigator;

    public IntentNavigator(Navigator navigator, Intent target) {
        super(navigator);

        this.navigator = navigator;
        this.target = target;
    }

    public Intent getTarget() {
        return target;
    }

    @Override
    public void navigate() {
        navigator.navigateTo(this);
    }
}
