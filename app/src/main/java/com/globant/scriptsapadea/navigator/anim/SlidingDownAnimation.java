/* (c) Disney. All rights reserved. */
package com.globant.scriptsapadea.navigator.anim;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.navigator.Navigator;

/**
 * Sliding down animation.
 */
public class SlidingDownAnimation extends Navigator.CustomAnimations {

    public SlidingDownAnimation() {
        super(R.anim.pull_down_to_bottom_navigate, R.anim.do_nothing);
    }
}
