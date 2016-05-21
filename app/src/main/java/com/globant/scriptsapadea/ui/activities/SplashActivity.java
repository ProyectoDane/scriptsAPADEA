/* (c) Disney. All rights reserved. */
package com.globant.scriptsapadea.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.globant.scriptsapadea.R;

/**
 * This class provides the splash screen to be shown when the app is launch.
 *
 * @author nicolas.quartieri
 */
public class SplashActivity extends BaseActivity {

    private static final int SPLASH_DELAY = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Runnable enterAppRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                navigator.to(intent).navigate();

                finish();
            }
        };

        new Handler().postDelayed(enterAppRunnable, SPLASH_DELAY);
    }
}
