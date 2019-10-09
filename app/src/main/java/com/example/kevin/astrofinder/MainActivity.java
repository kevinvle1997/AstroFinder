package com.example.kevin.astrofinder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private static boolean firstRun = true;

    private int SETTINGS_REQUEST = 1;
    static public boolean mCustomary;
    static public boolean mHapticFeedback = true;
    static public boolean mHelper = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ImageView mLogoColor = findViewById(R.id.logo_color);
        final ImageView mLogo = findViewById(R.id.logo);

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        super.onCreate(savedInstanceState);
        setInstanceVarsFromSharedPrefs();
        setContentView(R.layout.activity_main);

        if (firstRun) {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.primaryGray));
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.primaryBlue));
            }
        }

    }

    public void onToggleButtonClicked(View v) {

/*
        final int mTag = ((MyTag)v.getTag()).getMyTag();
*/
        setInstanceVarsFromSharedPrefs();

        final String sTag = getId(v);
        boolean on = ((ToggleButton) v).isChecked();
        final ImageView mLogoColor = findViewById(R.id.logo_color);
        final ImageView mLogo = findViewById(R.id.logo);

        final ToggleButton mAsteroid = findViewById(R.id.asteroidButton);
        final ToggleButton mMap = findViewById(R.id.mapButton);
        final ToggleButton mAbout = findViewById(R.id.aboutButton);
        final ToggleButton mSettings = findViewById(R.id.settingsButton);
        disableButtons();





        if (on) {

            if (firstRun) {
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window = this.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(this.getResources().getColor(R.color.primaryBlue));
                }
                ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mLogoColor.setAlpha((Float) valueAnimator.getAnimatedValue());
                    }
                });
                animator.setDuration(3000);
                animator.start();

                new Timer().schedule(new TimerTask() {
                    public void run() {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                switch (sTag) {
                                    case "com.example.kevin.astrofinder:id/asteroidButton":
                                        startActivity(new Intent(MainActivity.this, NasaDataActivity.class));
                                        if (mCustomary) {
                                            Log.d("test", "customary");
                                        }
                                        resetButtons();
                                        mLogo.setImageResource(R.drawable.logo_color);

                                        break;


                                    case "com.example.kevin.astrofinder:id/aboutButton":
                                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                                        resetButtons();
                                        mLogo.setImageResource(R.drawable.logo_color);

                                        break;

                                    case "com.example.kevin.astrofinder:id/settingsButton":
                                        startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class), SETTINGS_REQUEST);
                                        resetButtons();
                                        mLogo.setImageResource(R.drawable.logo_color);
                                        break;

                                    case "com.example.kevin.astrofinder:id/mapButton":
                                        Toast.makeText(MainActivity.this, "The map function is not available in this release of the app", Toast.LENGTH_SHORT).show();

                                        break;

                                }
                            }
                        });
                    }
                }, 2000);

            }

            else {

                switch (sTag) {
                    case "com.example.kevin.astrofinder:id/asteroidButton":
                        startActivity(new Intent(MainActivity.this, NasaDataActivity.class));
                        if (mCustomary) {
                            Log.d("test", "customary");
                        }
                        resetButtons();

                        break;

                    case "com.example.kevin.astrofinder:id/mapButton":
                        startActivity(new Intent(MainActivity.this, MapActivity.class));
                        resetButtons();

                        break;

                    case "com.example.kevin.astrofinder:id/aboutButton":
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        resetButtons();

                        break;

                    case "com.example.kevin.astrofinder:id/settingsButton":
                        startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class), SETTINGS_REQUEST);
                        resetButtons();

                        break;

                }
        }


        }


    }
    public void disableButtons(){
        ToggleButton mAsteroid = findViewById(R.id.asteroidButton);
        ToggleButton mMap = findViewById(R.id.mapButton);
        ToggleButton mAbout = findViewById(R.id.aboutButton);
        ToggleButton mSettings = findViewById(R.id.settingsButton);
        mAsteroid.setEnabled(false);
        mMap.setEnabled(false);
        mSettings.setEnabled(false);
        mAbout.setEnabled(false);
    }
    public void resetButtons(){
        ToggleButton mAsteroid = findViewById(R.id.asteroidButton);
        ToggleButton mMap = findViewById(R.id.mapButton);
        ToggleButton mAbout = findViewById(R.id.aboutButton);
        ToggleButton mSettings = findViewById(R.id.settingsButton);
        mAsteroid.setChecked(false);
        mMap.setChecked(false);
        mAbout.setChecked(false);
        mSettings.setChecked(false);
        mAsteroid.setEnabled(true);
        mMap.setEnabled(true);
        mSettings.setEnabled(true);
        mAbout.setEnabled(true);
        firstRun = false;
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.primaryBlue));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_REQUEST) {
            SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
            mCustomary = sharedPref.getBoolean("customary", true);
        }
    }

    @SuppressLint("ResourceType")
    public static String getId(View view) {
        if (view.getId() == 0xffffffff) return "no-id";
        else return view.getResources().getResourceName(view.getId());
    }

    private void setInstanceVarsFromSharedPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mCustomary = prefs.getBoolean("customary", true);
        mHapticFeedback = prefs.getBoolean("haptic_feedback", true);
        mHelper = prefs.getBoolean("hints", true);


    }



}
