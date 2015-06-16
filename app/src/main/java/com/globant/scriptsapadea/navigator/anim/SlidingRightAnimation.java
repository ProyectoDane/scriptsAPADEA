/* (c) Disney. All rights reserved. */
package com.globant.scriptsapadea.navigator.anim;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.navigator.Navigator;

/**
 * Sliding left animation.
 */
public class SlidingRightAnimation extends Navigator.CustomAnimations {

    public SlidingRightAnimation() {
        super(R.anim.pull_right_to_left, R.anim.do_nothing_for_x);
    }
}
