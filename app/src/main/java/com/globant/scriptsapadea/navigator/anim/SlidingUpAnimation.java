/* (c) Disney. All rights reserved. */
package com.globant.scriptsapadea.navigator.anim;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.navigator.Navigator;

/**
 * Sliding up animation.
 * Created by jorge.capra on 1/21/15.
 */
public class SlidingUpAnimation extends Navigator.CustomAnimations {

    public SlidingUpAnimation() {
        super(R.anim.pull_up_from_bottom, R.anim.do_nothing);
    }
}
