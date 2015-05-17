/* (c) Disney. All rights reserved. */
package com.globant.scriptsapadea.navigator.anim;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.navigator.Navigator;

/**
 * Sliding up animation.
 */
public class SlidingUpAnimation extends Navigator.CustomAnimations {

    public SlidingUpAnimation() {
        super(R.anim.pull_up_from_bottom, R.anim.do_nothing);
    }
}
