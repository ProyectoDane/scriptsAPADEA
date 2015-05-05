package com.globant.scriptsapadea.navigator;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.globant.scriptsapadea.ui.activities.BaseActivity;

/**
 * Created by nicolas.quartieri.
 */
public class Navigator {


    private final FragmentActivity activity;
    private final NavigationListener listener;
    private final int layoutId;
    private final FragmentManager fragmentManager;
    
    private Fragment fragmentTarget;
    private Intent targetIntent;
    

    public Navigator(BaseActivity activity, Bundle savedInstanceState, int containerId, NavigationListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.layoutId = containerId;
        this.fragmentManager = activity.getSupportFragmentManager();

        // TODO
        /*
        if (savedInstanceState == null || savedInstanceState.get(MARKERS) == null) {
            flowMarkers = Lists.newLinkedList();
        } else {
            flowMarkers = Lists.newLinkedList((List<Integer>) savedInstanceState.get(MARKERS));
        }
        restorePendingNavigations(savedInstanceState);
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                //This is ugly, but we don't have a way to determine which was the entry that was removed!
                List<Integer> backStackEntries = Lists.newArrayList();
                for (int j = 0; j < fragmentManager.getBackStackEntryCount(); j++) {
                    FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(j);
                    backStackEntries.add(entry.getId());
                }
                flowMarkers.retainAll(backStackEntries);
            }
        });
        */
    }

    public IntentNavigator to(Intent target) {
        IntentNavigator intentNavigator = new IntentNavigator(this, target);
        
        return intentNavigator;
    }

    public FragmentNavigator to(Fragment target) {
        FragmentNavigator fragmentNavigator = new FragmentNavigator(this, target);
        
        return fragmentNavigator;
    }

    public void navigateTo(Intent target) {
        activity.startActivity(target);
    }

    public void navigateTo(Fragment target) {

        activity.getFragmentManager().beginTransaction().add(layoutId, target).commit();
    }

    public interface NavigationListener {

    }
}
