package com.globant.scriptsapadea.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;
import android.widget.TextView;

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

        initActionBar(getApplicationContext());
        initViewPager(getApplicationContext());
    }

    private void initViewPager(Context applicationContext) {
        List<Fragment> fragments = getFragments();

        pageAdapter = new ScreenSliderPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setAdapter(pageAdapter);
    }

    private void initActionBar(Context applicationContext) {
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.action_bar);

            TextView txtTitle = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.txt_title_action_bar);
            txtTitle.setText(R.string.title_back);

            // TODO: Image related with the Script
            ImageView imgTitleCenter = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.img_title_center);

            // TODO: Text related with the Script
            TextView txtTitleCenter = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.txt_title_center);
        }
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


    public class ScreenSliderPageAdapter extends FragmentStatePagerAdapter {

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
