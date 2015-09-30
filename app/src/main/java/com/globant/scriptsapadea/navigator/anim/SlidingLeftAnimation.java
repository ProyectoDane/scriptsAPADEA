/* (c) Disney. All rights reserved. */
package com.globant.scriptsapadea.navigator.anim;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.navigator.Navigator;

/**
 * Sliding left animation.
 */
public class SlidingLeftAnimation extends Navigator.CustomAnimations {

    public SlidingLeftAnimation() {
        super(R.anim.pull_left_from_right, R.anim.do_nothing_for_x);
    }
}
