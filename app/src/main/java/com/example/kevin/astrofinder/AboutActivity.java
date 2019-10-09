package com.example.kevin.astrofinder;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class AboutActivity extends Activity {
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.primaryBlue));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void playSong(View v){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.wake_up);
        mp.start();



    }
    @Override
    protected void onStop() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.wake_up);

        super.onStop();
        super.onPause();

        mp.stop();
        mp.release();

    }

    @Override
    protected void onPause() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.wake_up);
        super.onPause();

        mp.stop();
        mp.release();

    }

}
