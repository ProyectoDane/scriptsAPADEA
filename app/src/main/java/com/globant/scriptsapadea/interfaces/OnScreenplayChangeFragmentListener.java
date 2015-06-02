package com.globant.scriptsapadea.interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by leonel.mendez on 6/1/2015.
 * <p/>
 * Interface to manage replacing fragments
 */
public interface OnScreenplayChangeFragmentListener {
    void onChangeFragment(Fragment fragment, boolean addInStack);
}
