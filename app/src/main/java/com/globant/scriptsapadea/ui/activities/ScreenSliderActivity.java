package com.globant.scriptsapadea.ui.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.ui.fragments.SliderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas.quartieri
 */
public class ScreenSliderActivity extends BaseActivity {

    ScreenSliderPageAdapter pageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_slider_layout);

        List<Fragment> fragments = getFragments();

        pageAdapter = new ScreenSliderPageAdapter(getFragmentManager(), fragments);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setAdapter(pageAdapter);
    }

    private List<Fragment> getFragments() {
        ArrayList fragments = new ArrayList<>();

        // TODO The number of Fragments depends on the numbers of images of the Social Script selected.
        fragments.add(SliderFragment.newInstance("page1"));
        fragments.add(SliderFragment.newInstance("page2"));
        fragments.add(SliderFragment.newInstance("page3"));
        fragments.add(SliderFragment.newInstance("page4"));

        return fragments;
    }


    public class ScreenSliderPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public ScreenSliderPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);

            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
