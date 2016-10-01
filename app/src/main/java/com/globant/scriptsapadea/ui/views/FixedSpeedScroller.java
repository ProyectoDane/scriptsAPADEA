package com.globant.scriptsapadea.ui.views;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * With this we are able to control the speed of any {@link android.support.v4.view.ViewPager}.
 *
 * @author nicolas.quartieri
 */
public class FixedSpeedScroller extends Scroller {
    /** Duration */
    private int mDuration = 5000; // In milliseconds.

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setFixedDuration(int duration) {
        this.mDuration = duration;
    }
}