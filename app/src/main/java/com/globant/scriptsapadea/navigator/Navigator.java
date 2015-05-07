package com.globant.scriptsapadea.navigator;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.globant.scriptsapadea.ui.activities.BaseActivity;

import java.io.Serializable;

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

    public void navigateTo(IntentNavigator intentNavigator) {
        activity.startActivity(intentNavigator.getTarget());

        CustomAnimations animations = intentNavigator.getAnimations();
        if (animations != null) {
            activity.overridePendingTransition(animations.enter, animations.exit);
        }
    }

    // TODO: replace() transaction must replace add()
    public void navigateTo(FragmentNavigator fragmentNavigator) {
        if (fragmentNavigator.isNoPush()) {
            // TODO: Assign Tag
            activity.getFragmentManager().beginTransaction().add(layoutId, fragmentNavigator.getTarget()).addToBackStack(null).commit();
        } else {
            activity.getFragmentManager().beginTransaction().add(layoutId, fragmentNavigator.getTarget()).commit();
        }
    }

    public interface NavigationListener {

    }

    /**
     * The custom transition animation configuration class.
     */
    public static class CustomAnimations implements Serializable {
        public final int enter;
        public final int exit;
        public final int popEnter;
        public final int popExit;

        /**
         * The {@link CustomAnimations} constructor.
         *
         * @param enter    the enter animation.
         * @param exit     the exit animation.
         * @param popEnter the pop backstack enter animation (only used for fragment transactions).
         * @param popExit  the pop backstack exit animation (only used for fragment transactions).
         */
        public CustomAnimations(int enter, int exit, int popEnter, int popExit) {
            this.enter = enter;
            this.exit = exit;
            this.popEnter = popEnter;
            this.popExit = popExit;
        }

        /**
         * The {@link CustomAnimations} constructor.
         *
         * @param enter A resource ID of the animation resource to use for
         *              the incoming view.  Use 0 for no animation.
         * @param exit A resource ID of the animation resource to use for
         *             the outgoing view.  Use 0 for no animation.
         */
        public CustomAnimations(int enter, int exit) {
            this(enter, exit, 0, 0);
        }
    }
}
