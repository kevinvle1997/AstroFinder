package com.example.kevin.astrofinder;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AsteroidList extends Activity {
    public int mIndex = 0;
    private static boolean firstRun = true;
    private int SETTINGS_REQUEST = 1;
    MainActivity variables = new MainActivity();
    private boolean cCustomary = variables.mCustomary;
    private boolean mHapticFeedback = variables.mHapticFeedback;
    private boolean mHelper = variables.mHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NasaDataActivity mData = new NasaDataActivity();
        final List<Asteroid> asteroidList = mData.asteroidGroup;
        int currentOrientation = this.getResources().getConfiguration().orientation;
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.primaryBlue));
        }
        if(mHelper && firstRun) {
            Toast.makeText(this, "Click the arrow keys to navigate", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "through the list", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "the image represents if the asteroid is potentially hazardous, green = safe, red = hazardous", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Click this image to return to main menu.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Rotate the screen to see another layout", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Press the magnifying glass to start a new search", Toast.LENGTH_SHORT).show();

            firstRun = false;

        }

        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_asteroid_list);
            ListView lv = findViewById(android.R.id.list);
            if (lv != null) {
                AsteroidAdapter mAdapter;
                mAdapter = new AsteroidAdapter(this, asteroidList);
                lv.setAdapter(mAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        // Retrieve our class object and use index to resolve item tapped
                        final Asteroid item = asteroidList.get(position);
                        final int menuIndex = item.getIndex();

                    }
                });
            }

        }

        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_asteroid_list);




            if (mIndex < asteroidList.size()) {
                Button asteroidGIF = (Button) findViewById(R.id.asteroidGIF);
                TextView asteroidName = (TextView) findViewById(R.id.asteroidName);
                TextView passbyDate = (TextView) findViewById(R.id.passByDate);
                TextView asteroidVelo = (TextView) findViewById(R.id.asteroidVelo);
                TextView asteroidID = (TextView) findViewById(R.id.asteroidID);
                TextView asteroidDiameter = (TextView) findViewById(R.id.asteroidDiameter);
                TextView asteroidMD = (TextView) findViewById(R.id.asteroidMD);
                setResources(mIndex);

                asteroidGIF.invalidate();
                asteroidName.invalidate();
                passbyDate.invalidate();
                asteroidVelo.invalidate();
                asteroidMD.invalidate();
                asteroidID.invalidate();
                asteroidDiameter.invalidate();



            }




        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_REQUEST) {
            SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
            cCustomary = sharedPref.getBoolean("customary", true);
        }
    }




    public void increaseIndex(View v) {

        NasaDataActivity mData = new NasaDataActivity();
        Button prevButton = (Button)findViewById(R.id.prevButton);
        final List<Asteroid> asteroidList = mData.asteroidGroup;
        Button asteroidGIF = (Button) findViewById(R.id.asteroidGIF);
        TextView asteroidName = (TextView) findViewById(R.id.asteroidName);
        TextView passbyDate = (TextView) findViewById(R.id.passByDate);
        TextView asteroidVelo = (TextView) findViewById(R.id.asteroidVelo);
        TextView asteroidID = (TextView) findViewById(R.id.asteroidID);
        TextView asteroidDiameter = (TextView) findViewById(R.id.asteroidDiameter);
        TextView asteroidMD = (TextView) findViewById(R.id.asteroidMD);
        Button nextButton = (Button)findViewById(R.id.nextButton);

        if (mIndex < asteroidList.size() - 1) {

            mIndex += 1;
            setResources(mIndex);
            asteroidGIF.invalidate();
            asteroidName.invalidate();
            passbyDate.invalidate();
            asteroidVelo.invalidate();
            asteroidMD.invalidate();
            asteroidID.invalidate();
            asteroidDiameter.invalidate();
            nextButton.invalidate();
            prevButton.invalidate();

        }
        if(mIndex == asteroidList.size() - 1){
            nextButton.setBackgroundResource(R.drawable.next_button_grey);
            if(mHapticFeedback) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            }

        }
        nextButton.invalidate();
        prevButton.invalidate();
    }

    public void newSearch(View v){
        startActivity(new Intent(this, NasaDataActivity.class));

    }



    public void decreaseIndex(View v) {
        Button nextButton = (Button) findViewById(R.id.nextButton);

        NasaDataActivity mData = new NasaDataActivity();
        Button prevButton = (Button) findViewById(R.id.prevButton);
        final List<Asteroid> asteroidList = mData.asteroidGroup;
        Button asteroidGIF = (Button) findViewById(R.id.asteroidGIF);
        TextView asteroidName = (TextView) findViewById(R.id.asteroidName);
        TextView passbyDate = (TextView) findViewById(R.id.passByDate);
        TextView asteroidVelo = (TextView) findViewById(R.id.asteroidVelo);
        TextView asteroidID = (TextView) findViewById(R.id.asteroidID);
        TextView asteroidDiameter = (TextView) findViewById(R.id.asteroidDiameter);
        TextView asteroidMD = (TextView) findViewById(R.id.asteroidMD);
        if (mIndex > 0) {
            mIndex -= 1;
            setResources(mIndex);
            nextButton.invalidate();
            prevButton.invalidate();


        }
        if (mIndex == 0) {
            prevButton.setBackgroundResource(R.drawable.prev_button_grey);
            if(mHapticFeedback) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            }


        }



        }

    private void setResources(int mIndex){
        Button nextButton = (Button) findViewById(R.id.nextButton);

        NasaDataActivity mData = new NasaDataActivity();
        Button prevButton = (Button) findViewById(R.id.prevButton);
        final List<Asteroid> asteroidList = mData.asteroidGroup;
        Button asteroidGIF = (Button) findViewById(R.id.asteroidGIF);
        TextView asteroidName = (TextView) findViewById(R.id.asteroidName);
        TextView passbyDate = (TextView) findViewById(R.id.passByDate);
        TextView asteroidVelo = (TextView) findViewById(R.id.asteroidVelo);
        TextView asteroidID = (TextView) findViewById(R.id.asteroidID);
        TextView asteroidDiameter = (TextView) findViewById(R.id.asteroidDiameter);
        TextView asteroidMD = (TextView) findViewById(R.id.asteroidMD);
        if (cCustomary){
            String velo = "Velocity: " +asteroidList.get(mIndex).getVelocity() + " mph";
            String MD = "Miss Distance: " + asteroidList.get(mIndex).getMissDistance() + " miles";
            String Diameter = "Diameter: " + asteroidList.get(mIndex).getMinDiameter() + " miles";
            asteroidVelo.setText(velo);
            asteroidMD.setText(MD);
            asteroidDiameter.setText(Diameter);

        }

        else {
            Double Dvelo = Double.parseDouble(asteroidList.get(mIndex).getVelocity()) * 1.60934;
            String Vkm = Double.toString(Dvelo);
            String velo = "Velocity: " + Vkm + " kph";

            Double DMD = Double.parseDouble(asteroidList.get(mIndex).getMissDistance()) * 1.60934;
            String VMD = Double.toString(DMD);
            String MD = "Miss Distance: " + VMD + " kilometers";

            Double dDia = Double.parseDouble(asteroidList.get(mIndex).getMinDiameter()) * 1.60934;
            String dkm = Double.toString(dDia);
            String Diameter = "Diameter: " + dkm + " kilometers";
            asteroidVelo.setText(velo);
            asteroidMD.setText(MD);
            asteroidDiameter.setText(Diameter);
        }

        nextButton.setBackgroundResource(R.drawable.next_button);
        prevButton.setBackgroundResource(R.drawable.prev_button);
        asteroidName.setText(asteroidList.get(mIndex).getName());
        passbyDate.setText(asteroidList.get(mIndex).getCloseApproachDate());
        asteroidID.setText("ID: "+ asteroidList.get(mIndex).getID());
        if (asteroidList.get(mIndex).getPotentialHazard()){
            asteroidGIF.setBackgroundResource(R.drawable.comet_red);
        }
        else{
            asteroidGIF.setBackgroundResource(R.drawable.comet_green);

        }            if (mIndex == 0) {
            prevButton.setBackgroundResource(R.drawable.prev_button_grey);

        }
    }
    public void goHome2 (View v){
        startActivity(new Intent(this, MainActivity.class));

    }


}



