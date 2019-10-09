package com.example.kevin.astrofinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class NasaDataActivity extends Activity {
    private String startDate;
    private String endDate;

    MainActivity variables = new MainActivity();
    private boolean mHapticFeedback = variables.mHapticFeedback;
    private boolean mHelper = variables.mHelper;


    private GestureDetector mDetector;

    public static List<Asteroid> asteroidGroup = new ArrayList<>();
    TextView txtDate;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asteroidGroup.clear();
        setContentView(R.layout.activity_nasa_data);
        Button startButton = (Button) findViewById(R.id.startButton);
        Button submitButton = (Button) findViewById(R.id.submitButton);


        if(mHelper) {
            Toast.makeText(this, "Click the Calender to choose a Date", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "then the launch button will open a list of asteroids", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "that will pass Earth in a week range.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Click the logo to return to main menu.", Toast.LENGTH_SHORT).show();

        }

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.primaryBlue));
        }

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        TextView tv = (TextView)findViewById(R.id.tv);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        startDate = Integer.toString(mYear) + '-' + Integer.toString(mMonth + 1) + '-' + Integer.toString(mDay) ;
        Calendar d = Calendar.getInstance();
        try{
            //Setting the date to the given date
            d.setTime(sdf.parse(startDate));
        }catch(ParseException e){
            e.printStackTrace();
        }

        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, 7);
        //Date after adding the days to the given date
        endDate = sdf.format(d.getTime());
        //Displaying the new Date after addition of Days
        tv.setText(startDate);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHapticFeedback) {
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(NasaDataActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            TextView tv = (TextView)findViewById(R.id.tv);
                            startDate = Integer.toString(year) + '-' + Integer.toString(month + 1) + '-' + Integer.toString(day) ;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar c = Calendar.getInstance();
                                try{
                                    //Setting the date to the given date
                                    c.setTime(sdf.parse(startDate));
                                }catch(ParseException e){
                                    e.printStackTrace();
                                }

                                //Number of Days to add
                                c.add(Calendar.DAY_OF_MONTH, 7);
                                //Date after adding the days to the given date
                                endDate = sdf.format(c.getTime());
                                //Displaying the new Date after addition of Days
                                tv.setText(startDate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }

        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHapticFeedback) {
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
                downloadData();



            }

        });
    }




    private void downloadData() {
//check to see if device is connected to the internet before proceeding to download image:
        ConnectivityManager connMgr
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data:
            new NasaDataActivity.DownloadDataTask().execute(); // this will cause syntax error
        } else {
            Toast.makeText(this, "This device is not connected to the intenet", Toast.LENGTH_LONG).show();
            // display error:

        }//end if else
    }



        class DownloadDataTask extends AsyncTask<Void, Void, String> {

            private String urlString = "https://api.nasa.gov/neo/rest/v1/feed?start_date=" + startDate + "&end_date=" + endDate + "&api_key=0UycaysfJOaU2QJPXb5SxAVt5hxA5kCWnkq7Djgm";
            private String urlStringFinal = urlString.replace("\\", "%5C");


        protected void onPreExecute() {

        }

        protected String doInBackground(Void... urls) {


            try {

                URL url = new URL(urlStringFinal);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";

            }

            sortData(response);
        }

    }



    private void sortData (String data){


        int counter = 0;
        String[] dataLines = data.split("\n");
        int dataNumLines = dataLines.length;
        for (int x = 0; x < dataNumLines; x++){
            String dataLine = dataLines[x];
            if (dataLine.contains("neo_reference_id")){
                String asteroidName = dataLines[x + 1];
                String minDiameter = dataLines[x + 6];
                String maxDiameter = dataLines[x + 7];
                String hazard = dataLines[x + 22];
                String closeApproach = dataLines[x + 24];
                String velocity = dataLines[x + 27];
                String missDistance = dataLines[x + 35];
                asteroidGroup.add(new Asteroid(counter ,dataLines[x], asteroidName,minDiameter,maxDiameter,hazard,closeApproach,velocity,missDistance));
                counter = counter + 1;

            }
        }
        startActivity(new Intent(this, AsteroidList.class));
    }
    public void goHome (View v){
        startActivity(new Intent(this, MainActivity.class));

    }

}
