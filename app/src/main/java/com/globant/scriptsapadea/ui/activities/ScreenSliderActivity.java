package com.globant.scriptsapadea.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.manager.PageResult;
import com.globant.scriptsapadea.manager.ResponseEvent;
import com.globant.scriptsapadea.ui.fragments.SliderFragment;
import com.globant.scriptsapadea.ui.views.MyProgressBar;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by nicolas.quartieri
 */
@ContentView(R.layout.screen_slider_layout)
public class ScreenSliderActivity extends BaseActivity implements SliderFragment.SliderCallback {

    private ScreenSliderPageAdapter pageAdapter;

    private ViewPager viewPager;

    private int currentSlide;
    private boolean avoid = false;

    @InjectView(R.id.progress_bar)
    private MyProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_slider_layout);

        initActionBar(getApplicationContext());
        initViewPager(getApplicationContext());
    }

    private void initViewPager(Context applicationContext) {
        List<Fragment> fragments = getFragments();
        currentSlide = 0;

        pageAdapter = new ScreenSliderPageAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (!avoid) {
                    if (currentSlide - position > 0) { // Right direction
                        progressBar.prev();

                        currentSlide--;
                    } else if (currentSlide - position < 0) { // Left direction
                        progressBar.next();

                        currentSlide++;
                    }
                }

                avoid = false;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

    @Override
    public void nextSlide() {
        if (currentSlide + 1 < pageAdapter.getCount()) {
            avoid = true;

            viewPager.setCurrentItem(currentSlide + 1, true);

            progressBar.next();

            currentSlide++;

            //TODO This should be refavtor to be one line statement.
            MovedPageEvent movedPageEvent = new MovedPageEvent();
            movedPageEvent.setResult(new PageResult(currentSlide));
            movedPageEvent.setResult(true);
            bus.post(movedPageEvent);
        }
    }

    @Override
    public void previousSlide() {
        if (currentSlide - 1 >= 0) {
            avoid = true;

            viewPager.setCurrentItem(currentSlide - 1, true);

            progressBar.prev();

            currentSlide--;

            //TODO This should be refavtor to be one line statement.
            MovedPageEvent movedPageEvent = new MovedPageEvent();
            movedPageEvent.setResult(new PageResult(currentSlide));
            movedPageEvent.setResult(true);
            bus.post(movedPageEvent);
        }
    }

    @Subscribe
    public void logPageMoved(MovedPageEvent event) {
        Log.i("INFO", "Page moved to: " + event.getPayload().getPageId());
    }

    public static class MovedPageEvent extends ResponseEvent<PageResult> {
    }
}
