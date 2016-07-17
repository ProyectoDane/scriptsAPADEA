package com.globant.scriptsapadea.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.scriptsapadea.R;
import com.globant.scriptsapadea.manager.PageResult;
import com.globant.scriptsapadea.manager.ResponseEvent;
import com.globant.scriptsapadea.models.Script;
import com.globant.scriptsapadea.models.Slide;
import com.globant.scriptsapadea.sql.SQLiteHelper;
import com.globant.scriptsapadea.ui.fragments.ChooseScriptPictureFragment;
import com.globant.scriptsapadea.ui.fragments.CreateScriptFragment;
import com.globant.scriptsapadea.ui.fragments.ShowScriptPictureFragment;
import com.globant.scriptsapadea.ui.fragments.SliderFragment;
import com.globant.scriptsapadea.ui.views.MyProgressBar;
import com.globant.scriptsapadea.widget.CropCircleTransformation;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * This class provides the container activity for the script sliding screen.
 *
 * @author nicolas.quartieri
 */
@ContentView(R.layout.screen_slider_layout)
public class ScreenSliderActivity extends BaseActivity implements SliderFragment.SliderCallback,
        CreateScriptFragment.OnTakeScriptPictureFragmentListener,
        ChooseScriptPictureFragment.OnShowScriptPictureFragmentListener,
        ShowScriptPictureFragment.OnEditFragmentListener {

    private ScreenSliderPageAdapter pageAdapter;
    private ViewPager viewPager;

    private Script script;

    private int currentSlide;
    private boolean avoid = false;

    @Inject
    private SQLiteHelper mDBHelper;

    @InjectView(R.id.progress_bar)
    private MyProgressBar progressBar;

    public static Intent createIntent(Context context, Script script) {
        Intent intent = new Intent(context, ScreenSliderActivity.class);
        intent.putExtra(Script.SCRIPT, script);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_slider_layout);

        TextView txtScriptName = (TextView)findViewById(R.id.txt_script_name);

        if (savedInstanceState == null) {
            if (getIntent().hasExtra(Script.SCRIPT)) {
                script = (Script)getIntent().getExtras().getSerializable(Script.SCRIPT);

                List<Slide> slideList = script.getSlides();
                if (slideList.size() > 1 && slideList.get(0).getResImage() == R.drawable.teayudo_usuario) {
                    mDBHelper.deleteSlide(slideList.get(0), script.getId());
                    slideList.remove(0);
                }

                progressBar.setSize(script.getSlides().size());
                progressBar.build();

                String scriptName = script.getName();
                if (scriptName != null) {
                    txtScriptName.setText(scriptName);
                }
            }
        }

        ImageView imgProfile = (ImageView) findViewById(R.id.img_profile);
        if (script.isResourceImage()) {
            Picasso.with(getApplication()).load(script.getResImage()).error(R.drawable.ic_launcher)
                    .transform(new CropCircleTransformation())
                    .into(imgProfile);
        } else {
            Picasso.with(getApplication()).load(new File(script.getImageScripts())).error(R.drawable.ic_launcher)
                    .transform(new CropCircleTransformation())
                    .into(imgProfile);
        }

        View imaEditProfile = findViewById(R.id.img_profile_edit);
        imaEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditScriptProfile(CreateScriptFragment.newInstance(script));
            }
        });

        initActionBar();
        initViewPager();
    }

    private void initViewPager() {
        List<Fragment> fragments = getFragments();
        currentSlide = 0;

        pageAdapter = new ScreenSliderPageAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Do nothing.
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
                // Do noting.
            }
        });

        viewPager.setAdapter(pageAdapter);
    }

    private void initActionBar() {
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

    /**
     * Get the list of {@link SliderFragment} base on the provided list of {@link Slide}.
     *
     * @return the fragments list.
     */
    private List<Fragment> getFragments() {
        ArrayList fragments = new ArrayList<>();

        List<Slide> slides = script.getSlides();
        if (slides.size() > 0) {
            for (Slide slide : slides) {
                fragments.add(SliderFragment.newInstance(slide));
            }
        } else {
            SliderFragment fragment = SliderFragment.newInstance((new Slide(0, R.drawable.teayudo_iconovacio, "", Slide.ONLY_IMAGE)));
            fragments.add(fragment);
        }

        return fragments;
    }

    @Override
    public void onTakeScriptPictureFragment(Fragment fragment) {
        navigator.to(fragment).navigate();
    }

    @Override
    public void onEditScriptProfile(Fragment fragment) {
        navigator.to(fragment).navigate();
    }

    @Override
    public void onShowScriptPictureFragment(Fragment fragment) {
        navigator.to(fragment).navigate();
    }

    @Override
    public void onEditFragment(Fragment fragment) {
        navigator.to(fragment).navigate();
    }

    /**
     * This Adapter controls the flow of {@link SliderFragment}
     */
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

            //TODO This should be refactor to be one line statement.
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

            //TODO This should be refactor to be one line statement.
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.pull_right_to_left);
    }
}
